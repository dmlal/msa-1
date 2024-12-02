package com.sparta.msa_exam.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth")
public interface AuthClient {

    @PostMapping("/auth/validate/admin")
    Boolean validateAdminToken(@RequestHeader("Authorization") String token);
}
