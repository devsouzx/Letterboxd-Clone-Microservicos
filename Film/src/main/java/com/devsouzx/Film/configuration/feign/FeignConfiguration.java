package com.devsouzx.Film.configuration.feign;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            try {
                RequestAttributes attributes = RequestContextHolder.getRequestAttributes();

                if (attributes == null) {
                    System.out.println("[FeignInterceptor] Nenhum contexto de request encontrado");
                    return;
                }

                HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();

                String loggedUser = request.getHeader("LOGGED_USER_IDENTIFIER");
                if (loggedUser != null) {
                    template.header("LOGGED_USER_IDENTIFIER", loggedUser);
                    System.out.println("[FeignInterceptor] LOGGED_USER_IDENTIFIER propagado: " + loggedUser);
                } else {
                    System.out.println("[FeignInterceptor] LOGGED_USER_IDENTIFIER não encontrado");
                }

                // Propaga Authorization se necessário
                String authorization = request.getHeader("Authorization");
                if (authorization != null) {
                    template.header("Authorization", authorization);
                    System.out.println("[FeignInterceptor] Authorization propagado");
                }

            } catch (Exception e) {
                System.err.println("[FeignInterceptor] Erro ao interceptar headers: " + e.getMessage());
            }
        };
    }
}
