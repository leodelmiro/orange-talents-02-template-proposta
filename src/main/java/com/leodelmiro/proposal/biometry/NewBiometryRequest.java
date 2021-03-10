package com.leodelmiro.proposal.biometry;

import com.leodelmiro.proposal.cards.Card;

import javax.validation.constraints.NotBlank;

public class NewBiometryRequest {

    @NotBlank
    private String fingerprint;

    /**
     * @Deprecated for framework use only
     */
    @Deprecated
    public NewBiometryRequest() {

    }

    public NewBiometryRequest(@NotBlank String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public Biometry toModel(Card card) {
        return new Biometry(fingerprint, card);
    }
}
