package com.ufrn.imd.divide.ai.reform.dto.response;

import com.ufrn.imd.divide.ai.framework.dto.response.GroupResponseDTO;
import com.ufrn.imd.divide.ai.reform.model.ReformPriority;

import java.time.LocalDate;

public class ReformResponseDTO extends GroupResponseDTO {
    private Double area;
    private String local;
    private String priority;

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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
