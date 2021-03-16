package com.leodelmiro.proposal.block;

import com.leodelmiro.proposal.cards.Card;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_cardsblock")
public class CardBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @NotBlank
    @Column(nullable = false)
    private String userIp;

    @NotBlank
    @Column(nullable = false)
    private String userAgent;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * @Deprecated for framework use only
     */
    @Deprecated
    public CardBlock() {

    }

    public CardBlock(@NotNull @Valid Card card, @NotBlank String userIp, @NotBlank String userAgent) {
        Assert.notNull(card, "Cartão é obrigatório!");
        Assert.hasLength(userIp, "Ip do usuário é obrigatório!");
        Assert.hasLength(userAgent, "User Agent do usuário é obrigatório!");

        this.card = card;
        this.userIp = userIp;
        this.userAgent = userAgent;
    }


}
