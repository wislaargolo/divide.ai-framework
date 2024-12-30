package com.ufrn.imd.divide.ai.framework.controller;

import com.ufrn.imd.divide.ai.framework.dto.request.OpenAIRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.ApiResponseDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.OpenAIResponseDTO;
import com.ufrn.imd.divide.ai.framework.service.OpenAIservice;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("chat-completion")
public class OpenAIController {
    private final OpenAIservice openAIService;

    public OpenAIController(OpenAIservice openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponseDTO<OpenAIResponseDTO>> chatCompletion(@Valid @RequestBody OpenAIRequestDTO chatRequest) throws Exception {
        OpenAIResponseDTO chat = openAIService.chatCompletion(chatRequest);

        ApiResponseDTO<OpenAIResponseDTO> response = new ApiResponseDTO<>(
                true,
                "Chat completed successfully.",
                chat,
                null
        );
        return ResponseEntity.ok(response);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDTO<OpenAIResponseDTO>> chatCompletion(@PathVariable Long userId) throws Exception {
        OpenAIResponseDTO chat = openAIService.getLastChat(userId);

        ApiResponseDTO<OpenAIResponseDTO> response = new ApiResponseDTO<>(
                true,
                "Chat retrieved successfully.",
                chat,
                null
        );
        return ResponseEntity.ok(response);
    }
}
