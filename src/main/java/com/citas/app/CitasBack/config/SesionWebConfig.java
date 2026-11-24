package com.citas.app.CitasBack.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SesionWebConfig implements WebMvcConfigurer {

    @Autowired
    private SesionInterceptor sesionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Rutas que exigen sesión activa. Para proteger más rutas, agrégalas aquí.
        registry.addInterceptor(sesionInterceptor)
                .addPathPatterns("/api/auth/logout", "/api/auth/sesion");
    }
}
