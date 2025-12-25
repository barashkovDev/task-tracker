package com.barashkovmalofeev.tasktracker.repository;

import com.barashkovmalofeev.tasktracker.model.entity.Project;
import com.barashkovmalofeev.tasktracker.model.entity.Task;
import com.barashkovmalofeev.tasktracker.model.entity.User;
import org.jboss.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
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
            em.persist(project);
            return project;
        } else {
            return em.merge(project);
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
        Project project = em.find(Project.class, projectId);
        if (project == null) return;

        for (User user : project.getUsers()) {
            user.getProjects().remove(project);
        }
        project.getUsers().clear();

        em.createQuery("DELETE FROM Task t WHERE t.project = :project")
                .setParameter("project", project)
                .executeUpdate();

        em.remove(project);

    }

    public void addUserToProject(Long projectId, Long userId) {
        Project project = em.find(Project.class, projectId);
        User user = em.find(User.class, userId);

        if (project == null) {
            throw new EntityNotFoundException("Project not found with id: " + projectId);
        }
        if (user == null) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }

        if (!project.getUsers().contains(user)) {
            project.getUsers().add(user);
            user.getProjects().add(project);
            em.merge(project);
            em.merge(user);

            LOG.infof("User ID: %d added to Project ID: %d", userId, projectId);
        } else {
            LOG.infof("User ID: %d is already in Project ID: %d", userId, projectId);
        }
    }


    public void deleteUserFromProject(Long projectId, Long userId) {
        Project project = em.find(Project.class, projectId);
        User user = em.find(User.class, userId);

        if (project == null) {
            throw new EntityNotFoundException("Project not found with id: " + projectId);
        }
        if (user == null) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }

        if (!project.getUsers().contains(user)) {
            LOG.warnf("Пользователь ID: %d не состоит в проекте ID: %d", userId, projectId);
            throw new IllegalArgumentException("User is not assigned to this project");
        }

        boolean removedFromUser = user.getProjects().remove(project);
        boolean removedFromProject = project.getUsers().remove(user);

        em.merge(user);
    }
}
