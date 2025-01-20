package com.ufrn.imd.divide.ai.sporting.dto.request;

import com.ufrn.imd.divide.ai.framework.dto.request.GroupCreateRequestDTO;
import com.ufrn.imd.divide.ai.sporting.model.SportingsModalities;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SportingCreateRequestDTO extends GroupCreateRequestDTO {
    @NotBlank(message = "Local é obrigatório.")
    private String local;
    @NotNull(message = "Modalidade é obrigatória.")
    private SportingsModalities sportingsModalities;

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public SportingsModalities getSportingsModalities() {
        return sportingsModalities;
    }

    public void setSportingsModalities(SportingsModalities sportingsModalities) {
        this.sportingsModalities = sportingsModalities;
    }

}
