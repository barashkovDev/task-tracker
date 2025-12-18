package com.barashkovmalofeev.tasktracker.model.dto;

import com.barashkovmalofeev.tasktracker.model.entity.Notification;

public class NotificationResponseDTO {
    private String text;

    public NotificationResponseDTO(){}

    public NotificationResponseDTO(String text) {
        this.text = text;
    }

    public NotificationResponseDTO(Notification notification) {
        this.text = notification.getText();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
