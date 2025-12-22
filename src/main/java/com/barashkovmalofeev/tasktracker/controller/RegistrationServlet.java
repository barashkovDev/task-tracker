package com.barashkovmalofeev.tasktracker.controller;



import com.barashkovmalofeev.tasktracker.model.entity.User;
import com.barashkovmalofeev.tasktracker.service.UserService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/api/register")
public class RegistrationServlet extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Показываем форму регистрации
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Простейшая проверка (дополнительные проверки должны быть в UserService)
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Username and password cannot be empty.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        try {
            // 2. Вызов сервиса для регистрации
            User newUser = userService.createUser(username, password);

            // 3. Обработка успеха
            request.getSession().setAttribute("success", "User " + newUser.getUsername() + " registered successfully!");
            // Перенаправляем на главную или страницу входа, чтобы избежать двойной отправки формы (Post/Redirect/Get)
            Cookie cookie = new Cookie("userId", newUser.getId().toString());

            cookie.setPath("/");

            cookie.setMaxAge(7 * 24 * 60 * 60);

            request.login(username, password);

            response.addCookie(cookie);
            response.sendRedirect("/");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }


}
