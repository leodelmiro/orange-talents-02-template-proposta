package com.leodelmiro.proposal.block;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.leodelmiro.proposal.cards.CardStatus;

public class CardBlockResponse {

    @JsonProperty("resultado")
    private String blockStatus;

    /**
     * @Deprecated for framework use only
     */
    @Deprecated
    public CardBlockResponse() {

    }

    public CardBlockResponse(String blockStatus) {
        this.blockStatus = blockStatus;
    }

    public CardStatus toCardStatus() {
        if (blockStatus.equals("BLOQUEADO")) {
            return CardStatus.BLOCKED;
        }

        return CardStatus.ACTIVE;
    }
}
