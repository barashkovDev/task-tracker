package com.barashkovmalofeev.tasktracker.repository;

import com.barashkovmalofeev.tasktracker.model.entity.Task;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless // Делает этот класс управляемым контейнером EJB, обеспечивая транзакции
public class TaskRepository {

    // Внедрение EntityManager для работы с БД
    @PersistenceContext(unitName = "taskTrackerPU") // Имя Persistence Unit
    private EntityManager em;

    // Метод для поиска задач по ID пользователя
    public List<Task> findByAssignedUserId(Long userId) {
        // Использование JPA QL (JPQL) для запроса
        System.out.println("Received userId: " + userId);
        return em.createQuery(
                        "SELECT t FROM Task t WHERE t.assignedUser.id = :userId", Task.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public Task saveTask(Task task) {
        if (task.getId() == null) {
            em.persist(task);  // INSERT
            return task;
        } else {
            return em.merge(task);  // UPDATE
        }
    }

    public Task findById(Long id) {
        return em.find(Task.class, id);
    }

}
