package com.ufrn.imd.divide.ai.framework.dto.response;

import java.time.LocalDate;
import java.util.List;

public class GroupResponseDTO {

    private Long id;
    private String name;
    private String description;
    private String code;
    private List<UserResponseDTO> members;
    private UserResponseDTO createdBy;
    private boolean discontinued;

    public GroupResponseDTO() {}

    public GroupResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<UserResponseDTO> getMembers() {
        return members;
    }

    public void setMembers(List<UserResponseDTO> members) {
        this.members = members;
    }

    public UserResponseDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserResponseDTO createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }

}
