package com.barashkovmalofeev.tasktracker.service;

import com.barashkovmalofeev.tasktracker.model.dto.CommentCreateDTO;
import com.barashkovmalofeev.tasktracker.model.dto.CommentResponseDTO;
import com.barashkovmalofeev.tasktracker.model.dto.ProjectResponseDTO;
import com.barashkovmalofeev.tasktracker.model.entity.Comment;
import com.barashkovmalofeev.tasktracker.model.entity.Task;
import com.barashkovmalofeev.tasktracker.model.entity.User;
import com.barashkovmalofeev.tasktracker.repository.CommentRepository;
import com.barashkovmalofeev.tasktracker.repository.TaskRepository;
import com.barashkovmalofeev.tasktracker.repository.UserRepository;
import org.jboss.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class CommentService {
    @EJB
    private TaskRepository taskRepository;

    @EJB
    private UserRepository userRepository;

    @EJB
    private CommentRepository commentRepository;

    private static final Logger LOG = Logger.getLogger(ProjectService.class);

    public List<CommentResponseDTO> getCommentsByTaskId(Long taskId) {
        List<Comment> comments = commentRepository.findByTaskId(taskId);

        return comments.stream().map(
                comment -> {
                    return new CommentResponseDTO(
                        comment.getId(),
                        comment.getText(),
                        comment.getAuthor().getId(),
                        comment.getAuthor().getUsername(),
                        comment.getProductionDate()
                    );
                }
        ).collect(Collectors.toList());
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Comment createComment(CommentCreateDTO commentCreateDTO) {
        Comment comment = new Comment();
        comment.setText(commentCreateDTO.getText());
        comment.setProductionDate(LocalDateTime.now());

        if(commentCreateDTO.getAuthorId() != null) {
            User user = userRepository.findById(commentCreateDTO.getAuthorId());
            if (user == null) {
                throw new EntityNotFoundException("User not found with id: " + commentCreateDTO.getAuthorId());
            }
            comment.setAuthor(user);
        }

        if(commentCreateDTO.getTaskId() != null) {
            Task task = taskRepository.findById(commentCreateDTO.getTaskId());
            if (task == null) {
                throw new EntityNotFoundException("User not found with id: " + commentCreateDTO.getAuthorId());
            }
            comment.setTask(task);
        }

        return commentRepository.saveComment(comment);
    }
}
