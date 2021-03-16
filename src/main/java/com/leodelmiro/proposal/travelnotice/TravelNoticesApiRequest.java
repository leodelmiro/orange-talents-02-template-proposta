package com.leodelmiro.proposal.travelnotice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class TravelNoticesApiRequest {

    @JsonProperty("destino")
    private String destiny;

    @JsonProperty("validoAte")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public TravelNoticesApiRequest(TravelNotice entity) {
        this.destiny = entity.getDestiny();
        this.endDate = entity.getEndDate();
    }

    public String getDestiny() {
        return destiny;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
