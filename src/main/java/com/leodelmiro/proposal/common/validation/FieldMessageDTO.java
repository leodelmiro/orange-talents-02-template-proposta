package com.leodelmiro.proposal.common.validation;

public class FieldMessageDTO {

    private String field;
    private String message;


    public FieldMessageDTO(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
