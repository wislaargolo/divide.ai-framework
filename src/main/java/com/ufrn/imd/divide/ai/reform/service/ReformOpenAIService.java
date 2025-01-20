package com.ufrn.imd.divide.ai.reform.service;

import com.azure.ai.openai.OpenAIClient;
import com.ufrn.imd.divide.ai.framework.dto.request.OpenAIRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.ConfigOpenAIResponseDTO;
import com.ufrn.imd.divide.ai.framework.mapper.ChatMapper;
import com.ufrn.imd.divide.ai.framework.repository.OpenAIRepository;
import com.ufrn.imd.divide.ai.framework.service.OpenAIService;
import com.ufrn.imd.divide.ai.framework.service.UserService;
import com.ufrn.imd.divide.ai.framework.service.UserValidationService;
import com.ufrn.imd.divide.ai.reform.model.Reform;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import com.ufrn.imd.divide.ai.framework.util.FileUtils;

@Profile("reform")
@Service
public class ReformOpenAIService extends OpenAIService<Reform> {

    public ReformOpenAIService(OpenAIClient openAIClient, ChatMapper chatMapper, OpenAIRepository openAIRepository,
                               UserValidationService userValidationService, UserService userService, ReformService reformService) {
        super(openAIClient, chatMapper, openAIRepository, userValidationService, userService, reformService);
    }

    @Override
    protected String buildPrompt(OpenAIRequestDTO chatRequestDTO, Reform group) throws Exception {
        String local = group.getLocal() != null ? group.getLocal().toString() : "N/A";
        String descricao = group.getDescription() != null ? group.getDescription() : "N/A";
        String area = group.getArea() != null ? group.getArea().toString() : "N/A";
        String priority = (group.getPriority() != null && group.getPriority().getDescription() != null)
                ? group.getPriority().getDescription()
                : "N/A";

        String userPrompt = chatRequestDTO.prompt() != null ? chatRequestDTO.prompt() : "";

        return FileUtils.readSystemPromptFile("prompt/reform/Prompt.txt")
                .replace("{local}", local)
                .replace("{descricao}", descricao)
                .replace("{area}", area)
                .replace("{priority}", priority)
                .replace("{userPrompt}", userPrompt);
    }

    @Override
    protected ConfigOpenAIResponseDTO getConfig() {
        return new ConfigOpenAIResponseDTO("gpt-4o", 0.5, "prompt/reform/ReformSystemPrompt.txt");
    }

}
