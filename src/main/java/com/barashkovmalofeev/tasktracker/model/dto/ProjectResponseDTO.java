package com.barashkovmalofeev.tasktracker.model.dto;

import com.barashkovmalofeev.tasktracker.model.entity.Project;
import com.barashkovmalofeev.tasktracker.model.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

public class ProjectResponseDTO {
    private Long id;
    private String name;
    private Set<UserDTO> users;

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
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
        this.users = project.getUsers().stream().map(user -> new UserDTO(user)).collect(Collectors.toSet());
    }

    public ProjectResponseDTO(Long id, String name, Set<User> users) {
        this.id = id;
        this.name = name;
        this.users = users.stream().map(user -> new UserDTO(user)).collect(Collectors.toSet());
    }
}
