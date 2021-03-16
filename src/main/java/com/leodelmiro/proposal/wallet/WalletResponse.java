package com.leodelmiro.proposal.wallet;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WalletResponse {

    @JsonProperty("resultado")
    private String response;

    /**
     * @Deprecated for framework use only
     */
    @Deprecated
    public WalletResponse() {

    }

    public WalletResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
