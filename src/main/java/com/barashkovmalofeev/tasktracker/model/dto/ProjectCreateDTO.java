package com.barashkovmalofeev.tasktracker.model.dto;

import java.util.HashSet;
import java.util.Set;

public class ProjectCreateDTO {
    private String name;
    private Set<Long> userIds = new HashSet<>();

    public Set<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Long> userIds) {
        this.userIds = userIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectCreateDTO(){}

    public ProjectCreateDTO(String name, Set<Long> userIds) {
        this.name = name;
        this.userIds = userIds;
    }
}
