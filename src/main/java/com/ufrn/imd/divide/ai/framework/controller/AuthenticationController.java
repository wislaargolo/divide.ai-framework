package com.ufrn.imd.divide.ai.framework.controller;

import com.ufrn.imd.divide.ai.framework.dto.request.AuthRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.ApiResponseDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.AuthResponseDTO;
import com.ufrn.imd.divide.ai.framework.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> authenticate(
            @Valid @RequestBody AuthRequestDTO request) {
        ApiResponseDTO<AuthResponseDTO> response = new ApiResponseDTO<>(
                true,
                "Autenticação realizada com sucesso.",
                authenticationService.authenticate(request),
                null);

        return ResponseEntity.ok(response);
    }
}
