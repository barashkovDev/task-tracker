package com.barashkovmalofeev.tasktracker.rest;

import com.barashkovmalofeev.tasktracker.model.dto.CommentCreateDTO;
import com.barashkovmalofeev.tasktracker.model.dto.CommentResponseDTO;
import com.barashkovmalofeev.tasktracker.model.dto.ProjectCreateDTO;
import com.barashkovmalofeev.tasktracker.model.dto.ProjectResponseDTO;
import com.barashkovmalofeev.tasktracker.model.entity.Comment;
import com.barashkovmalofeev.tasktracker.model.entity.Project;
import com.barashkovmalofeev.tasktracker.service.CommentService;
import com.barashkovmalofeev.tasktracker.service.ProjectService;

import javax.ejb.EJB;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/comments")
public class CommentResource {

    @EJB
    private CommentService commentService;

    @GET
    @Path("/{taskId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentsByTaskId(@PathParam("taskId") Long taskId) {
        List<CommentResponseDTO> comments = commentService.getCommentsByTaskId(taskId);

        return Response.ok(comments).build();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response createComment(CommentCreateDTO commentCreateDTO){
        try {

            Comment comment = commentService.createComment(commentCreateDTO);

            // Возвращаем DTO для ответа
            CommentResponseDTO commentResponseDTO = new CommentResponseDTO(comment);

            return Response.status(Response.Status.CREATED)
                    .entity(commentResponseDTO)
                    .build();

        } catch (Exception e) {
            return Response.ok().build();
        }
    }
}
