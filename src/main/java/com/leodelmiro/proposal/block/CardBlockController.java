package com.leodelmiro.proposal.block;

import com.leodelmiro.proposal.cards.Card;
import com.leodelmiro.proposal.cards.CardsClient;
import com.leodelmiro.proposal.common.utils.ClientHostResolver;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/cards")
public class CardBlockController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CardsClient cardsClient;

    @PostMapping("/{cardId}/block")
    @Transactional
    public ResponseEntity<?> blockCard(@PathVariable Long cardId,
                                       @RequestHeader(HttpHeaders.USER_AGENT) String userAgent,
                                       @RequestBody @Valid CardBlockRequest request,
                                       HttpServletRequest requestInfo) {

        Card card = entityManager.find(Card.class, cardId);
        if (card == null) {
            return ResponseEntity.notFound().build();
        }

        String userIp = new ClientHostResolver(requestInfo).resolve();

        if (isBlankUser(userAgent, userIp)) {
            return ResponseEntity.badRequest().build();
        }

        if (card.isAlreadyBlocked()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        try {
            card.updateStatus(cardsClient, request);
            CardBlock cardBlock = new CardBlock(card, userIp, userAgent);
            entityManager.merge(cardBlock);
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Erro na api de bloquei, tente novamente!");
        }

        return ResponseEntity.ok().build();
    }

    private boolean isBlankUser(String userAgent, String userIp) {
        return userIp.isBlank() || userAgent.isBlank();
    }
}
