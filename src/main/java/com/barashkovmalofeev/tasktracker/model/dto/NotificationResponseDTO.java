package com.barashkovmalofeev.tasktracker.model.dto;

import com.barashkovmalofeev.tasktracker.model.entity.Notification;
import com.barashkovmalofeev.tasktracker.model.entity.User;

import javax.persistence.*;
import java.time.LocalDate;

public class NotificationResponseDTO {
    private Long id;
    private String text;
    private String taskName;
    private LocalDate productionDate;
    private Boolean isRead;

    public NotificationResponseDTO(){}

    public NotificationResponseDTO(String text) {
        this.text = text;
    }

    public NotificationResponseDTO(Notification notification) {
        id = notification.getId();
        text = notification.getText();
        taskName = notification.getTaskName();
        productionDate = notification.getProductionDate();
        isRead = notification.getRead();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
