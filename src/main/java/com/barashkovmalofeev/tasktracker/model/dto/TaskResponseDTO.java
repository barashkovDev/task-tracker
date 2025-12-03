package com.barashkovmalofeev.tasktracker.model.dto;

import com.barashkovmalofeev.tasktracker.model.entity.Task;

import java.time.LocalDate;

public class TaskResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Long number;
    private LocalDate productionDate;
    private LocalDate endDate;
    private String complexity;
    private String priority;
    private String status;
    private Boolean isTaskCompleted;

    // Только ID связанных сущностей
    private Long assignedUserId;
    private Long projectId;

    // Конструктор из Entity
    public TaskResponseDTO(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.number = task.getNumber();
        this.productionDate = task.getProductionDate();
        this.endDate = task.getEndDate();
        this.complexity = task.getComplexity();
        this.priority = task.getPriority();
        this.status = task.getStatus();
        this.isTaskCompleted = task.getTaskCompleted();

        // Только ID, а не целые объекты
        if (task.getAssignedUser() != null) {
            this.assignedUserId = task.getAssignedUser().getId();
        }

        if (task.getProject() != null) {
            this.projectId = task.getProject().getId();
        }
    }

    // Геттеры (сеттеры по необходимости)
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Long getNumber() { return number; }
    public LocalDate getProductionDate() { return productionDate; }
    public LocalDate getEndDate() { return endDate; }
    public String getComplexity() { return complexity; }
    public String getPriority() { return priority; }
    public String getStatus() { return status; }
    public Boolean getTaskCompleted() { return isTaskCompleted; }
    public Long getAssignedUserId() { return assignedUserId; }
    public Long getProjectId() { return projectId; }
}