package com.leodelmiro.proposal.cards;

import com.leodelmiro.proposal.biometry.Biometry;
import com.leodelmiro.proposal.block.CardBlock;
import com.leodelmiro.proposal.block.CardBlockRequest;
import com.leodelmiro.proposal.block.CardBlockResponse;
import com.leodelmiro.proposal.proposal.Proposal;
import com.leodelmiro.proposal.travelnotice.TravelNotice;
import com.leodelmiro.proposal.wallet.Wallet;
import com.leodelmiro.proposal.wallet.WalletServices;
import feign.FeignException;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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

    @NotBlank
    @Column(nullable = false)
    private String cardNumber;

    @NotBlank
    @Column(nullable = false)
    private String holder;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal cardLimit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardStatus status = CardStatus.ACTIVE;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "proposal_id", nullable = false)
    private Proposal proposal;

    @OneToMany(mappedBy = "card", cascade = CascadeType.MERGE)
    private Set<Biometry> biometrics = new HashSet<>();

    @OneToOne(mappedBy = "card", cascade = CascadeType.MERGE)
    private CardBlock cardBlock;

    @OneToMany(mappedBy = "card", cascade = CascadeType.MERGE)
    private Set<TravelNotice> travelNotices = new HashSet<>();

    @OneToMany(mappedBy = "card", cascade = CascadeType.MERGE)
    private Set<Wallet> wallets = new HashSet<>();

    /**
     * @Deprecated for framework use only
     */
    @Deprecated
    public Card() {
    }

    public Card(String cardNumber, String holder, BigDecimal cardLimit, LocalDateTime createdAt, Proposal proposal) {
        Assert.hasLength(cardNumber, "Número de cartão é obrigatório");
        Assert.hasLength(holder, "Nome do dono do cartão é obrigatório");
        Assert.notNull(cardLimit, "Limite do cartão é obrigatório");
        Assert.state(cardLimit.compareTo(BigDecimal.ZERO) > 0, "Limite deve ser maior que 0");
        Assert.notNull(createdAt, "Momento de criação é obrigatório!");
        Assert.notNull(proposal, "Proposta é obrigatória!");

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

    public CardStatus getStatus() {
        return status;
    }

    public CardBlock getCardBlock() {
        return cardBlock;
    }

    public Set<TravelNotice> getTravelNotices() {
        return travelNotices;
    }

    public Set<Wallet> getWallets() {
        return wallets;
    }

    public boolean isAlreadyBlocked() {
        return status == CardStatus.BLOCKED;
    }

    public void updateStatus(CardsClient client, CardBlockRequest request) throws FeignException {
        CardBlockResponse response = client.blockCard(cardNumber, request);

        this.status = response.toCardStatus();
    }

    public boolean isAlreadyAssociateTo(WalletServices walletService) {
        return wallets.stream().anyMatch(wallet -> wallet.getWalletService() == walletService);
    }
}
