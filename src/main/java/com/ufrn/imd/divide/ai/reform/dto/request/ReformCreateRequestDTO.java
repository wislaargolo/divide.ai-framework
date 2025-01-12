package com.ufrn.imd.divide.ai.reform.dto.request;

import com.ufrn.imd.divide.ai.framework.dto.request.GroupCreateRequestDTO;
import com.ufrn.imd.divide.ai.reform.model.ReformPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ReformCreateRequestDTO extends GroupCreateRequestDTO {
    @NotNull(message = "area é obrigatório.")
    @Positive(message = "A área deve ser maior que zero.")
    private Double area;
    @NotBlank(message = "local é obrigatório.")
    private String local;
    @NotNull(message = "priority é obrigatório.")
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
