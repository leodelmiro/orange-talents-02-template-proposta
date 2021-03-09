package com.leodelmiro.proposal.proposal;

import com.leodelmiro.proposal.cards.Card;
import com.leodelmiro.proposal.cards.CardResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProposalResponse {

    private Long id;
    private String document;
    private String email;
    private String name;
    private RequesterAddressResponse address;
    private BigDecimal salary;
    private ProposalStatus proposalStatus;
    private CardResponse card;
    private LocalDateTime createdAt;

    public ProposalResponse(Proposal entity) {
        this.id = entity.getId();
        this.document = entity.getDocument();
        this.email = entity.getEmail();
        this.name = entity.getName();
        this.address = new RequesterAddressResponse(entity.getAddress());
        this.salary = entity.getSalary();
        this.proposalStatus = entity.getStatus();
        if (entity.getCard() != null) this.card = new CardResponse(entity.getCard());
        this.createdAt = entity.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getDocument() {
        return document;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public RequesterAddressResponse getAddress() {
        return address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public ProposalStatus getProposalStatus() {
        return proposalStatus;
    }

    public CardResponse getCard() {
        return card;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
