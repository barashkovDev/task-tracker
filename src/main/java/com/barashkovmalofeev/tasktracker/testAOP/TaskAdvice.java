package com.barashkovmalofeev.tasktracker.testAOP;

import com.barashkovmalofeev.tasktracker.model.dto.TaskCreateDTO;
import com.barashkovmalofeev.tasktracker.model.dto.TaskResponseDTO;
import com.barashkovmalofeev.tasktracker.model.entity.Notification;
import com.barashkovmalofeev.tasktracker.model.entity.Task;
import com.barashkovmalofeev.tasktracker.model.entity.User;
import com.barashkovmalofeev.tasktracker.repository.NotificationRepository;
import com.barashkovmalofeev.tasktracker.repository.TaskRepository;
import com.barashkovmalofeev.tasktracker.repository.UserRepository;
import com.barashkovmalofeev.tasktracker.service.NotificationService;
import com.barashkovmalofeev.tasktracker.service.UserService;
import org.jboss.logging.Logger;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entered
@Interceptor
public class TaskAdvice implements Serializable {

    @EJB
    private UserRepository userRepository;
    @EJB
    private TaskRepository taskRepository;
    @EJB
    private NotificationRepository notificationRepository;

    private static final Logger LOGGER = Logger.getLogger(TaskAdvice.class.toString());

    @AroundInvoke
    protected Object protocolInvocation(final InvocationContext invocationContext) throws Exception {

        String methodName = invocationContext.getMethod().getName();
        Object[] params = invocationContext.getParameters();

        Object result = null;
        Object[] oldStateForUpdate = null;

        if ("updateTask".equals(methodName) && params != null && params.length >= 2) {
            if (params[0] instanceof Long && params[1] instanceof TaskCreateDTO) {
                Long taskId = (Long) params[0];
                Task oldTaskBeforeProceed = taskRepository.findById(taskId);
                oldStateForUpdate = new Object[]{oldTaskBeforeProceed};
            }
        }

        result = invocationContext.proceed();

        LOGGER.infof("Метод: %s", invocationContext.getMethod().getName());
        LOGGER.infof("Класс: %s", invocationContext.getMethod().getDeclaringClass().getName());

        if ("createTask".equals(methodName)) {
            handleCreateTask(result, invocationContext);
        } else if ("updateTask".equals(methodName)) {
            handleUpdateTask(invocationContext, oldStateForUpdate);
        }

        return result;
    }

    private void handleCreateTask(Object result, InvocationContext invocationContext) {
        LOGGER.info("Task created");
        if (result instanceof Response) {
            Response response = (Response) result;
            Object responseEntity = response.getEntity();
            if (responseEntity instanceof TaskResponseDTO) {
                TaskResponseDTO createdTask = (TaskResponseDTO) responseEntity;
                Notification notification = new Notification();
                notification.setAssignedUser(userRepository.findById(createdTask.getAssignedUserId()));
                notification.setText("На вас назначили задачу");
                notification.setTaskName(createdTask.getName());
                notification.setProductionDate(LocalDate.now());
                notification.setRead(false);
                notificationRepository.saveNotification(notification);
            }
        }
    }

    private void handleUpdateTask(InvocationContext ctx, Object[] oldStateInfo) {
        LOGGER.info("Task updated");
        Object[] params = ctx.getParameters();
        if (params == null || params.length < 2) {
            return;
        }

        Long taskId;
        TaskCreateDTO taskDTO;

        try {
            taskId = (Long) params[0];
            taskDTO = (TaskCreateDTO) params[1];
        } catch (ClassCastException e) {
            return;
        }

        // Получаем старую задачу из сохраненного состояния
        Task oldTask = null;
        if (oldStateInfo != null && oldStateInfo.length > 0 && oldStateInfo[0] instanceof Task) {
            oldTask = (Task) oldStateInfo[0];
        }

        if (oldTask == null) {
            LOGGER.info("Не удалось получить старое состояние задачи с ID: " + taskId);
            return;
        }

        Long oldUserId = (oldTask.getAssignedUser() != null) ? oldTask.getAssignedUser().getId() : null;
        Long newUserId = taskDTO.getAssignedUserId();

        if (!Objects.equals(oldUserId, newUserId)) {
            if (newUserId != null) {
                User newAssignedUser = userRepository.findById(newUserId);
                if (newAssignedUser != null) {
                    Notification notification = new Notification();
                    notification.setAssignedUser(newAssignedUser);
                    notification.setText("На вас переназначили задачу");
                    notification.setTaskName(oldTask.getName());
                    notification.setProductionDate(LocalDate.now());
                    notification.setRead(false);
                    notificationRepository.saveNotification(notification);
                }
            }
            else if (oldUserId != null) {
                User oldAssignedUser = userRepository.findById(oldUserId);
                if (oldAssignedUser != null) {
                    Notification notification = new Notification();
                    notification.setAssignedUser(oldAssignedUser);
                    notification.setText("Задача снята с вас");
                    notification.setTaskName(oldTask.getName());
                    notification.setProductionDate(LocalDate.now());
                    notification.setRead(false);
                    notificationRepository.saveNotification(notification);
                }
            }
        }
    }

}
