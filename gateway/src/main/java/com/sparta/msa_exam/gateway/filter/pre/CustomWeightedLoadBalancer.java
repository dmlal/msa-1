package com.sparta.msa_exam.gateway.filter.pre;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class CustomWeightedLoadBalancer implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String originalUri = exchange.getRequest().getURI().toString();

        double randomWeight = Math.random();
        int port = (randomWeight < 0.7) ? 19093 : 19094;

        String newUri = originalUri.replace("lb://product", "http://localhost:" + port);

        exchange.getAttributes().put("custom-port", String.valueOf(port));

        ServerHttpRequest httpRequest = exchange.getRequest().mutate().uri(URI.create(newUri)).build();
        return chain.filter(exchange.mutate().request(httpRequest).build());
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 5;
    }
}
