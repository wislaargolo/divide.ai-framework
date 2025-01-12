package com.ufrn.imd.divide.ai.reform.dto.request;

import com.ufrn.imd.divide.ai.framework.dto.request.GroupUpdateRequestDTO;
import com.ufrn.imd.divide.ai.reform.model.ReformPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ReformUpdateRequestDTO extends GroupUpdateRequestDTO {
    private Double area;
    private String local;
    private ReformPriority priority;

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public ReformPriority getPriority() {
        return priority;
    }

    public void setPriority(ReformPriority priority) {
        this.priority = priority;
    }
}
