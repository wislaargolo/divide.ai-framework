package com.ufrn.imd.divide.ai.reform.model;

import com.ufrn.imd.divide.ai.framework.model.Group;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "reforms")
public class Reform extends Group {

    private String local;
    private LocalDate startDate;

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
