package com.barashkovmalofeev.tasktracker.model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne // Много комментариев (Comment) к Одному Пользователю (User)
    @JoinColumn(name = "user_author_id") // Внешний ключ в таблице Comment
    private User author;

    @ManyToOne // Много комментариев (Comment) к Одной задаче (Task)
    @JoinColumn(name = "task_id") // Внешний ключ в таблице Task
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
