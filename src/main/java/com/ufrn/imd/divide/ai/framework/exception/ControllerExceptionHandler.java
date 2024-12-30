package com.ufrn.imd.divide.ai.framework.exception;

import com.ufrn.imd.divide.ai.framework.dto.response.ApiResponseDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.ErrorResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.ZonedDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<ApiResponseDTO<ErrorResponseDTO>> handleResourceNotFoundException(
            Exception exception, WebRequest request) {

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                ZonedDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                exception.getMessage(),
                request.getDescription(false));

        ApiResponseDTO<ErrorResponseDTO> response = new ApiResponseDTO<>(
                false,
                "Recurso não encontrado.",
                null,
                errorResponse
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponseDTO<ErrorResponseDTO>> handleBusinessException(
            BusinessException exception, WebRequest request) {

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                ZonedDateTime.now(),
                exception.getHttpStatusCode().value(),
                "Erro de negócio.",
                exception.getMessage(),
                request.getDescription(false));

        ApiResponseDTO<ErrorResponseDTO> response = new ApiResponseDTO<>(
                false,
                "Falha na validação de negócio.",
                null,
                errorResponse
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<ApiResponseDTO<ErrorResponseDTO>> handleBadCredentialsException(
            Exception exception, WebRequest request) {

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                ZonedDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                exception.getMessage(),
                request.getDescription(false));

        ApiResponseDTO<ErrorResponseDTO> response = new ApiResponseDTO<>(
                false,
                "Credenciais inválidas.",
                null,
                errorResponse
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponseDTO<ErrorResponseDTO>> handleExpiredJwtException(ExpiredJwtException exception, WebRequest request) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                ZonedDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                exception.getMessage(),
                request.getDescription(false));

        ApiResponseDTO<ErrorResponseDTO> response = new ApiResponseDTO<>(
                false,
                "Token expirado.",
                null,
                errorResponse
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<ErrorResponseDTO>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception, WebRequest request) {
        String errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                ZonedDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errors,
                request.getDescription(false));

        ApiResponseDTO<ErrorResponseDTO> response = new ApiResponseDTO<>(
                false,
                "Erro de validação.",
                null,
                errorResponse
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ForbiddenOperationException.class)
    public ResponseEntity<ApiResponseDTO<ErrorResponseDTO>> handleUnauthorizedUserException(
            ForbiddenOperationException exception, WebRequest request) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                ZonedDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                exception.getMessage(),
                request.getDescription(false)
        );

        ApiResponseDTO<ErrorResponseDTO> response = new ApiResponseDTO<>(
                false,
                "Acesso proibido.",
                null,
                errorResponse
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<ErrorResponseDTO>> handleException(Exception exception, WebRequest request) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                ZonedDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                exception.getMessage(),
                request.getDescription(false));

        ApiResponseDTO<ErrorResponseDTO> response = new ApiResponseDTO<>(
                false,
                "Erro interno de servidor.",
                null,
                errorResponse
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

