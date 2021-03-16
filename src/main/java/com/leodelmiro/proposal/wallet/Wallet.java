package com.leodelmiro.proposal.wallet;

import com.leodelmiro.proposal.cards.Card;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "tb_wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String associationId;

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

    public Wallet(@NotBlank String email, @NotNull WalletServices walletService, @NotNull @Valid Card card, @NotBlank String associationId) {
        Assert.hasLength(email, "Email é obrigatório");
        Assert.notNull(walletService, "Carteira digital é obrigatória");
        Assert.notNull(card, "Cartão digital é obrigatório");
        Assert.hasLength(associationId, "Id de associação é obrigatório!");

        this.email = email;
        this.walletService = walletService;
        this.card = card;
        this.associationId = associationId;
    }

    public Long getId() {
        return id;
    }

    public WalletServices getWalletService() {
        return walletService;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return walletService == wallet.walletService && Objects.equals(card, wallet.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(walletService, card);
    }
}
