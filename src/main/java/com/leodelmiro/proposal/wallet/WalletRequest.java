package com.leodelmiro.proposal.wallet;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.leodelmiro.proposal.cards.Card;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class WalletRequest {

    @Email
    @NotBlank
    @JsonProperty("email")
    private String email;

    @NotNull
    @JsonProperty("carteira")
    private WalletServices wallet;

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

    public Wallet toModel(Card card, String associationId) {
        return new Wallet(email, wallet, card, associationId);
    }
}
