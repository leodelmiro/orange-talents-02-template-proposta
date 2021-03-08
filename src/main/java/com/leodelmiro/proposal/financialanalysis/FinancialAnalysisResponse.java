package com.leodelmiro.proposal.financialanalysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.leodelmiro.proposal.proposal.ProposalStatus;

import static com.leodelmiro.proposal.proposal.ProposalStatus.ELIGIBLE;
import static com.leodelmiro.proposal.proposal.ProposalStatus.NOT_ELIGIBLE;

public class FinancialAnalysisResponse {

    @JsonProperty("documento")
    private String document;

    @JsonProperty("nome")
    private String name;

    @JsonProperty("resultadoSolicitacao")
    private SolicitationStatus solicitationStatus;

    @JsonProperty("idProposta")
    private String proposalId;

    public FinancialAnalysisResponse(String document, String name, SolicitationStatus solicitationStatus, String proposalId) {
        this.document = document;
        this.name = name;
        this.solicitationStatus = solicitationStatus;
        this.proposalId = proposalId;
    }

    public String getDocument() {
        return document;
    }

    public String getName() {
        return name;
    }

    public SolicitationStatus getSolicitationStatus() {
        return solicitationStatus;
    }

    public String getProposalId() {
        return proposalId;
    }

    public ProposalStatus statusToProposalStatus() {
        if (solicitationStatus == SolicitationStatus.COM_RESTRICAO) {
            return NOT_ELIGIBLE;
        }

        return ELIGIBLE;
    }

}
