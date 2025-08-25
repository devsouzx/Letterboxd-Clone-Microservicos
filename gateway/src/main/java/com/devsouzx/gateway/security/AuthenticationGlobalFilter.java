package com.devsouzx.gateway.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

@Component
public class AuthenticationGlobalFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationGlobalFilter.class);

    private final JwtUtil jwtUtil;

    public AuthenticationGlobalFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/v1/accounts/sign-in",
            "/api/v1/accounts/register",
            "/api/v1/tmdb/movie/popular",
            "/api/v1/tmdb/movie/now_playing",
            "/eureka/**"
    );

    private final Predicate<String> isSecured = path ->
            PUBLIC_PATHS.stream().noneMatch(path::startsWith);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Processing request to: {}", exchange.getRequest().getPath());

        if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
            return chain.filter(exchange);
        }

        String path = exchange.getRequest().getURI().getPath();
        log.info("Path: {}", path);

        if (!isSecured.test(path)) {
            log.info("Public path - allowing access");
            return chain.filter(exchange);
        }

        List<String> authHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (authHeaders == null || authHeaders.isEmpty() || !authHeaders.get(0).startsWith("Bearer ")) {
            log.warn("No authorization header found");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        try {
            String token = authHeaders.get(0).substring(7);
            log.info("Validating token: {}", token);

            jwtUtil.validateToken(token);
            String username = jwtUtil.extractUsername(token);

            log.info("Authenticated user: {}", username);

            exchange = exchange.mutate()
                    .request(r -> r.header("LOGGED_USER_IDENTIFIER", username))
                    .build();

            return chain.filter(exchange);
        } catch (Exception ex) {
            log.error("Authentication failed", ex);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}