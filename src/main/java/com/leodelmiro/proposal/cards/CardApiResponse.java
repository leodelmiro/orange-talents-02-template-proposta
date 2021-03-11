package com.leodelmiro.proposal.cards;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.leodelmiro.proposal.proposal.Proposal;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CardApiResponse {

    @JsonProperty("id")
    private String cardNumber;

    @JsonProperty("titular")
    private String holder;

    @JsonProperty("limite")
    private BigDecimal limit;

    @JsonProperty("emitidoEm")
    private LocalDateTime createdAt;

    public CardApiResponse(String cardNumber, LocalDateTime createdAt, String holder, BigDecimal limit, String proposalId) {
        this.cardNumber = cardNumber;
        this.createdAt = createdAt;
        this.holder = holder;
        this.limit = limit;
    }

    public Card toModel(Proposal proposal) {
        return new Card(cardNumber, holder, limit, createdAt, proposal);
    }

}
