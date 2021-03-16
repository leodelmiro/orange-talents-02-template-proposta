package com.leodelmiro.proposal.proposal;

import com.leodelmiro.proposal.cards.Card;
import com.leodelmiro.proposal.cards.CardRequest;
import com.leodelmiro.proposal.cards.CardApiResponse;
import com.leodelmiro.proposal.cards.CardsClient;
import com.leodelmiro.proposal.common.validation.CPForCNPJ;
import com.leodelmiro.proposal.financialanalysis.FinancialAnalysisClient;
import com.leodelmiro.proposal.financialanalysis.FinancialAnalysisRequest;
import com.leodelmiro.proposal.financialanalysis.FinancialAnalysisResponse;
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
    @Column(nullable = false)
    private String document;

    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @Embedded
    @Column(nullable = false)
    private ProposalRequesterAddress address;

    @Positive
    @NotNull
    @Column(nullable = false)
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProposalStatus status = ProposalStatus.NOT_ELIGIBLE;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "card_id")
    private Card card;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * @Deprecated for framework use only
     */
    @Deprecated
    public Proposal() {
    }

    public Proposal(@NotBlank String document, @NotBlank @Email String email, @NotBlank String name, @NotBlank ProposalRequesterAddress address, @PositiveOrZero BigDecimal salary) {
        Assert.hasLength(document, "Documento é obrigatório!!!");
        Assert.hasLength(email, "Email é obrigatório");
        Assert.hasLength(name, "Nome é obrigatório");
        Assert.notNull(address, "Endereço é obrigatório");
        Assert.state(salary.compareTo(BigDecimal.ZERO) > 0, "Salário não pode ser negativo");

        this.document = document.replaceAll("[.-]", "");
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

    public String getEmail() {
        return email;
    }

    public ProposalRequesterAddress getAddress() {
        return address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public ProposalStatus getStatus() {
        return status;
    }

    public Card getCard() {
        return card;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void updateStatus(FinancialAnalysisClient client) {
        FinancialAnalysisResponse response = client.financialAnalysis(toFinancialAnalysis());
        this.status = response.statusToProposalStatus();
    }

    public void associateCard(CardsClient client) {
        CardApiResponse response = client.getCard(toCardRequest());
        this.card = response.toModel(this);
    }

    public FinancialAnalysisRequest toFinancialAnalysis() {
        return new FinancialAnalysisRequest(document, name, id);
    }

    public CardRequest toCardRequest() {
        return new CardRequest(document, name, id.toString());
    }
}
