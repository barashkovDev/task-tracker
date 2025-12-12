package com.barashkovmalofeev.tasktracker.rest;

import com.barashkovmalofeev.tasktracker.model.dto.ProjectCreateDTO;
import com.barashkovmalofeev.tasktracker.model.dto.ProjectResponseDTO;
import com.barashkovmalofeev.tasktracker.model.dto.TaskCreateDTO;
import com.barashkovmalofeev.tasktracker.model.dto.TaskResponseDTO;
import com.barashkovmalofeev.tasktracker.model.entity.Project;
import com.barashkovmalofeev.tasktracker.model.entity.Task;
import com.barashkovmalofeev.tasktracker.model.entity.User;
import com.barashkovmalofeev.tasktracker.service.ProjectService;
import com.barashkovmalofeev.tasktracker.service.TaskService;
import com.barashkovmalofeev.tasktracker.service.UserService;

import javax.ejb.EJB;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/projects")
public class ProjectResource {
    @EJB
    private ProjectService projectService;

    @EJB
    private UserService userService;

    @Context
    private HttpServletRequest req;

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjectsByUserId() {
        User user = userService.getCurrentUser(req);

        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"unauthorized\"}")
                    .build();
        }
        List<ProjectResponseDTO> projects = projectService.getProjectsByAssignedUser(user.getId());

        return Response.ok(projects).build();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response createProject(ProjectCreateDTO projectCreateDTO){
        try {

            Project project = projectService.createProject(projectCreateDTO);

            // Возвращаем DTO для ответа
            ProjectResponseDTO responseDTO = new ProjectResponseDTO(project);

            return Response.status(Response.Status.CREATED)
                    .entity(responseDTO)
                    .build();

        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User or project not found: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error creating task: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{projectId}")
    public Response addUserToProject(@PathParam("projectId") Long projectId,  Map<String, Object> userData) {
        try {
            String username = (String) userData.get("username");

            if(projectService.addUserToProject(projectId, username)) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("User not found")
                        .build();
            }
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User or project not found: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error creating task: " + e.getMessage())
                    .build();
        }


    }

    @DELETE
    @Path("/{projectId}")
    public Response deleteProject(@PathParam("projectId") Long projectId) {
        try {
            projectService.deleteTaskById(projectId);

            return Response.status(Response.Status.OK)
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting project: " + e.getMessage())
                    .build();
        }
    }


}
