package com.ufrn.imd.divide.ai.framework.controller;

import com.ufrn.imd.divide.ai.framework.dto.request.GroupTransactionCreateRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.request.GroupTransactionUpdateRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.ApiResponseDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.GroupTransactionResponseDTO;
import com.ufrn.imd.divide.ai.framework.service.GroupTransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group-transactions")
public class GroupTransactionController {

    private final GroupTransactionService groupTransactionService;

    public GroupTransactionController(GroupTransactionService groupTransactionService) {
        this.groupTransactionService = groupTransactionService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<GroupTransactionResponseDTO>> save(
            @Valid @RequestBody GroupTransactionCreateRequestDTO dto) {

        ApiResponseDTO<GroupTransactionResponseDTO> response = new ApiResponseDTO<>(
                true,
                "Despesa em grupo criada com sucesso.",
                groupTransactionService.save(dto),
                null);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<ApiResponseDTO<List<GroupTransactionResponseDTO>>> findAllByGroupId(@PathVariable Long groupId) {
        ApiResponseDTO<List<GroupTransactionResponseDTO>> response = new ApiResponseDTO<>(
                true,
                "Despesas em grupo listadas com sucesso.",
                groupTransactionService.findAll(groupId),
                null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<GroupTransactionResponseDTO>> findById(@PathVariable Long id) {
        ApiResponseDTO<GroupTransactionResponseDTO> response = new ApiResponseDTO<>(
                true,
                "Despesa recuperada com sucesso.",
                groupTransactionService.findById(id),
                null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<GroupTransactionResponseDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody GroupTransactionUpdateRequestDTO dto) {

        ApiResponseDTO<GroupTransactionResponseDTO> response = new ApiResponseDTO<>(
                true,
                "Despesa em grupo atualizada com sucesso.",
                groupTransactionService.update(id, dto),
                null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{groupId}/{transactionId}")
    public ResponseEntity<ApiResponseDTO<String>> delete(
            @PathVariable Long groupId,
            @PathVariable Long transactionId) {

        ApiResponseDTO<String> response = new ApiResponseDTO<>(
                true,
                "Despesa em grupo deletada com sucesso.",
                groupTransactionService.delete(groupId, transactionId),
                null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
