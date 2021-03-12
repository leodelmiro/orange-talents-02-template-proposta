package com.leodelmiro.proposal.cards;

import com.leodelmiro.proposal.biometry.Biometry;
import com.leodelmiro.proposal.block.CardBlock;
import com.leodelmiro.proposal.proposal.Proposal;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "proposal_id")
    private Proposal proposal;

    @OneToMany(mappedBy = "card", cascade = CascadeType.PERSIST)
    private Set<Biometry> biometrics = new HashSet<>();

    @OneToOne(mappedBy = "card", cascade = CascadeType.MERGE)
    private CardBlock cardBlock;

    /**
     *
     * @Deprecated for framework use only
     */
    @Deprecated
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

    public Set<Biometry> getBiometrics() {
        return biometrics;
    }

    public boolean isAlreadyBlocked() {
        return cardBlock != null;
    }
}
