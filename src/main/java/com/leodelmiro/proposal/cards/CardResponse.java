package com.leodelmiro.proposal.cards;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.leodelmiro.proposal.proposal.Proposal;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CardResponse {

    private String id;

    @JsonProperty("emitidoEm")
    private LocalDateTime createdAt;

    @JsonProperty("titular")
    private String holder;

    @JsonProperty("limite")
    private BigDecimal limit;

    @JsonProperty("idProposta")
    private String proposalId;

    public CardResponse(String id, LocalDateTime createdAt, String holder, BigDecimal limit, String proposalId) {
        this.id = id;
        this.createdAt = createdAt;
        this.holder = holder;
        this.limit = limit;
        this.proposalId = proposalId;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getHolder() {
        return holder;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public String getProposalId() {
        return proposalId;
    }

    @Override
    public String toString() {
        return "CardResponse{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", holder='" + holder + '\'' +
                ", limit=" + limit +
                ", proposalId='" + proposalId + '\'' +
                '}';
    }

    public Card toModel(Proposal proposal) {
        return new Card(id, holder, limit, createdAt, proposal);
    }

}
