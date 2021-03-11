package com.leodelmiro.proposal.biometry;

import com.leodelmiro.proposal.cards.Card;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/biometrics")
public class BiometryController {

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/{cardId}")
    @Transactional
    public ResponseEntity<?> create(@PathVariable Long cardId, @RequestBody @Valid NewBiometryRequest request) {
        Card possibleCard = entityManager.find(Card.class, cardId);
        if (possibleCard == null) {
            return ResponseEntity.notFound().build();
        }

        Biometry biometry = request.toModel(possibleCard);
        entityManager.persist(biometry);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(biometry.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
