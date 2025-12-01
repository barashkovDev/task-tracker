package com.barashkovmalofeev.tasktracker.service;

import com.barashkovmalofeev.tasktracker.model.entity.Task;
import com.barashkovmalofeev.tasktracker.repository.TaskRepository;

// В классе TaskService
import javax.ejb.EJB; // Для внедрения EJB, если репозиторий - EJB
import javax.inject.Inject; // Для внедрения CDI Beans
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class TaskService {

    // Если TaskRepository помечен как @Stateless (EJB)
    @EJB
    private TaskRepository taskRepository;

    public List<Task> getTasksByAssignedUser(Long userId) {
        // Вызываем метод репозитория

        return taskRepository.findByAssignedUserId(userId);
    }
}
