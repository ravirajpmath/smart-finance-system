package com.smartfinance.gateway.filter;

import com.smartfinance.gateway.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter
        implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        String path =
                exchange.getRequest()
                        .getURI()
                        .getPath();

        // ✅ LOG REQUEST
        log.info(
                "Incoming request for path: {}",
                path
        );

        // ✅ PUBLIC ENDPOINTS
        if (path.startsWith("/api/auth")) {

            log.info(
                    "Public endpoint accessed: {}",
                    path
            );

            return chain.filter(exchange);
        }

        String authHeader =
                exchange.getRequest()
                        .getHeaders()
                        .getFirst("Authorization");

        // ❌ TOKEN MISSING
        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            log.warn(
                    "Missing or invalid Authorization header"
            );

            exchange.getResponse()
                    .setStatusCode(
                            HttpStatus.UNAUTHORIZED
                    );

            return exchange.getResponse()
                    .setComplete();
        }

        String token =
                authHeader.substring(7);

        // ❌ INVALID TOKEN
        if (!jwtUtil.validateToken(token)) {

            log.error(
                    "JWT validation failed"
            );

            exchange.getResponse()
                    .setStatusCode(
                            HttpStatus.UNAUTHORIZED
                    );

            return exchange.getResponse()
                    .setComplete();
        }

        // ✅ EXTRACT USER
        String user =
                jwtUtil.extractUser(token);

        // ✅ EXTRACT ROLE
        String role =
                jwtUtil.extractRole(token);

        log.info(
                "Authenticated user: {}",
                user
        );

        log.info(
                "User role: {}",
                role
        );

        // ✅ FORWARD HEADERS
        ServerWebExchange modifiedExchange =
                exchange.mutate()
                        .request(r -> r
                                .header("X-User", user)
                                .header("X-Role", role)
                        )
                        .build();

        log.info(
                "Forwarding request to downstream service"
        );

        return chain.filter(modifiedExchange);
    }

    @Override
    public int getOrder() {

        return -1;
    }
}