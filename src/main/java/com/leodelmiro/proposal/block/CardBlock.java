package com.leodelmiro.proposal.block;

import com.leodelmiro.proposal.cards.Card;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_cardsblock")
public class CardBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @NotBlank
    private String userIp;

    @NotBlank
    private String userAgent;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * @Deprecated for framework use only
     */
    @Deprecated
    public CardBlock() {

    }

    public CardBlock(Card card, String userIp, String userAgent) {
        this.card = card;
        this.userIp = userIp;
        this.userAgent = userAgent;
    }


}
