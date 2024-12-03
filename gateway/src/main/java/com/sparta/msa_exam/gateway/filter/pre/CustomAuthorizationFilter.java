package com.sparta.msa_exam.gateway.filter.pre;

import com.sparta.msa_exam.gateway.common.exception.CustomException;
import com.sparta.msa_exam.gateway.common.exception.ErrorCode;
import com.sparta.msa_exam.gateway.config.JwtProvider;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CustomAuthorizationFilter implements GlobalFilter, Ordered {

    private final JwtProvider jwtProvider;

    public CustomAuthorizationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        if (path.contains("/auth/signUp") || path.contains("/auth/signIn")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        if (isAdminURI(path) && !jwtProvider.isAdmin(token)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        return chain.filter(exchange);
    }

    private boolean isAdminURI(String path) {
        return ((path.startsWith("/products/admin")) || path.startsWith("/orders/admin"));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
