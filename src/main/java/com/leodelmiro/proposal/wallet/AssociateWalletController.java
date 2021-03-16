package com.leodelmiro.proposal.wallet;

import com.leodelmiro.proposal.cards.Card;
import com.leodelmiro.proposal.cards.CardsClient;
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

        Wallet wallet = request.toModel(card);
        try {
            WalletResponse response = cardsClient.walletAssociation(card.getCardNumber(), request);
            if (response.getResponse().equals("ASSOCIADA")) entityManager.merge(wallet);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Erro na api de carteira, tente novamente!");
        }

        URI uri = ServletUriComponentsBuilder.fromCurrentServletMapping().path("api/wallets/{id}").buildAndExpand(wallet.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
