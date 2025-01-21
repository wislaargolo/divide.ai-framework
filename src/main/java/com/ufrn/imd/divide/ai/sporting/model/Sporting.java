package com.ufrn.imd.divide.ai.sporting.model;

import com.ufrn.imd.divide.ai.framework.model.Group;
import jakarta.persistence.*;

@Entity
@Table(name = "sportings")
public class Sporting extends Group {
    @Column(nullable = false)
    private String local;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
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
