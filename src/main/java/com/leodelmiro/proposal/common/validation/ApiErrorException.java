package com.leodelmiro.proposal.common.validation;

import org.springframework.http.HttpStatus;

public class ApiErrorException extends RuntimeException {

    private HttpStatus httpStatus;

    private String reason;

    public ApiErrorException(HttpStatus httpStatus, String reason) {
        super(reason);
        this.httpStatus = httpStatus;
        this.reason = reason;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getReason() {
        return reason;
    }
}
