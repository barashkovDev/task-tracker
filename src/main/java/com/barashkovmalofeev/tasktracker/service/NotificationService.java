package com.barashkovmalofeev.tasktracker.service;

import com.barashkovmalofeev.tasktracker.model.dto.NotificationCreateDTO;
import com.barashkovmalofeev.tasktracker.model.entity.Notification;
import com.barashkovmalofeev.tasktracker.model.entity.Task;
import com.barashkovmalofeev.tasktracker.model.entity.User;
import com.barashkovmalofeev.tasktracker.repository.NotificationRepository;
import com.barashkovmalofeev.tasktracker.repository.ProjectRepository;
import com.barashkovmalofeev.tasktracker.repository.UserRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Stateless
public class NotificationService {

    @EJB
    private NotificationRepository notificationRepository;

    @EJB
    private UserRepository userRepository;

    public List<Notification> getTasksByAssignedUser(Long userId) {
        List<Notification> notifications = notificationRepository.findByAssignedUserId(userId);
        return notifications;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Notification createNotification(NotificationCreateDTO notificationCreateDTO) {
        Notification notification = new Notification();
        notification.setText(notificationCreateDTO.getText());
        if(notificationCreateDTO.getAssignedUserId() != null) {
            User user = userRepository.findById(notificationCreateDTO.getAssignedUserId());
            if (user == null) {
                throw new EntityNotFoundException("User not found with id: " + notificationCreateDTO.getAssignedUserId());
            }
            notification.setAssignedUser(user);
        }

        return notificationRepository.saveNotification(notification);
    }
}
