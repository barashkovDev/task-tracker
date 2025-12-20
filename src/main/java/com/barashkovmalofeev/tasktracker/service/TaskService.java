package com.barashkovmalofeev.tasktracker.service;

import com.barashkovmalofeev.tasktracker.model.dto.TaskCreateDTO;
import com.barashkovmalofeev.tasktracker.model.dto.TaskFilterDto;
import com.barashkovmalofeev.tasktracker.model.entity.Project;
import com.barashkovmalofeev.tasktracker.model.entity.Task;
import com.barashkovmalofeev.tasktracker.model.entity.User;
import com.barashkovmalofeev.tasktracker.model.enums.TaskStatus;
import com.barashkovmalofeev.tasktracker.repository.ProjectRepository;
import com.barashkovmalofeev.tasktracker.repository.TaskRepository;
import com.barashkovmalofeev.tasktracker.repository.UserRepository;

// В классе TaskService
import javax.ejb.EJB; // Для внедрения EJB, если репозиторий - EJB
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class TaskService {

    @EJB
    private TaskRepository taskRepository;

    @EJB
    private UserRepository userRepository;

    @EJB
    private ProjectRepository projectRepository;

    @PersistenceContext(unitName = "taskTrackerPU")
    private EntityManager em;

    public List<Task> getTasksByAssignedUser(Long userId) {
        return taskRepository.findByAssignedUserId(userId);
    }

    public List<Task> getTasksByProject(Long projectId) {
        return taskRepository.findByProject(projectId);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Task createTask(TaskCreateDTO taskDTO) {
        Task task = new Task();
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setPriority(taskDTO.getPriority());
        task.setComplexity(taskDTO.getComplexity());
        task.setStatus(TaskStatus.NEW);
        task.setTaskCompleted(false);
        task.setProductionDate(LocalDate.now());
        task.setEndDate(taskDTO.getEndDate());

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

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    public Task updateTask(Long taskId, TaskCreateDTO taskDTO) {
        Task task = new Task();
        task.setId(taskId);
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setPriority(taskDTO.getPriority());
        task.setStatus(taskDTO.getStatus());
        task.setComplexity(taskDTO.getComplexity());
        task.setTaskCompleted(false);
        task.setEndDate(taskDTO.getEndDate());
        if(taskDTO.getStatus().equals(TaskStatus.DONE)) {
            task.setEndDate(LocalDate.now());
            //task.setSpentTime(Duration.between(task.getProductionDate(), task.getEndDate()));
        }
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
        return  taskRepository.saveTask(task);
    }

    public List<Task> findTasksByProjectWithFilters(Long projectId, TaskFilterDto filter) {
        // Начинаем построение JPQL запроса
        StringBuilder jpql = new StringBuilder(
                "SELECT DISTINCT t FROM Task t " +
                        "LEFT JOIN t.comments c " +
                        "WHERE t.project.id = :projectId "
        );

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("projectId", projectId);

        // Добавляем условия фильтрации
        if (filter.getDescription() != null && !filter.getDescription().trim().isEmpty()) {
            jpql.append("AND LOWER(t.description) LIKE LOWER(:description) ");
            parameters.put("description", "%" + filter.getDescription().trim() + "%");
        }

        if (filter.getName() != null && !filter.getName().trim().isEmpty()) {
            jpql.append("AND LOWER(t.name) LIKE LOWER(:name) ");
            parameters.put("name", "%" + filter.getName().trim() + "%");
        }

        if (filter.getStatus() != null) {
            jpql.append("AND t.status = :status ");
            parameters.put("status", filter.getStatus());
        }

        if (filter.getComment() != null && !filter.getComment().trim().isEmpty()) {
            jpql.append("AND LOWER(c.text) LIKE LOWER(:comment) ");
            parameters.put("comment", "%" + filter.getComment().trim() + "%");
        }

        jpql.append("ORDER BY t.productionDate DESC");

        // Создаем и выполняем запрос
        TypedQuery<Task> query = em.createQuery(jpql.toString(), Task.class);

        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.getResultList();
    }

    public List<Task> findTasksByUserWithFilters(Long userId, TaskFilterDto filter) {
        // Начинаем построение JPQL запроса
        StringBuilder jpql = new StringBuilder(
                "SELECT DISTINCT t FROM Task t " +
                        "LEFT JOIN t.comments c " +
                        "WHERE t.assignedUser.id = :userId "
        );

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userId", userId);

        // Добавляем условия фильтрации
        if (filter.getDescription() != null && !filter.getDescription().trim().isEmpty()) {
            jpql.append("AND LOWER(t.description) LIKE LOWER(:description) ");
            parameters.put("description", "%" + filter.getDescription().trim() + "%");
        }

        if (filter.getName() != null && !filter.getName().trim().isEmpty()) {
            jpql.append("AND LOWER(t.name) LIKE LOWER(:name) ");
            parameters.put("name", "%" + filter.getName().trim() + "%");
        }

        if (filter.getStatus() != null) {
            jpql.append("AND t.status = :status ");
            parameters.put("status", filter.getStatus());
        }

        if (filter.getComment() != null && !filter.getComment().trim().isEmpty()) {
            jpql.append("AND LOWER(c.text) LIKE LOWER(:comment) ");
            parameters.put("comment", "%" + filter.getComment().trim() + "%");
        }

        jpql.append("ORDER BY t.productionDate DESC");

        // Создаем и выполняем запрос
        TypedQuery<Task> query = em.createQuery(jpql.toString(), Task.class);

        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.getResultList();
    }
}
