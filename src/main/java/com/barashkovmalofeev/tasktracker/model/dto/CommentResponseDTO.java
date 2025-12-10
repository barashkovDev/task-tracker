package com.barashkovmalofeev.tasktracker.model.dto;

import com.barashkovmalofeev.tasktracker.model.entity.Comment;

import java.time.LocalDateTime;

public class CommentResponseDTO {
    private Long id;
    private String text;
    private Long authorId;
    private String authorName;
    private LocalDateTime productionDate;

    public CommentResponseDTO(){}

    public CommentResponseDTO(Long id, String text, Long authorId, String authorName, LocalDateTime productionDate) {
        this.id = id;
        this.text = text;
        this.authorId = authorId;
        this.authorName = authorName;
        this.productionDate = productionDate;
    }

    public CommentResponseDTO(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.authorId = comment.getAuthor().getId();
        this.authorName = comment.getAuthor().getUsername();
        this.productionDate = comment.getProductionDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public LocalDateTime getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDateTime productionDate) {
        this.productionDate = productionDate;
    }
}
