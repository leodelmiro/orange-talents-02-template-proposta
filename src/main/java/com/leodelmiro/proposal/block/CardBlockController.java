package com.leodelmiro.proposal.block;

import com.leodelmiro.proposal.cards.Card;
import com.leodelmiro.proposal.common.utils.ClientHostResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/cards")
public class CardBlockController {

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/{cardId}/block")
    @Transactional
    public ResponseEntity<?> blockCard(@PathVariable Long cardId,
                                       @RequestHeader(HttpHeaders.USER_AGENT) String userAgent,
                                       HttpServletRequest request) {

        Card card = entityManager.find(Card.class, cardId);
        if(card == null) {
            return ResponseEntity.notFound().build();
        }

        String userIp = new ClientHostResolver(request).resolve();

        if (isInvalidRequest(userAgent, userIp)) {
            return ResponseEntity.badRequest().build();
        }

        if(card.isAlreadyBlocked()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        CardBlock cardBlock = new CardBlock(card, userIp, userAgent);
        entityManager.persist(cardBlock);

        return ResponseEntity.ok().build();
    }

    private boolean isInvalidRequest(String userAgent, String userIp) {
        return userIp == null || userIp.isBlank() || userAgent == null || userAgent.isBlank();
    }
}