package com.leodelmiro.proposal.common.validation;

import java.util.Collection;

public class ErrorMessage {

    private Collection<String> messages;


    public ErrorMessage(Collection<String> messages) {
        this.messages = messages;
    }

    public Collection<String> getMessages() {
        return messages;
    }
}
