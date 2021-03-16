package com.leodelmiro.proposal.wallet;

import com.leodelmiro.proposal.cards.Card;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WalletServices walletService;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Deprecated
    /**
     * @Deprecated for framework only
     */
    public Wallet() {
    }

    public Wallet(@NotBlank String email, @NotNull WalletServices walletService, @NotNull @Valid Card card) {
        Assert.hasLength(email, "Email é obrigatório");
        Assert.notNull(walletService, "Carteira digital é obrigatória");
        Assert.notNull(card, "Cartão digital é obrigatório");

        this.email = email;
        this.walletService = walletService;
        this.card = card;
    }

    public Long getId() {
        return id;
    }

    public WalletServices getWalletService() {
        return walletService;
    }
}
