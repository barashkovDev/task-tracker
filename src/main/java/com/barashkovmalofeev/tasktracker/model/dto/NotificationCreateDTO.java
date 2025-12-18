package com.barashkovmalofeev.tasktracker.model.dto;

public class NotificationCreateDTO {
    private String text;
    private Long assignedUserId;

    public NotificationCreateDTO(){}

    public NotificationCreateDTO(String text, Long assignedUserId) {
        this.text = text;
        this.assignedUserId = assignedUserId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }
}
