package com.barashkovmalofeev.tasktracker.model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private String text;

    private LocalDateTime productionDate;

    public Comment(){}

    public Comment(Long id, User author, String text, LocalDateTime productionDate) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.productionDate = productionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDateTime productionDate) {
        this.productionDate = productionDate;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

}
