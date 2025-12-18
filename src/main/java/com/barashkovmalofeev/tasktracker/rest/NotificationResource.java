package com.barashkovmalofeev.tasktracker.rest;

import com.barashkovmalofeev.tasktracker.model.dto.CommentResponseDTO;
import com.barashkovmalofeev.tasktracker.model.dto.NotificationResponseDTO;
import com.barashkovmalofeev.tasktracker.model.dto.TaskResponseDTO;
import com.barashkovmalofeev.tasktracker.model.entity.Notification;
import com.barashkovmalofeev.tasktracker.service.CommentService;
import com.barashkovmalofeev.tasktracker.service.NotificationService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/notifications")
public class NotificationResource {

    @EJB
    private NotificationService notificationService;

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentsByTaskId(@PathParam("userId") Long userId) {
        if (userId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("User ID cookie is required")
                    .build();
        }

        List<Notification> notifications =
                notificationService.getTasksByAssignedUser(userId);

        List<NotificationResponseDTO> notificationResponseDTOS = notifications.stream()
                .map(notification -> new NotificationResponseDTO(notification))
                .collect(Collectors.toList());
        return Response.ok(notificationResponseDTOS).build();
    }
}
