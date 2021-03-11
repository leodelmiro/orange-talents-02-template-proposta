package com.leodelmiro.proposal.biometry;

import com.leodelmiro.proposal.cards.Card;
import com.leodelmiro.proposal.common.validation.IsBase64;

import javax.validation.constraints.NotBlank;

public class NewBiometryRequest {

    @NotBlank
    @IsBase64
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
