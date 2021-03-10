package com.leodelmiro.proposal.biometry;

import com.leodelmiro.proposal.cards.Card;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_biometrics")
public class Biometry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String fingerprint;

    @NotNull
    @OneToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @Column(updatable = false)
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
}
