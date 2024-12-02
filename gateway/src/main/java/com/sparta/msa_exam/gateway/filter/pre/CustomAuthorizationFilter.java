//package com.sparta.msa_exam.gateway.filter.pre;
//
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//public class CustomAuthorizationFilter implements GlobalFilter, Ordered {
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        return null;
//    }
//
//    @Override
//    public int getOrder() {
//        return Ordered.HIGHEST_PRECEDENCE +1;
//    }
//}
