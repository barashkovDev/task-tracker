package com.barashkovmalofeev.tasktracker.model.entity;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;

@Entity
@Table(name = "Task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String name;
    private Long number;
    @Column(length = 10000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @ManyToOne // Много задач (Task) к Одному Пользователю (User)
    @JoinColumn(name = "assigned_user_id") // Внешний ключ в таблице Task
    private User assignedUser;
    private LocalDate productionDate;
    private LocalDate endDate;
    private String complexity;
    private String priority;
    private String status;
    private Boolean isTaskCompleted;
    private Duration spentTime;

    public Task(){}

    public Task(String name, Long number, String description, Project project, User assignedUser,
                LocalDate productionDate, LocalDate endDate, String complexity, String priority, String status,
                Boolean isTaskCompleted, Duration spentTime) {
        this.name = name;
        this.number = number;
        this.description = description;
        this.project = project;
        this.assignedUser = assignedUser;
        this.productionDate = productionDate;
        this.endDate = endDate;
        this.complexity = complexity;
        this.priority = priority;
        this.status = status;
        this.isTaskCompleted = isTaskCompleted;
        this.spentTime = spentTime;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public Duration getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(Duration spentTime) {
        this.spentTime = spentTime;
    }

    public Boolean getTaskCompleted() {
        return isTaskCompleted;
    }

    public void setTaskCompleted(Boolean taskCompleted) {
        isTaskCompleted = taskCompleted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
