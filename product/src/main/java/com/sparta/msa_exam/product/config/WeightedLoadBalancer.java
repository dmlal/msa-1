package com.sparta.msa_exam.product.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class WeightedLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        return null;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose() {
        return ReactorServiceInstanceLoadBalancer.super.choose();
    }
}
