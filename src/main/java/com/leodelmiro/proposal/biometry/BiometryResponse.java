package com.leodelmiro.proposal.biometry;

import java.time.LocalDateTime;

public class BiometryResponse {

    private String fingerprint;
    private LocalDateTime createdAt;

    /**
     * @Deprecated for framework use only
     */
    @Deprecated
    public BiometryResponse() {

    }

    public BiometryResponse(Biometry entity) {
        this.fingerprint = entity.getFingerprint();
        this.createdAt = entity.getCreatedAt();
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
