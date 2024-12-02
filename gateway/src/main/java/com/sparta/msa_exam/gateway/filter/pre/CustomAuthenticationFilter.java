package com.sparta.msa_exam.gateway.filter.pre;

import com.sparta.msa_exam.gateway.common.exception.CustomException;
import com.sparta.msa_exam.gateway.common.exception.ErrorCode;
import com.sparta.msa_exam.gateway.config.JwtProvider;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CustomAuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtProvider jwtProvider;

    public CustomAuthenticationFilter(JwtProvider jwtProvider) {
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
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        String token = authHeader.substring(7);

        if (!jwtProvider.validateToken(token)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        Claims claims = jwtProvider.parseToken(token);
        Integer userId = claims.get("userId", Integer.class);
        String userRole = claims.get("userRole", String.class);

        ServerHttpRequest httpRequest = exchange.getRequest().mutate()
                .header("X-User-Id", userId.toString())
                .header("X-User-Role", userRole)
                .build();

        return chain.filter(exchange.mutate().request(httpRequest).build());
    }


    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
