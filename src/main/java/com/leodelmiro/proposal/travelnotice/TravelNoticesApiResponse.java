package com.leodelmiro.proposal.travelnotice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TravelNoticesApiResponse {

    @JsonProperty("resultado")
    String response;

    @Deprecated
    /**
     * @Deprecated for framework use only
     */
    public TravelNoticesApiResponse() {
    }

    public TravelNoticesApiResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
