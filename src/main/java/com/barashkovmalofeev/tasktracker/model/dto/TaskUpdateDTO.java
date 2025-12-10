package com.barashkovmalofeev.tasktracker.model.dto;

import com.barashkovmalofeev.tasktracker.model.enums.TaskComplexity;
import com.barashkovmalofeev.tasktracker.model.enums.TaskPriority;
import com.barashkovmalofeev.tasktracker.model.enums.TaskStatus;

import java.time.LocalDate;

public class TaskUpdateDTO {

    private String name;
    private String description;
    private Long projectId;
    private Long assignedUserId;
    private TaskPriority priority;
    private TaskComplexity complexity;
    private TaskStatus status;
    private LocalDate endDate;

}
