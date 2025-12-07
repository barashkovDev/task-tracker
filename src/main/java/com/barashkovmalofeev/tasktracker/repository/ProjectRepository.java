package com.barashkovmalofeev.tasktracker.repository;

import com.barashkovmalofeev.tasktracker.model.entity.Project;
import com.barashkovmalofeev.tasktracker.model.entity.Task;
import com.barashkovmalofeev.tasktracker.model.entity.User;
import org.jboss.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Stateless
public class ProjectRepository {
    @PersistenceContext(unitName = "taskTrackerPU") // Имя Persistence Unit
    private EntityManager em;

    private static final Logger LOG = Logger.getLogger(ProjectRepository.class);

    public List<Project> findByAssignedUserId(Long userId) {
        return em.createQuery(
                        "SELECT DISTINCT p FROM Project p " +
                                "JOIN p.users u " +  // или JOIN p.assignees, в зависимости от названия поля
                                "WHERE u.id = :userId", Project.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public Project saveProject(Project project) {
        if (project.getId() == null) {
            em.persist(project);  // INSERT
            return project;
        } else {
            return em.merge(project);  // UPDATE
        }
    }

    public Project findById(Long id) {
        return em.find(Project.class, id);
    }

    public void addUsersToProject(Project project, Set<Long> userIds) {
        LOG.infof( "=== id пользователей которые пришли === ");
        for (Long item : userIds) {
            LOG.infof(item + " ");
        }
        List<User> users = em.createQuery(
                        "SELECT u FROM User u WHERE u.id IN :ids", User.class)
                .setParameter("ids", userIds)
                .getResultList();

        LOG.infof( "=== Пользователи которые пришли по запросу === ");
        for (User user : users) {
            LOG.infof(user.getUsername() + " " );
        }
        for (User user : users) {
            user.getProjects().add(project);
            project.getUsers().add(user);
        }
    }


    public void deleteProjectById(Long projectId) {
        // 1. Находим проект
        Project project = em.find(Project.class, projectId);
        if (project == null) return;

        LOG.infof("Удаление проекта ID: %d", projectId);

        // 2. Разрываем связи с пользователями
        for (User user : project.getUsers()) {
            user.getProjects().remove(project); // Удаляем проект из коллекции пользователя
        }
        project.getUsers().clear(); // Очищаем коллекцию проекта

        // 3. Удаляем все задачи проекта (если нужно)
        em.createQuery("DELETE FROM Task t WHERE t.project = :project")
                .setParameter("project", project)
                .executeUpdate();

        // 4. Теперь можно удалить проект
        em.remove(project);

        LOG.infof("Проект ID: %d удалён", projectId);
    }
}
