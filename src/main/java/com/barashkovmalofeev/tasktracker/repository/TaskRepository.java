package com.barashkovmalofeev.tasktracker.repository;

import com.barashkovmalofeev.tasktracker.model.entity.Task;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless // Делает этот класс управляемым контейнером EJB, обеспечивая транзакции
public class TaskRepository {

    @PersistenceContext(unitName = "taskTrackerPU")
    private EntityManager em;

    public List<Task> findByAssignedUserId(Long userId) {
        return em.createQuery(
                        "SELECT t FROM Task t WHERE t.assignedUser.id = :userId", Task.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Task> findByProject(Long projectId) {
        return em.createQuery(
                        "SELECT t FROM Task t WHERE t.project.id = :projectId", Task.class)
                .setParameter("projectId", projectId)
                .getResultList();
    }

    public Task saveTask(Task task) {
        if (task.getId() == null) {
            em.persist(task);
            return task;
        } else {
            return em.merge(task);
        }
    }

    public Task findById(Long id) {
        return em.find(Task.class, id);
    }

    public void deleteById(Long id) {
        Task task = em.find(Task.class, id);
        if (task == null)
            return;

        em.createQuery("DELETE FROM Comment c WHERE c.task = :task")
                .setParameter("task", task)
                .executeUpdate();

        em.remove(task);
    }
    public void unassignUserFromProject(Long projectId, Long userId) {
        em.createQuery(
                "UPDATE Task t " +
                        "SET t.assignedUser = NULL " +
                        "WHERE t.assignedUser.id = :userId " +
                        "AND t.project.id = :projectId")
        .setParameter("userId", userId)
        .setParameter("projectId", projectId)
        .executeUpdate();
    }

}
