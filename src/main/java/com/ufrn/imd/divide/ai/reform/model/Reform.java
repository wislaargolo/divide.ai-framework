package com.ufrn.imd.divide.ai.reform.model;

import com.ufrn.imd.divide.ai.framework.model.Group;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "reforms")
public class Reform extends Group {

    @Column(nullable = false)
    private String local;
    @Column(nullable = false)
    private Double area;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReformPriority priority;

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public ReformPriority getPriority() {
        return priority;
    }

    public void setPriority(ReformPriority priority) {
        this.priority = priority;
    }
}
