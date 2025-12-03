package com.barashkovmalofeev.tasktracker.model.dto;

import java.time.LocalDate;

public class TaskCreateDTO {
    private String name;
    private String description;
    private Long projectId;
    private Long assignedUserId;
    private String priority;
    private String complexity;

    public TaskCreateDTO(){}

    public TaskCreateDTO(String name, String description, Long projectId, Long assignedUserId, String priority, String complexity) {
        this.name = name;
        this.description = description;
        this.projectId = projectId;
        this.assignedUserId = assignedUserId;
        this.priority = priority;
        this.complexity = complexity;
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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getComplexity() {
        return complexity;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }
}
