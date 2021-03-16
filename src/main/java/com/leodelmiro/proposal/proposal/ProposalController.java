package com.leodelmiro.proposal.proposal;

import com.leodelmiro.proposal.financialanalysis.FinancialAnalysisClient;
import com.leodelmiro.proposal.financialanalysis.FinancialAnalysisResponse;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.exception.HystrixTimeoutException;
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
import java.util.Optional;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/proposals")
public class ProposalController {

    @Autowired
    private ProposalRepository repository;

    @Autowired
    private FinancialAnalysisClient financialAnalysisClient;

    @PostMapping
    @Transactional
    public ResponseEntity<Void> create(@RequestBody @Valid NewProposalRequest request) {
        if (repository.existsByDocument(request.getDocument())) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Proposal proposal = repository.save(request.toModel());

        try {
            proposal.updateStatus(financialAnalysisClient);
        } catch (HystrixRuntimeException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Erro na Api de cartões");
        }

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(proposal.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Optional<Proposal> possibleProposal = repository.findById(id);

        if (possibleProposal.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new ProposalResponse(possibleProposal.get()));
    }
}
