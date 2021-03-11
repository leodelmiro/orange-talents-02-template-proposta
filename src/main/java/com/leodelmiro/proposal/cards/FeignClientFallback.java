package com.leodelmiro.proposal.cards;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FeignClientFallback implements CardsClient {
    @Override
    public CardResponse getCard(CardRequest request) {
        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                "Erro ao associar cartão, tente novamente!");
    }
}
