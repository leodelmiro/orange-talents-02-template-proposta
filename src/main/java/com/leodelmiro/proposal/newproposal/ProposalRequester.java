package com.leodelmiro.proposal.newproposal;

import com.leodelmiro.proposal.common.validation.CPForCNPJ;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tb_requester")
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

    @NotBlank
    private String address;

    @PositiveOrZero
    @NotNull
    private BigDecimal salary;

    @Deprecated
    public ProposalRequester() {
    }

    public ProposalRequester(@NotBlank String document, @NotBlank @Email String email, @NotBlank String name, @NotBlank String address, @PositiveOrZero BigDecimal salary) {
        Assert.hasLength(document, "Documento é obrigatório!!!");
        Assert.hasLength(email, "Email é obrigatório");
        Assert.hasLength(name, "Nome é obrigatório");
        Assert.hasLength(address, "Endereço é obrigatório");
        Assert.state(salary.compareTo(BigDecimal.ZERO) >= 0, "Salário não pode ser negativo");

        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public boolean alreadyHasProposal(EntityManager entityManager) {
        Query query = entityManager.createQuery("SELECT 1 FROM ProposalRequester r WHERE r.document = :requesterDocument");
        query.setParameter("requesterDocument", document);
        List<?> list = query.getResultList();

        return !list.isEmpty();
    }
}
