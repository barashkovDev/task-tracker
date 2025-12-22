package com.barashkovmalofeev.tasktracker.rest;

import com.barashkovmalofeev.tasktracker.model.dto.CommentResponseDTO;
import com.barashkovmalofeev.tasktracker.model.dto.NotificationResponseDTO;
import com.barashkovmalofeev.tasktracker.model.dto.TaskCreateDTO;
import com.barashkovmalofeev.tasktracker.model.dto.TaskResponseDTO;
import com.barashkovmalofeev.tasktracker.model.entity.Notification;
import com.barashkovmalofeev.tasktracker.model.entity.User;
import com.barashkovmalofeev.tasktracker.model.entity.Task;
import com.barashkovmalofeev.tasktracker.repository.NotificationRepository;
import com.barashkovmalofeev.tasktracker.service.CommentService;
import com.barashkovmalofeev.tasktracker.service.NotificationService;
import com.barashkovmalofeev.tasktracker.service.UserService;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/notifications")
public class NotificationResource {
    @EJB
    private UserService userService;

    @EJB
    private NotificationRepository notificationRepository;

    @EJB
    private NotificationService notificationService;

    @Context
    private HttpServletRequest req;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentsByTaskId() {
        User user = userService.getCurrentUser(req);

        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"unauthorized\"}")
                    .build();
        }

        List<Notification> notifications =
                notificationService.getTasksByAssignedUser(user.getId());

        List<NotificationResponseDTO> notificationResponseDTOS = notifications.stream()
                .map(notification -> new NotificationResponseDTO(notification))
                .collect(Collectors.toList());
        return Response.ok(notificationResponseDTOS).build();
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{notificationId}")
    public Response updateTask(@PathParam("notificationId") Long notificationId, Map<String, Object> data) {

        try {
            Boolean isRead = (Boolean) data.get("isRead");
            Notification notification = notificationRepository.findById(notificationId);
            notification.setRead(isRead);
            Notification savedNotification = notificationRepository.saveNotification(notification);
            NotificationResponseDTO notificationResponseDTO = new NotificationResponseDTO(savedNotification);
            return Response.status(Response.Status.CREATED)
                    .entity(notificationResponseDTO)
                    .build();

        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Notification not found: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating Notification: " + e.getMessage())
                    .build();
        }
    }
}
