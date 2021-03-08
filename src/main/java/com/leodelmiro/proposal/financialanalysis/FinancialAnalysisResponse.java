package com.leodelmiro.proposal.financialanalysis;

import com.leodelmiro.proposal.newproposal.ProposalStatus;

import static com.leodelmiro.proposal.newproposal.ProposalStatus.ELIGIBLE;
import static com.leodelmiro.proposal.newproposal.ProposalStatus.NOT_ELIGIBLE;

public class FinancialAnalysisResponse {

    private String documento;
    private String nome;
    private SolicitationStatus resultadoSolicitacao;
    private String idProposta;

    public FinancialAnalysisResponse(String documento, String nome, SolicitationStatus resultadoSolicitacao, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public SolicitationStatus getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public ProposalStatus statusToProposalStatus() {
        if (resultadoSolicitacao == SolicitationStatus.COM_RESTRICAO) {
            return NOT_ELIGIBLE;
        }

        return ELIGIBLE;
    }

}
