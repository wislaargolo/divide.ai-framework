package com.ufrn.imd.divide.ai.sporting.dto.response;

import com.ufrn.imd.divide.ai.framework.dto.response.GroupResponseDTO;
import com.ufrn.imd.divide.ai.sporting.model.SportingsModalities;

public class SportingResponseDTO extends GroupResponseDTO{
    private String local;
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
