package com.ufrn.imd.divide.ai.reform.dto.request;

import com.ufrn.imd.divide.ai.framework.dto.request.GroupUpdateRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ReformUpdateRequestDTO extends GroupUpdateRequestDTO {
    private LocalDate startDate;
    private String local;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
