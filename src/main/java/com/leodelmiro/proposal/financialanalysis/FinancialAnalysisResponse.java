package com.leodelmiro.proposal.financialanalysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.leodelmiro.proposal.proposal.ProposalStatus;

import static com.leodelmiro.proposal.proposal.ProposalStatus.ELIGIBLE;
import static com.leodelmiro.proposal.proposal.ProposalStatus.NOT_ELIGIBLE;

public class FinancialAnalysisResponse {

    @JsonProperty("resultadoSolicitacao")
    private SolicitationStatus solicitationStatus;

    /**
     * @Deprecated for framework use only
     */
    @Deprecated
    public FinancialAnalysisResponse() {
    }

    public FinancialAnalysisResponse(SolicitationStatus solicitationStatus) {
        this.solicitationStatus = solicitationStatus;
    }

    public SolicitationStatus getSolicitationStatus() {
        return solicitationStatus;
    }

    public ProposalStatus statusToProposalStatus() {
        if (solicitationStatus == SolicitationStatus.COM_RESTRICAO) {
            return NOT_ELIGIBLE;
        }

        return ELIGIBLE;
    }

}
