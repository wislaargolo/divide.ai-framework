package com.ufrn.imd.divide.ai.sporting.dto.request;

import com.ufrn.imd.divide.ai.framework.dto.request.GroupUpdateRequestDTO;
import com.ufrn.imd.divide.ai.sporting.model.SportingsModalities;

public class SportingUpdateRequestDTO extends GroupUpdateRequestDTO {
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
