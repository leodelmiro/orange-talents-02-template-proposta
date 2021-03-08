package com.leodelmiro.proposal.proposal;

import com.leodelmiro.proposal.cards.Card;
import com.leodelmiro.proposal.cards.CardRequest;
import com.leodelmiro.proposal.common.validation.CPForCNPJ;
import com.leodelmiro.proposal.financialanalysis.FinancialAnalysisRequest;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_proposals")
public class Proposal {

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
    private ProposalRequesterAddress address;

    @Positive
    @NotNull
    private BigDecimal salary;

    @Enumerated
    private ProposalStatus status;

    @OneToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "card_id")
    private Card card;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * @Deprecated only for framework
     */
    public Proposal() {
    }

    public Proposal(@NotBlank String document, @NotBlank @Email String email, @NotBlank String name, @NotBlank ProposalRequesterAddress address, @PositiveOrZero BigDecimal salary) {
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

    public void setCard(Card card) {
        this.card = card;
    }

    public FinancialAnalysisRequest toFinancialAnalysis() {
        return new FinancialAnalysisRequest(document, name, id);
    }

    public CardRequest toCardRequest() {
        return new CardRequest(document, name, id.toString());
    }
}
