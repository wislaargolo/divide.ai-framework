package com.ufrn.imd.divide.ai.framework.mapper;

import com.ufrn.imd.divide.ai.framework.dto.request.OpenAIRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.OpenAIResponseDTO;
import com.ufrn.imd.divide.ai.framework.model.Chat;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {

    public Chat toEntity(OpenAIRequestDTO openAIRequestDTO) {
        if (openAIRequestDTO == null) {
            return null;
        }

        Chat chat = new Chat();
        chat.setPrompt(openAIRequestDTO.prompt());
        return chat;
    }

    public Chat toEntity(OpenAIResponseDTO openAIResponseDTO) {
        if (openAIResponseDTO == null) {
            return null;
        }

        Chat chat = new Chat();
        chat.setResponse(openAIResponseDTO.response());
        return chat;
    }

    public OpenAIResponseDTO toDto(Chat chat) {
        if (chat == null) {
            return null;
        }

        return new OpenAIResponseDTO(
                null,
                null,
                null,
                null,
                chat.getResponse(),
                false
        );
    }

}