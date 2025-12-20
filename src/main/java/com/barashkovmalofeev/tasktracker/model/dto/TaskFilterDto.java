package com.barashkovmalofeev.tasktracker.model.dto;

import com.barashkovmalofeev.tasktracker.model.enums.TaskStatus;

public class TaskFilterDto {
    private String description;
    private String name;
    private TaskStatus status;
    private String comment;

    // Конструктор по умолчанию (обязателен для JSON десериализации)
    public TaskFilterDto() {
    }

    // Геттеры и сеттеры
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "TaskFilterDto{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", comment='" + comment + '\'' +
                '}';
    }
}
