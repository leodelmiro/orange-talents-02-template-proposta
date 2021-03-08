package com.leodelmiro.proposal.financialanalysis;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FinancialAnalysisRequest {

    @JsonProperty("documento")
    private String document;

    @JsonProperty("nome")
    private String name;

    @JsonProperty("idProposta")
    private String proposalId;

    @Deprecated
    public FinancialAnalysisRequest() {
    }

    public FinancialAnalysisRequest(String document, String name, Long id) {
        this.document = document;
        this.name = name;
        this.proposalId = id.toString();
    }

    public String getDocument() {
        return document;
    }

    public String getName() {
        return name;
    }

    public String getProposalId() {
        return proposalId;
    }
}
