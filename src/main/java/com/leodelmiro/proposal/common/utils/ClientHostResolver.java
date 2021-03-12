package com.leodelmiro.proposal.common.utils;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.google.common.base.MoreObjects.firstNonNull;

@Component
public class ClientHostResolver {

    private final HttpServletRequest request;

    public ClientHostResolver(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Resolves client IP address when application is behind a NGINX or other reverse proxy server
     */
    public String resolve() {

        String xRealIp = request.getHeader("X-Real-IP");
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        String remoteAddr = request.getRemoteAddr();

        if (xRealIp != null)
            return xRealIp;

        return firstNonNull(xForwardedFor, remoteAddr);
    }
}