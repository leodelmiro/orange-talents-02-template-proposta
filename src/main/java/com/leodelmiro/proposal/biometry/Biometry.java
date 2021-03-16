package com.leodelmiro.proposal.biometry;

import com.leodelmiro.proposal.cards.Card;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tb_biometrics")
public class Biometry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String fingerprint;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false, name = "card_id")
    private Card card;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * @Deprecated for framework use only
     */
    @Deprecated
    public Biometry() {

    }
    public Biometry(@NotBlank String fingerprint, @NotNull Card card) {
        Assert.hasLength(fingerprint, "Digital é obrigatória!");
        Assert.notNull(card, "Cartão é obrigatório!");

        this.fingerprint = fingerprint;
        this.card = card;
    }

    public Long getId() {
        return id;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Biometry biometry = (Biometry) o;
        return Objects.equals(fingerprint, biometry.fingerprint) && Objects.equals(card, biometry.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fingerprint, card);
    }
}
