package com.barashkovmalofeev.tasktracker.repository;

import com.barashkovmalofeev.tasktracker.model.entity.Comment;
import com.barashkovmalofeev.tasktracker.model.entity.Notification;
import com.barashkovmalofeev.tasktracker.model.entity.Task;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class NotificationRepository {

    @PersistenceContext(unitName = "taskTrackerPU")
    private EntityManager em;

    public List<Notification> findByAssignedUserId(Long userId) {
        return em.createQuery(
                        "SELECT t FROM Notification t WHERE t.assignedUser.id = :userId", Notification.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public Notification saveNotification(Notification notification) {
        if (notification.getId() == null) {
            em.persist(notification);  // INSERT
            return notification;
        } else {
            return em.merge(notification);  // UPDATE
        }
    }
}
