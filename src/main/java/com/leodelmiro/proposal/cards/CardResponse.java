package com.leodelmiro.proposal.cards;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.leodelmiro.proposal.proposal.Proposal;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CardResponse {

    @JsonProperty("id")
    private String cardNumber;

    @JsonProperty("titular")
    private String holder;

    @JsonProperty("limite")
    private BigDecimal limit;

    @JsonProperty("emitidoEm")
    private LocalDateTime createdAt;

    public CardResponse(Card entity) {
        this.cardNumber = entity.getCardNumber();
        this.holder = entity.getHolder();
        this.limit = entity.getCardLimit();
        this.createdAt = entity.getCreatedAt();
    }

    public CardResponse(String cardNumber, LocalDateTime createdAt, String holder, BigDecimal limit, String proposalId) {
        this.cardNumber = cardNumber;
        this.createdAt = createdAt;
        this.holder = holder;
        this.limit = limit;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getHolder() {
        return holder;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public Card toModel(Proposal proposal) {
        return new Card(cardNumber, holder, limit, createdAt, proposal);
    }

}
