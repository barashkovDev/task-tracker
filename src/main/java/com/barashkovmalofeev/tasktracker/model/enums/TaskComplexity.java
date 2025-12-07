package com.barashkovmalofeev.tasktracker.model.enums;

public enum TaskComplexity {
    EASY("Легкий"),
    MEDIUM("Средний"),
    HARD("Тяжелый");


    private final String description;

    TaskComplexity(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
