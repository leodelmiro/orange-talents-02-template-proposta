package com.leodelmiro.proposal.proposal;

import com.leodelmiro.proposal.common.validation.CPForCNPJ;
import com.leodelmiro.proposal.common.validation.UniqueValue;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class NewProposalRequest {

    @CPForCNPJ
    @UniqueValue(domainClass = Proposal.class, fieldName = "document")
    @NotBlank
    private String document;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;

    @NotNull
    private RequesterAddressRequest address;

    @PositiveOrZero
    @NotNull
    private BigDecimal salary;

    @Deprecated
    public NewProposalRequest() {
    }

    public NewProposalRequest(@NotBlank String document, @NotBlank @Email String email, @NotBlank String name,
                              @NotNull @Valid RequesterAddressRequest address, @PositiveOrZero BigDecimal salary) {
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

    public RequesterAddressRequest getAddress() {
        return address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public Proposal toModel() {
        return new Proposal(document, email, name, address.toModel(), salary);
    }
}
