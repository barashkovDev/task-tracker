package com.barashkovmalofeev.tasktracker.model.dto;

public class CommentCreateDTO {
    private String text;
    private Long authorId;
    private Long taskId;

    public CommentCreateDTO(){}

    public CommentCreateDTO(String text, Long authorId, Long taskId) {
        this.text = text;
        this.authorId = authorId;
        this.taskId = taskId;
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

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
