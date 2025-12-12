package com.barashkovmalofeev.tasktracker.rest;

import com.barashkovmalofeev.tasktracker.model.dto.TaskCreateDTO;
import com.barashkovmalofeev.tasktracker.model.dto.TaskResponseDTO;
import com.barashkovmalofeev.tasktracker.model.entity.Task;
import com.barashkovmalofeev.tasktracker.model.entity.User;
import com.barashkovmalofeev.tasktracker.service.TaskService;
import com.barashkovmalofeev.tasktracker.service.UserService;

import javax.ejb.EJB;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/tasks")
public class TaskResource {
    @EJB
    private TaskService taskService;


    @EJB
    private UserService userService;

    @Context
    private HttpServletRequest req;

    @GET
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByUserId(@PathParam("userId") Long userId) {

        List<Task> tasks = taskService.getTasksByAssignedUser(userId);


        List<TaskResponseDTO> taskResponseDTOS = tasks.stream()
                .map(task -> new TaskResponseDTO(task))
                .collect(Collectors.toList());

        return Response.ok(taskResponseDTOS).build();
    }

    @GET
    @Path("/project/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByProjectId(@PathParam("projectId") Long projectId) {

        List<Task> tasks = taskService.getTasksByProject(projectId);


        List<TaskResponseDTO> taskResponseDTOS = tasks.stream()
                .map(task -> new TaskResponseDTO(task))
                .collect(Collectors.toList());

        return Response.ok(taskResponseDTOS).build();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response createTask(TaskCreateDTO taskDTO){
        try {
            // Создаем Task из DTO
            Task task = taskService.createTask(taskDTO);

            // Возвращаем DTO для ответа
            TaskResponseDTO responseDTO = new TaskResponseDTO(task);

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

    @DELETE
    @Path("/{taskId}")
    public Response deleteTask(@PathParam("taskId") Long taskId) {
        try {
            taskService.deleteTaskById(taskId);

            return Response.status(Response.Status.OK)
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting task: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByUserIdCookie() {
        User user = userService.getCurrentUser(req);
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"unauthorized\"}")
                    .build();
        }

        List<Task> tasks = taskService.getTasksByAssignedUser(user.getId());


        List<TaskResponseDTO> taskResponseDTOS = tasks.stream()
                .map(task -> new TaskResponseDTO(task))
                .collect(Collectors.toList());

        return Response.ok(taskResponseDTOS).build();
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{taskId}")
    public Response updateTask(@PathParam("taskId") Long taskId, TaskCreateDTO taskCreateDTO) {

        try {
            // Создаем Task из DTO
            Task updatedTask = taskService.updateTask(taskId, taskCreateDTO);

            // Возвращаем DTO для ответа
            TaskResponseDTO responseDTO = new TaskResponseDTO(updatedTask);

            return Response.status(Response.Status.CREATED)
                    .entity(responseDTO)
                    .build();

        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User or project not found: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating task: " + e.getMessage())
                    .build();
        }
    }



}
