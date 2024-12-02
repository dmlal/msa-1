package com.sparta.msa_exam.gateway.filter.post;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class serverPortResponseFilter implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    String port = (String) exchange.getAttributes().get("custom-port");
                    System.out.println("포스트필터 port = " + port);

                    if (port != null) {
                        exchange.getResponse().getHeaders().add("Server-Port", port);
                    } else {
                        log.info("Custom Port 를 찾을 수 없습니다.");
                    }
                })
        );
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
