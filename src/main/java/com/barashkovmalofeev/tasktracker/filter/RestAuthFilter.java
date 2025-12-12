package com.barashkovmalofeev.tasktracker.filter;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

@Provider
@Priority(Priorities.AUTHENTICATION)   // запускается до ресурсов
public class RestAuthFilter implements ContainerRequestFilter {

    @Context
    private HttpServletRequest req;

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        String path = ctx.getUriInfo().getPath();

        // Разрешаем анонимный доступ к некоторым REST-эндпоинтам, если нужно
        if (path.startsWith("rest/public")) {
            return;
        }

        Principal p = req.getUserPrincipal();
        if (p == null) {
            ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"unauthorized\"}")
                    .build());
            return;
        }

        if (!req.isUserInRole("employee")) {
            ctx.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"error\":\"forbidden\"}")
                    .build());
        }
    }
}
