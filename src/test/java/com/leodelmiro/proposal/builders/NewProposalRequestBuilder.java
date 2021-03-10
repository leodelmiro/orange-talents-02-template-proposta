package com.leodelmiro.proposal.builders;

import com.leodelmiro.proposal.proposal.NewProposalRequest;
import com.leodelmiro.proposal.proposal.RequesterAddressRequest;

import java.math.BigDecimal;

public class NewProposalRequestBuilder {

    private String document;
    private String email;
    private String name;
    private RequesterAddressRequest address;
    private BigDecimal salary;


    public NewProposalRequestBuilder withDocument(String document) {
        this.document = document;
        return this;
    }

    public NewProposalRequestBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public NewProposalRequestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public NewProposalRequestBuilder withAddress(RequesterAddressRequest address) {
        this.address = address;
        return this;
    }

    public NewProposalRequestBuilder withSalary(BigDecimal salary) {
        this.salary = salary;
        return this;
    }

    public NewProposalRequestBuilder defaultValues() {
        this.document = "404.761.395-97";
        this.email = "test@test.com";
        this.name = "Testador";
        this.address = new RequesterAddressRequest("Rua dos testes", "90", "11740000");
        this.salary = new BigDecimal("2000");
        return this;
    }

    public NewProposalRequest build() {
        return new NewProposalRequest(document, email, name, address, salary);
    }
}
