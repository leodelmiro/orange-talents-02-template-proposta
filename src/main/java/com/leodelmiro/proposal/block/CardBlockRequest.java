package com.leodelmiro.proposal.block;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class CardBlockRequest {

    @NotBlank
    @JsonProperty("sistemaResponsavel")
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
}
