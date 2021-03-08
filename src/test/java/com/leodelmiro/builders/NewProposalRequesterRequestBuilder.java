package com.leodelmiro.builders;

import com.leodelmiro.proposal.newproposal.NewProposalRequesterRequest;
import com.leodelmiro.proposal.newproposal.RequesterAddressRequest;

import java.math.BigDecimal;

public class NewProposalRequesterRequestBuilder {

    private String document;
    private String email;
    private String name;
    private RequesterAddressRequest address;
    private BigDecimal salary;


    public NewProposalRequesterRequestBuilder withDocument(String document) {
        this.document = document;
        return this;
    }

    public NewProposalRequesterRequestBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public NewProposalRequesterRequestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public NewProposalRequesterRequestBuilder withAddress(RequesterAddressRequest address) {
        this.address = address;
        return this;
    }

    public NewProposalRequesterRequestBuilder withSalary(BigDecimal salary) {
        this.salary = salary;
        return this;
    }

    public NewProposalRequesterRequestBuilder defaultValues() {
        this.document = "404.761.395-97";
        this.email = "test@test.com";
        this.name = "Testador";
        this.address = new RequesterAddressRequest("Rua dos testes", "90", "11740000");
        this.salary = new BigDecimal("2000");
        return this;
    }

    public NewProposalRequesterRequest build() {
        return new NewProposalRequesterRequest(document, email, name, address, salary);
    }
}
