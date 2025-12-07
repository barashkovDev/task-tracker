package com.barashkovmalofeev.tasktracker.model.enums;

public enum TaskPriority {
    LOW("Низкий"),
    MEDIUM("Средний"),
    HIGH("Высокий");


    private final String description;

    TaskPriority(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
