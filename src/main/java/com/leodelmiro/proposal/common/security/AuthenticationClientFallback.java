package com.leodelmiro.proposal.common.security;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AuthenticationClientFallback implements AuthenticationClient {
    @Override
    public LoginResponse auth(MultiValueMap<String, String> request) {
        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                "Erro ao realizar login, tente novamente!");

    }
}
