package com.leodelmiro.proposal.wallet;

import com.leodelmiro.proposal.cards.Card;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class WalletRequest {

    @NotBlank
    private String email;

    @NotNull
    private WalletServices wallet;

    /**
     * @Deprecated for framework use only
     */
    @Deprecated
    public WalletRequest() {

    }

    public WalletRequest(@NotBlank String email, @NotNull WalletServices wallet) {
        this.email = email;
        this.wallet = wallet;
    }

    public String getEmail() {
        return email;
    }

    public WalletServices getWallet() {
        return wallet;
    }

    public Wallet toModel(Card card) {
        return new Wallet(email, wallet, card);
    }
}
