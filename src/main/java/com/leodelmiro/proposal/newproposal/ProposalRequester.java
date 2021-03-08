package com.leodelmiro.proposal.newproposal;

import com.leodelmiro.proposal.common.validation.CPForCNPJ;
import com.leodelmiro.proposal.financialanalysis.FinancialAnalysisRequest;
import org.apache.tomcat.jni.Address;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_requesters")
public class ProposalRequester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CPForCNPJ
    @NotBlank
    private String document;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;

    @NotNull
    @Embedded
    private RequesterAddress address;

    @Positive
    @NotNull
    private BigDecimal salary;

    @Enumerated
    private ProposalStatus status;

    /**
     * @Deprecated only for framework
     */
    public ProposalRequester() {
    }

    public ProposalRequester(@NotBlank String document, @NotBlank @Email String email, @NotBlank String name, @NotBlank RequesterAddress address, @PositiveOrZero BigDecimal salary) {
        Assert.hasLength(document, "Documento é obrigatório!!!");
        Assert.hasLength(email, "Email é obrigatório");
        Assert.hasLength(name, "Nome é obrigatório");
        Assert.notNull(address, "Endereço é obrigatório");
        Assert.state(salary.compareTo(BigDecimal.ZERO) > 0, "Salário não pode ser negativo");

        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public String getDocument() {
        return document;
    }

    public String getName() {
        return name;
    }

    public void setProposalStatus(ProposalStatus status) {
        this.status = status;
    }

    public FinancialAnalysisRequest toFinancialAnalysis() {
        return new FinancialAnalysisRequest(document, name, id);
    }

}
