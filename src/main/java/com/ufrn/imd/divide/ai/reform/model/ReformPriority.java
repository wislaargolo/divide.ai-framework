package com.ufrn.imd.divide.ai.reform.model;

public enum ReformPriority {

    URGENT("Urgente"),
    HIGH("Alta"),
    MEDIUM("Média"),
    LOW("Baixa");

    private final String description;

    ReformPriority(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
