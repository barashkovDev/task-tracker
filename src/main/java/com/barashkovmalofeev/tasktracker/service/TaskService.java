package com.barashkovmalofeev.tasktracker.service;

import com.barashkovmalofeev.tasktracker.model.dto.TaskCreateDTO;
import com.barashkovmalofeev.tasktracker.model.entity.Project;
import com.barashkovmalofeev.tasktracker.model.entity.Task;
import com.barashkovmalofeev.tasktracker.model.entity.User;
import com.barashkovmalofeev.tasktracker.repository.ProjectRepository;
import com.barashkovmalofeev.tasktracker.repository.TaskRepository;
import com.barashkovmalofeev.tasktracker.repository.UserRepository;

// В классе TaskService
import javax.ejb.EJB; // Для внедрения EJB, если репозиторий - EJB
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class TaskService {

    // Если TaskRepository помечен как @Stateless (EJB)
    @EJB
    private TaskRepository taskRepository;

    @EJB
    private UserRepository userRepository;

    @EJB
    private ProjectRepository projectRepository;

    public List<Task> getTasksByAssignedUser(Long userId) {
        return taskRepository.findByAssignedUserId(userId);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Task createTask(TaskCreateDTO taskDTO) {
        Task task = new Task();
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setPriority(taskDTO.getPriority());
        task.setComplexity(taskDTO.getComplexity());
        task.setStatus("NEW");
        task.setTaskCompleted(false);
        task.setProductionDate(LocalDate.now());

        if (taskDTO.getAssignedUserId() != null) {
            User user = userRepository.findById(taskDTO.getAssignedUserId());
            if (user == null) {
                throw new EntityNotFoundException("User not found with id: " + taskDTO.getAssignedUserId());
            }
            task.setAssignedUser(user);
        }

        // 3. Находим и устанавливаем Project (если указан)
        if (taskDTO.getProjectId() != null) {
            Project project = projectRepository.findById(taskDTO.getProjectId());
            if (project == null) {
                throw new EntityNotFoundException("Project not found with id: " + taskDTO.getProjectId());
            }
            task.setProject(project);
        }

        // 4. Сохраняем
        return taskRepository.saveTask(task);
    }
}
