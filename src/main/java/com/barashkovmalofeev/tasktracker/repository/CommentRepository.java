package com.barashkovmalofeev.tasktracker.repository;

import com.barashkovmalofeev.tasktracker.model.entity.Comment;
import com.barashkovmalofeev.tasktracker.model.entity.Project;
import com.barashkovmalofeev.tasktracker.model.entity.Task;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CommentRepository {
    @PersistenceContext(unitName = "taskTrackerPU")
    private EntityManager em;

    public List<Comment> findByTaskId(Long taskId) {
        return em.createQuery(
                        "SELECT c FROM Comment c WHERE c.task.id = :taskId", Comment.class)
                .setParameter("taskId", taskId)
                .getResultList();
    }

    public List<Comment> findAll() {
        return em.createQuery("SELECT c FROM Comment c", Comment.class)
                .getResultList();
    }

    public Comment saveComment(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);  // INSERT
            return comment;
        } else {
            return em.merge(comment);  // UPDATE
        }
    }
}
