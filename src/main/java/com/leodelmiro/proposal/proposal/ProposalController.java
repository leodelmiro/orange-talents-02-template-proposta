package com.leodelmiro.proposal.proposal;

import com.leodelmiro.proposal.financialanalysis.FinancialAnalysisClient;
import com.leodelmiro.proposal.financialanalysis.FinancialAnalysisResponse;
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

    private Proposal proposal;

    @PostMapping
    @Transactional
    public ResponseEntity<Void> create(@RequestBody @Valid NewProposalRequest request) {
        proposal = request.toModel();
        entityManager.persist(proposal);

        try {
            FinancialAnalysisResponse financialAnalysis = financialAnalysisClient.financialAnalysis(proposal.toFinancialAnalysis());
            proposal.setProposalStatus(financialAnalysis.statusToProposalStatus());
        } catch (Exception e) {
            proposal.setProposalStatus(ProposalStatus.NOT_ELIGIBLE);
            return ResponseEntity.unprocessableEntity().build();
        }

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(proposal.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
