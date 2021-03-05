package com.leodelmiro.proposal.newproposal;

import com.leodelmiro.proposal.common.validation.ApiErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/proposals")
public class ProposalController {

    @PersistenceContext
    EntityManager entityManager;

    @PostMapping
    @Transactional
    public ResponseEntity<Void> createRequester(@RequestBody @Valid NewProposalRequesterRequest request) {
        ProposalRequester requester = request.toModel();

        if (requester.alreadyHasProposal(entityManager)) {
            throw new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "Esse documento já possui uma solicitação de proposta!");
        }

        entityManager.persist(requester);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(requester.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
