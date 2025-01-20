package com.ufrn.imd.divide.ai.sporting.service;

import com.azure.ai.openai.OpenAIClient;
import com.ufrn.imd.divide.ai.framework.dto.request.OpenAIRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.ConfigOpenAIResponseDTO;
import com.ufrn.imd.divide.ai.framework.mapper.ChatMapper;
import com.ufrn.imd.divide.ai.framework.repository.OpenAIRepository;
import com.ufrn.imd.divide.ai.framework.service.OpenAIService;
import com.ufrn.imd.divide.ai.framework.service.UserService;
import com.ufrn.imd.divide.ai.framework.service.UserValidationService;
import com.ufrn.imd.divide.ai.framework.util.FileUtils;
import com.ufrn.imd.divide.ai.sporting.model.Sporting;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("sporting")
@Service
public class SportingOpenAIService extends OpenAIService<Sporting> {
    public SportingOpenAIService(OpenAIClient openAIClient, ChatMapper chatMapper, OpenAIRepository openAIRepository,
                                 UserValidationService userValidationService, UserService userService,
                                 SportingService sportingService) {
        super(openAIClient, chatMapper, openAIRepository, userValidationService, userService, sportingService);
    }

    @Override
    protected String buildPrompt(OpenAIRequestDTO chatRequestDTO, Sporting group) throws Exception {
        String local = group.getLocal() != null ? group.getLocal().toString() : "N/A";
        String sport = group.getSportingsModalities().getDescription() != null ? group.getSportingsModalities().getDescription() : "N/A";
        String dateTime = group.getOccurrenceDate().toString() != null ? group.getOccurrenceDate().toString() : "N/A";
        String descricao = group.getDescription() != null ? group.getDescription() : "N/A";
        String quatidadeParticipantes = group.getMembers().toString() != null ? group.getMembers().toString() : "N/A";

        return FileUtils.readSystemPromptFile("prompt/sport/Prompt.txt")
                .replace("{local}", local)
                .replace("{descricao}", descricao)
                .replace("{sport}", sport)
                .replace("{dateTime}", dateTime)
                .replace("{quatidadeParticipantes}", quatidadeParticipantes);
    }

    @Override
    protected ConfigOpenAIResponseDTO getConfig() {
        return new ConfigOpenAIResponseDTO("gpt-4o", 0.5, "prompt/sport/SportSystemPrompt.txt");
    }
}
