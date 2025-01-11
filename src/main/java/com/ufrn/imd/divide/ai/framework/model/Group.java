package com.ufrn.imd.divide.ai.framework.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "groups")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Group extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User createdBy;

    @ManyToMany
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members;

    @Column(nullable = false)
    private String code;

    private boolean discontinued = false;

    private LocalDate occurrenceDate;

    public LocalDate getOccurrenceDate() {
        return occurrenceDate;
    }

    public void setOccurrenceDate(LocalDate occurrenceDate) {
        this.occurrenceDate = occurrenceDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }

}
