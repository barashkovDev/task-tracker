package com.barashkovmalofeev.tasktracker.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class SpaFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();

        // Пропускаем API запросы
        if (path.startsWith("/api/")) {
            chain.doFilter(request, response);
            return;
        }

        // Пропускаем статические ресурсы
        if (isStaticResource(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Все остальные запросы перенаправляем на index.html
        request.getRequestDispatcher("/index.html").forward(request, response);
    }

    private boolean isStaticResource(String path) {
        return path.startsWith("/assets/") ||
                path.endsWith(".js") ||
                path.endsWith(".css") ||
                path.endsWith(".ico") ||
                path.endsWith(".png") ||
                path.endsWith(".jpg") ||
                path.endsWith(".svg");
    }
}