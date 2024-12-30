package com.ufrn.imd.divide.ai.reform.dto.response;

import com.ufrn.imd.divide.ai.framework.dto.response.GroupResponseDTO;

import java.time.LocalDate;

public class ReformResponseDTO extends GroupResponseDTO {
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
