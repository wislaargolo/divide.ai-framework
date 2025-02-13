package com.ufrn.imd.divide.ai.framework.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GroupCreateRequestDTO {

        @NotBlank(message = "name é obrigatório.")
        private String name;
        String description;
        @NotNull(message = "createdBy é obrigatório.")
        private Long createdBy;
        @NotNull(message = "occurrenceDate é obrigatório.")
        private LocalDateTime occurrenceDate;

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public Long getCreatedBy() {
                return createdBy;
        }

        public void setCreatedBy(Long createdBy) {
                this.createdBy = createdBy;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public LocalDateTime getOccurrenceDate() {
                return occurrenceDate;
        }

        public void setOccurrenceDate(LocalDateTime occurrenceDate) {this.occurrenceDate = occurrenceDate;}

}

