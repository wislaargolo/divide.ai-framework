package com.ufrn.imd.divide.ai.framework.service;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatRequestMessage;
import com.azure.ai.openai.models.ChatRequestSystemMessage;
import com.azure.ai.openai.models.ChatRequestUserMessage;
import com.ufrn.imd.divide.ai.framework.dto.request.OpenAIRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.ChatResponseDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.ConfigOpenAIResponseDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.OpenAIResponseDTO;
import com.ufrn.imd.divide.ai.framework.exception.BusinessException;
import com.ufrn.imd.divide.ai.framework.mapper.ChatMapper;
import com.ufrn.imd.divide.ai.framework.model.Chat;
import com.ufrn.imd.divide.ai.framework.model.Group;
import com.ufrn.imd.divide.ai.framework.model.User;
import com.ufrn.imd.divide.ai.framework.repository.OpenAIRepository;
import com.ufrn.imd.divide.ai.framework.util.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public abstract class OpenAIService<DTO> {
    protected OpenAIClient openAIClient;
    protected ChatMapper chatMapper;
    protected OpenAIRepository openAIRepository;

    private final UserValidationService userValidationService;
    private final UserService userService;
    private final GenericGroupService<Group> groupService;

    protected String SYSTEM_PROMPT_FILE_PATH = "prompt/SystemPrompt.txt";

    @Value("${openai.model}")
    protected String model;

    @Value("${openai.temperature}")
    protected Double temperature;

    public OpenAIService(OpenAIClient openAIClient, ChatMapper chatMapper, OpenAIRepository openAIRepository,
                         UserValidationService userValidationService, UserService userService,
                         GenericGroupService<Group> groupService)
    {
        this.openAIClient = openAIClient;
        this.chatMapper = chatMapper;
        this.openAIRepository = openAIRepository;
        this.userValidationService = userValidationService;
        this.userService = userService;
        this.groupService = groupService;
    }

    public OpenAIResponseDTO chatCompletion(OpenAIRequestDTO chatRequestDTO) throws Exception {
        Long userId = chatRequestDTO.userId();
        Long groupId = chatRequestDTO.groupId();

        userValidationService.validateUser(userId);
        User user = userService.findById(userId);

        Group group = groupService.findByIdIfExists(groupId);

        String prompt = buildPrompt(chatRequestDTO);

        List<ChatRequestMessage> chatMessages = buildChatMessages(prompt);

        ChatCompletionsOptions options = new ChatCompletionsOptions(chatMessages)
                .setTemperature(getConfig().temperature());

        var chatCompletionResponse = openAIClient.getChatCompletions(getConfig().model(), options);

        if (chatCompletionResponse.getChoices().isEmpty() || chatCompletionResponse.getChoices().get(0).getMessage().getContent().isEmpty())
            throw new BusinessException("Empty response from OpenAI", HttpStatus.BAD_REQUEST);

        String chatResponse = chatCompletionResponse.getChoices().get(0).getMessage().getContent();
        String chatId = chatCompletionResponse.getId();

        saveChat(chatRequestDTO, user, group, chatResponse, chatId);

        return new OpenAIResponseDTO(chatResponse);
    }

    protected List<ChatRequestMessage> buildChatMessages(String prompt) throws Exception {
        List<ChatRequestMessage> chatMessages = new ArrayList<>();
        String systemPromptTemplate = FileUtils.readSystemPromptFile(getConfig().systemPromptPath());
        chatMessages.add(new ChatRequestSystemMessage(systemPromptTemplate));
        chatMessages.add(new ChatRequestUserMessage(prompt));
        return chatMessages;
    }

    private void saveChat(OpenAIRequestDTO chatRequestDTO, User user, Group group, String chatResponse, String chatId) {
        Chat chat = chatMapper.toEntity(chatRequestDTO);
        chat.setUser(user);
        chat.setGroup(group);
        chat.setResponse(chatResponse);
        chat.setChatId(chatId);
        openAIRepository.save(chat);
    }

    protected abstract String buildPrompt(OpenAIRequestDTO chatRequestDTO) throws Exception;

    protected ConfigOpenAIResponseDTO getConfig() {
        return new ConfigOpenAIResponseDTO(
                model, temperature, SYSTEM_PROMPT_FILE_PATH);
    }

    public OpenAIResponseDTO getLastChat(Long userId) {
        Chat lastChat = openAIRepository.findTopByUserIdOrderByCreatedAtDesc(userId);

        return chatMapper.toDto(lastChat);
    }

    public ChatResponseDTO getLastChatByUserIdAndGroupId(Long userId, Long chatId) {
        Chat lastChat = openAIRepository.findTopByUserIdAndGroupIdOrderByCreatedAtDesc(userId, chatId);

        return chatMapper.toChatResponseDTO(lastChat);
    }

//    private OpenAIResponseDTO parseChatResponseToJSON(String chatResponse) {
//        String cleanedResponse = chatResponse.replaceAll("```json|```", "").trim();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        try {
//            return objectMapper.readValue(cleanedResponse, OpenAIResponseDTO.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new BusinessException("Erro ao parsear resposta da LLM para JSON.", HttpStatus.BAD_REQUEST);
//        }
//    }
}
