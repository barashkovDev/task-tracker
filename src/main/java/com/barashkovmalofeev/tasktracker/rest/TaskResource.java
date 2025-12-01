package com.barashkovmalofeev.tasktracker.rest;

import com.barashkovmalofeev.tasktracker.model.dto.TaskDTO;
import com.barashkovmalofeev.tasktracker.model.entity.Task;
import com.barashkovmalofeev.tasktracker.service.TaskService;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/tasks")
public class TaskResource {
    @EJB // Предполагая, что TaskService - это EJB
    private TaskService taskService;

    @GET
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByUserId(@PathParam("userId") Long userId) {

        List<Task> tasks = taskService.getTasksByAssignedUser(userId);

        if (tasks.isEmpty()) {
            // Возвращаем HTTP 404
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<TaskDTO> taskDTOs = tasks.stream()
                .map(task -> new TaskDTO(task))
                .collect(Collectors.toList());

        return Response.ok(taskDTOs).build();
    }

}
