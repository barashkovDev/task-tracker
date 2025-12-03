package com.barashkovmalofeev.tasktracker.rest;

import com.barashkovmalofeev.tasktracker.model.dto.TaskCreateDTO;
import com.barashkovmalofeev.tasktracker.model.dto.TaskResponseDTO;
import com.barashkovmalofeev.tasktracker.model.entity.Task;
import com.barashkovmalofeev.tasktracker.service.TaskService;

import javax.ejb.EJB;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
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

    // Реализовать удаление таски
    // Вопрос: Получаем от клиента id или полноценную таску
    //public Response deleteTask(Long id)


}
