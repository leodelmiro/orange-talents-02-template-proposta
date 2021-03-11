package com.leodelmiro.proposal.cards;

import com.leodelmiro.proposal.biometry.BiometryResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class CardResponse {

    private String cardNumber;
    private String holder;
    private BigDecimal limit;
    private Set<BiometryResponse> biometrics;
    private LocalDateTime createdAt;

    public CardResponse(Card entity) {
        this.cardNumber = entity.getCardNumber();
        this.holder = entity.getHolder();
        this.limit = entity.getCardLimit();
        this.createdAt = entity.getCreatedAt();
        this.biometrics = entity.getBiometrics().stream().map(BiometryResponse::new).collect(Collectors.toSet());
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getHolder() {
        return holder;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Set<BiometryResponse> getBiometrics() {
        return biometrics;
    }
}
