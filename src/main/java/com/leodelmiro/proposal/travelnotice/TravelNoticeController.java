package com.leodelmiro.proposal.travelnotice;

import com.leodelmiro.proposal.cards.Card;
import com.leodelmiro.proposal.cards.CardsClient;
import com.leodelmiro.proposal.common.utils.ClientHostResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/cards")
public class TravelNoticeController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CardsClient cardsClient;

    @PostMapping("/{cardId}/travelnotices")
    @Transactional
    public ResponseEntity<?> travelNotice(@PathVariable Long cardId,
                                          @RequestHeader(HttpHeaders.USER_AGENT) String userAgent,
                                          @RequestBody @Valid TravelNoticeRequest request,
                                          HttpServletRequest requestInfo) {

        Card card = entityManager.find(Card.class, cardId);
        if (card == null) {
            return ResponseEntity.notFound().build();
        }

        String userIp = new ClientHostResolver(requestInfo).resolve();


        if (isBlankUser(userAgent, userIp)) {
            return ResponseEntity.badRequest().build();
        }

        try {
            TravelNotice travelNotice = request.toModel(card, userAgent, userIp);
            TravelNoticesApiResponse apiResponse = cardsClient.notices(cardId.toString(), travelNotice.toExternalApiRequest());
            if (apiResponse.response.equals("CRIADO")) entityManager.persist(travelNotice);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok().build();

    }

    private boolean isBlankUser(String userAgent, String userIp) {
        return userIp.isBlank() || userAgent.isBlank();
    }
}
