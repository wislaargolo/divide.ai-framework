package com.ufrn.imd.divide.ai.framework.controller;

import com.ufrn.imd.divide.ai.framework.dto.request.GroupCreateRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.request.GroupUpdateRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.request.JoinGroupRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.ApiResponseDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.GroupResponseDTO;
import com.ufrn.imd.divide.ai.framework.model.Group;
import com.ufrn.imd.divide.ai.framework.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class GroupController<T extends Group,
        CRequestDTO extends GroupCreateRequestDTO,
        URequestDTO extends GroupUpdateRequestDTO,
        ResponseDTO extends GroupResponseDTO> {

    private final GroupService<T, CRequestDTO, URequestDTO, ResponseDTO> groupService;

    public GroupController(GroupService<T, CRequestDTO, URequestDTO, ResponseDTO> groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<ResponseDTO>> save(
            @Valid @RequestBody CRequestDTO dto) {

        ApiResponseDTO<ResponseDTO> response = new ApiResponseDTO<>(
                true,
                "Grupo criado com sucesso.",
                groupService.save(dto),
                null
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ResponseDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody URequestDTO dto) {

        ApiResponseDTO<ResponseDTO> response = new ApiResponseDTO<>(
                true,
                "Grupo atualizado com sucesso.",
                groupService.update(id, dto),
                null
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<?>> delete(@PathVariable Long id) {
        groupService.delete(id);

        ApiResponseDTO<?> response = new ApiResponseDTO<>(
                true,
                "Grupo removido com sucesso.",
                null,
                null
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ResponseDTO>> findById(@PathVariable Long id) {
        ResponseDTO groupResponse = groupService.findById(id);

        ApiResponseDTO<ResponseDTO> response = new ApiResponseDTO<>(
                true,
                "Grupo retornado com sucesso.",
                groupResponse,
                null
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDTO<List<ResponseDTO>>> findAllByUserId(
            @PathVariable Long userId) {

        ApiResponseDTO<List<ResponseDTO>> response = new ApiResponseDTO<>(
                true,
                "Grupos retornados com sucesso.",
                groupService.findAllByUserId(userId),
                null
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/join")
    public ResponseEntity<ApiResponseDTO<ResponseDTO>> joinGroupByCode(
            @Valid @RequestBody JoinGroupRequestDTO dto) {

        ApiResponseDTO<ResponseDTO> response = new ApiResponseDTO<>(
                true,
                "Usuário entrou no grupo com sucesso.",
                groupService.joinGroupByCode(dto),
                null
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{groupId}/user/{userId}/leave")
    public ResponseEntity<ApiResponseDTO<ResponseDTO>> leaveGroup(
            @PathVariable Long groupId,
            @PathVariable Long userId) {

        ApiResponseDTO<ResponseDTO> response = new ApiResponseDTO<>(
                true,
                "Usuário saiu do grupo com sucesso.",
                groupService.leaveGroup(groupId, userId),
                null
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{groupId}/user/{userId}/delete")
    public ResponseEntity<ApiResponseDTO<ResponseDTO>> deleteMember(
            @PathVariable Long groupId,
            @PathVariable Long userId) {

        ApiResponseDTO<ResponseDTO> response = new ApiResponseDTO<>(
                true,
                "Usuário foi removido do grupo com sucesso.",
                groupService.deleteMember(groupId, userId),
                null
        );

        return ResponseEntity.ok(response);
    }
}
