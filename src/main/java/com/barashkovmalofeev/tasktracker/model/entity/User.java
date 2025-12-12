package com.barashkovmalofeev.tasktracker.model.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "AppUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_project", // Название промежуточной таблицы
            joinColumns = @JoinColumn(name = "user_id"), // Внешний ключ, ссылающийся на ЭТУ сущность (User)
            inverseJoinColumns = @JoinColumn(name = "project_id") // Внешний ключ, ссылающийся на ДРУГУЮ сущность (Project)
    )
    private Set<Project> projects = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),        // внешний ключ на AppUser.id
            inverseJoinColumns = @JoinColumn(name = "role_id")  // внешний ключ на Role.id
    )
    private Set<Role> roles = new HashSet<>();

    public User() {}

    public User(Set<Project> projects, String password, String username) {
        this.projects = projects;
        this.password = password;
        this.username = username;
    }
    public User(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
