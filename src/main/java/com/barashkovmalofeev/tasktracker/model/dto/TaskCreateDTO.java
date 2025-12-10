package com.barashkovmalofeev.tasktracker.model.dto;

import com.barashkovmalofeev.tasktracker.model.enums.TaskComplexity;
import com.barashkovmalofeev.tasktracker.model.enums.TaskPriority;
import com.barashkovmalofeev.tasktracker.model.enums.TaskStatus;

import java.time.Duration;
import java.time.LocalDate;

public class TaskCreateDTO {
    private String name;
    private String description;
    private Long projectId;
    private Long assignedUserId;
    private TaskPriority priority;
    private TaskComplexity complexity;
    private TaskStatus status;
    private LocalDate endDate;


    public TaskCreateDTO(){}

    public TaskCreateDTO(String name, String description, Long projectId, Long assignedUserId, TaskPriority priority,
                         TaskComplexity complexity) {
        this.name = name;
        this.description = description;
        this.projectId = projectId;
        this.assignedUserId = assignedUserId;
        this.priority = priority;
        this.complexity = complexity;
    }

    public TaskCreateDTO(String name, String description, Long projectId, Long assignedUserId, TaskPriority priority,
                         TaskComplexity complexity, TaskStatus taskStatus) {
        this.name = name;
        this.description = description;
        this.projectId = projectId;
        this.assignedUserId = assignedUserId;
        this.priority = priority;
        this.complexity = complexity;
        this.status = taskStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public TaskComplexity getComplexity() {
        return complexity;
    }

    public void setComplexity(TaskComplexity complexity) {
        this.complexity = complexity;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
