package com.leodelmiro.proposal.wallet;

import com.leodelmiro.proposal.cards.Card;
import com.leodelmiro.proposal.cards.CardsClient;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class AssociateWalletController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CardsClient cardsClient;

    @PostMapping("/cards/{cardId}/wallets")
    @Transactional
    public ResponseEntity<?> associate(@PathVariable Long cardId, @RequestBody @Valid WalletRequest request) {
        Card card = entityManager.find(Card.class, cardId);
        if (card == null) {
            return ResponseEntity.notFound().build();
        }

        if (card.isAlreadyAssociateTo(request.getWallet())) {
            return ResponseEntity.unprocessableEntity().build();
        }

        try {
            WalletResponse response = cardsClient.walletAssociation(card.getCardNumber(), request);

            if (response.getResponse().equals("ASSOCIADA")) {
                Wallet wallet = request.toModel(card, response.getAssociationId());
                entityManager.persist(wallet);
                URI uri = ServletUriComponentsBuilder.fromCurrentServletMapping().path("api/wallets/{id}").buildAndExpand(wallet.getId()).toUri();
                return ResponseEntity.created(uri).build();
            }

            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Proposta não foi associada corretamente, tente novamente!");
        } catch (FeignException | HystrixRuntimeException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Erro na api de carteira, tente novamente!");
        }
    }
}
