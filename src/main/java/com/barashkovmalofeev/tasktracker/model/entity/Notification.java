package com.barashkovmalofeev.tasktracker.model.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;
    private String text;
    private String taskName;
    private LocalDate productionDate;
    private Boolean isRead;

    public Notification(){}

    public Notification(Long id, User assignedUser, String text) {
        this.id = id;
        this.assignedUser = assignedUser;
        this.text = text;
    }

    public Notification(Long id, User assignedUser, String text, String taskName, LocalDate productionDate, Boolean isRead) {
        this.id = id;
        this.assignedUser = assignedUser;
        this.text = text;
        this.taskName = taskName;
        this.productionDate = productionDate;
        this.isRead = isRead;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }
}
