package com.barashkovmalofeev.tasktracker.model.dto;

import com.barashkovmalofeev.tasktracker.model.entity.Project;

import java.util.Set;

public class ProjectResponseDTO {
    private Long id;
    private String name;
    Set<Long> userIds;

    public Set<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Long> userIds) {
        this.userIds = userIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectResponseDTO(){}

    public ProjectResponseDTO(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.userIds = project.getUserIds();
    }

    public ProjectResponseDTO(Long id, String name, Set<Long> userIds) {
        this.id = id;
        this.name = name;
        this.userIds = userIds;
    }
}
