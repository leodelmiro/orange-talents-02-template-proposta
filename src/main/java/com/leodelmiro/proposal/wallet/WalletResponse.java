package com.leodelmiro.proposal.wallet;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WalletResponse {

    @JsonProperty("resultado")
    private String response;

    @JsonProperty("id")
    private String associationId;

    /**
     * @Deprecated for framework use only
     */
    @Deprecated
    public WalletResponse() {

    }

    public WalletResponse(String response, String associationId) {
        this.response = response;
        this.associationId = associationId;
    }

    public String getResponse() {
        return response;
    }

    public String getAssociationId() {
        return associationId;
    }
}
