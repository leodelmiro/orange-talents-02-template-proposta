package com.leodelmiro.proposal.cards;

import com.leodelmiro.proposal.block.CardBlockRequest;
import com.leodelmiro.proposal.block.CardBlockResponse;
import com.leodelmiro.proposal.travelnotice.TravelNoticesApiRequest;
import com.leodelmiro.proposal.travelnotice.TravelNoticesApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class CardsClientFallback implements CardsClient {
    @Override
    public CardApiResponse getCard(CardRequest request) {
        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                "Erro ao associar cartão, tente novamente!");
    }

    @Override
    public CardBlockResponse blockCard(String id, CardBlockRequest request) {
        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                "Erro ao bloquear cartão, tente novamente!");
    }

    @Override
    public TravelNoticesApiResponse notices(String id, TravelNoticesApiRequest request) {
        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                "Erro ao notificar viagem, tente novamente!");
    }
}
