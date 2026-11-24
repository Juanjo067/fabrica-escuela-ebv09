package com.citas.app.CitasBack.config;

import com.citas.app.CitasBack.model.Sesion;
import com.citas.app.CitasBack.service.SesionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

// HU-26 (tarea 205): rechaza con 401 las rutas protegidas si la sesión no es válida.
@Component
public class SesionInterceptor implements HandlerInterceptor {

    @Autowired
    private SesionService sesionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String token = SesionService.extraerToken(request.getHeader("Authorization"));
        Optional<Sesion> sesion = token == null ? Optional.empty() : sesionService.buscarActiva(token);

        if (sesion.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("Sesión inválida o expirada. Inicia sesión de nuevo");
            return false;
        }

        request.setAttribute("sesion", sesion.get());
        return true;
    }
}
