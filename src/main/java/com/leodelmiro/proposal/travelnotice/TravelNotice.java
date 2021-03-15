package com.leodelmiro.proposal.travelnotice;

import com.leodelmiro.proposal.cards.Card;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_travelnotices")
public class TravelNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @NotBlank
    private String destiny;

    @NotNull
    @Future
    private LocalDate endDate;

    @NotBlank
    private String userAgent;

    @NotBlank
    private String userIp;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public TravelNotice(@NotNull @Valid Card card, @NotBlank String destiny, @NotNull @Future LocalDate endDate,
                        String userAgent, String userIp) {
        this.card = card;
        this.destiny = destiny;
        this.endDate = endDate;
        this.userAgent = userAgent;
        this.userIp = userIp;
    }

    public String getDestiny() {
        return destiny;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getUserIp() {
        return userIp;
    }
}
