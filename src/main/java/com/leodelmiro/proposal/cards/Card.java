package com.leodelmiro.proposal.cards;

import com.leodelmiro.proposal.proposal.Proposal;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNumber;

    private String holder;

    private BigDecimal cardLimit;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "proposal_id")
    private Proposal proposal;

    /**
     *
     * @Deprecated only form framework
     */
    public Card() {
    }

    public Card(String cardNumber, String holder, BigDecimal cardLimit, LocalDateTime createdAt, Proposal proposal) {
        this.cardNumber = cardNumber;
        this.holder = holder;
        this.cardLimit = cardLimit;
        this.createdAt = createdAt;
        this.proposal = proposal;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getHolder() {
        return holder;
    }

    public BigDecimal getCardLimit() {
        return cardLimit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Proposal getProposal() {
        return proposal;
    }
}
