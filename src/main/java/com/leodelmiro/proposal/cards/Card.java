package com.leodelmiro.proposal.cards;

import com.leodelmiro.proposal.biometry.Biometry;
import com.leodelmiro.proposal.block.CardBlock;
import com.leodelmiro.proposal.block.CardBlockRequest;
import com.leodelmiro.proposal.block.CardBlockResponse;
import com.leodelmiro.proposal.proposal.Proposal;
import com.leodelmiro.proposal.travelnotice.TravelNotice;

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

    @Enumerated(EnumType.STRING)
    private CardStatus status = CardStatus.ACTIVE;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "proposal_id")
    private Proposal proposal;

    @OneToMany(mappedBy = "card")
    private Set<Biometry> biometrics = new HashSet<>();

    @OneToOne(mappedBy = "card")
    private CardBlock cardBlock;

    @OneToMany(mappedBy = "card")
    private Set<TravelNotice> travelNotices = new HashSet<>();

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
        return status == CardStatus.BLOCKED;
    }

    public void updateStatus(CardsClient client, CardBlockRequest request) throws Exception{
        CardBlockResponse response = client.blockCard(cardNumber, request);

        this.status = response.toCardStatus();
    }


}
