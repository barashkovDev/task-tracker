package com.barashkovmalofeev.tasktracker.service;

import com.barashkovmalofeev.tasktracker.model.dto.ProjectCreateDTO;
import com.barashkovmalofeev.tasktracker.model.dto.ProjectResponseDTO;
import com.barashkovmalofeev.tasktracker.model.dto.TaskCreateDTO;
import com.barashkovmalofeev.tasktracker.model.entity.Project;
import com.barashkovmalofeev.tasktracker.model.entity.Task;
import com.barashkovmalofeev.tasktracker.model.entity.User;
import com.barashkovmalofeev.tasktracker.repository.ProjectRepository;
import com.barashkovmalofeev.tasktracker.repository.TaskRepository;
import com.barashkovmalofeev.tasktracker.repository.UserRepository;
import org.jboss.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
public class ProjectService {
    @EJB
    private TaskRepository taskRepository;

    @EJB
    private UserRepository userRepository;

    @EJB
    private ProjectRepository projectRepository;

    private static final Logger LOG = Logger.getLogger(ProjectService.class);

    public List<ProjectResponseDTO> getProjectsByAssignedUser(Long userId) {
        List<Project> projects = projectRepository.findByAssignedUserId(userId);

        return projects.stream()
                .map(project -> {
                    return new ProjectResponseDTO(
                            project.getId(),
                            project.getName(),
                            project.getUsers()
                    );
                })
                .collect(Collectors.toList());
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Project createProject(ProjectCreateDTO projectCreateDTO) {
        LOG.infof("userIds == null? " + (projectCreateDTO.getUserIds() == null));
        Project project = new Project();
        project.setName(projectCreateDTO.getName());
        if (projectCreateDTO.getUserIds() != null &&
                !projectCreateDTO.getUserIds().isEmpty()) {
            projectRepository.addUsersToProject(project, projectCreateDTO.getUserIds());
        }

        return projectRepository.saveProject(project);
    }

    public void deleteTaskById(Long projectId) {
        projectRepository.deleteProjectById(projectId);
    }

    public boolean addUserToProject(Long projectId, String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }
        projectRepository.addUserToProject(projectId, user.getId());
        return true;

    }
}
