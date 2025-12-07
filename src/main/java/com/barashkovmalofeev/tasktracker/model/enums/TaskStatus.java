package com.barashkovmalofeev.tasktracker.model.enums;

public enum TaskStatus {
    NEW("Новая"),
    IN_PROGRESS("В работе"),
    DONE("Завершена");


    private final String description;

    TaskStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
