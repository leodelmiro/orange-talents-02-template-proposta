package com.leodelmiro.proposal.newproposal;

import com.leodelmiro.proposal.common.validation.CPForCNPJ;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class NewProposalRequesterRequest {

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
    public NewProposalRequesterRequest() {
    }

    public NewProposalRequesterRequest(@NotBlank String document, @NotBlank @Email String email, @NotBlank String name, @NotBlank String address, @PositiveOrZero BigDecimal salary) {
        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    public String getDocument() {
        return document;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public ProposalRequester toModel() {
        return new ProposalRequester(document, email, name, address, salary);
    }
}
