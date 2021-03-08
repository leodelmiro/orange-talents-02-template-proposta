package com.leodelmiro.proposal.newproposal;

import com.leodelmiro.proposal.financialanalysis.FinancialAnalysisClient;
import com.leodelmiro.proposal.financialanalysis.FinancialAnalysisResponse;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
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
    private EntityManager entityManager;

    @Autowired
    private FinancialAnalysisClient financialAnalysisClient;

    @PostMapping
    @Transactional
    public ResponseEntity<Void> create(@RequestBody @Valid NewProposalRequesterRequest request) {
        ProposalRequester requester = request.toModel();

        entityManager.persist(requester);

        try {
            FinancialAnalysisResponse financialAnalysis = financialAnalysisClient.financialAnalysis(requester.toFinancialAnalysis());
            requester.setProposalStatus(financialAnalysis.statusToProposalStatus());
        } catch (FeignException e) {
            requester.setProposalStatus(ProposalStatus.NOT_ELIGIBLE);
        }

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(requester.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
