package com.leodelmiro.proposal.travelnotice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leodelmiro.proposal.cards.Card;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TravelNoticeRequest {

    @NotBlank
    private String destiny;

    @NotNull
    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public TravelNoticeRequest(@NotBlank String destiny, @NotNull @Future LocalDate endDate) {
        this.destiny = destiny;
        this.endDate = endDate;
    }

    public String getDestiny() {
        return destiny;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public TravelNotice toModel(Card card, String userAgent, String userIp) {
        return new TravelNotice(card, destiny, endDate, userAgent, userIp);
    }
}
