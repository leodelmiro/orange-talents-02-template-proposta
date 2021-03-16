package com.leodelmiro.proposal.block;

import javax.validation.constraints.NotBlank;

public class CardBlockRequest {

    @NotBlank
    private String responsibleSystem;

    /**
     * @Deprecated for framework use only
     */
    @Deprecated
    public CardBlockRequest() {

    }

    public CardBlockRequest(@NotBlank String responsibleSystem) {
        this.responsibleSystem = responsibleSystem;
    }

    public String getResponsibleSystem() {
        return responsibleSystem;
    }
}
