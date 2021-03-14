package com.leodelmiro.proposal.common.security;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "auth", url = "${auth.api}", fallback = AuthenticationClientFallback.class)
public interface AuthenticationClient{

    @PostMapping
    LoginResponse auth(MultiValueMap<String, String> request);
}
