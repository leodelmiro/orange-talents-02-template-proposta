package com.leodelmiro.builders;

import com.leodelmiro.proposal.newproposal.NewProposalRequesterRequest;

import java.math.BigDecimal;

public class NewProposalRequesterRequestBuilder {

    private String document;
    private String email;
    private String name;
    private String address;
    private BigDecimal salary;


    public NewProposalRequesterRequestBuilder withDocument(String document) {
        this.document = document;
        return this;
    }

    public NewProposalRequesterRequestBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public NewProposalRequesterRequestBuilder withName(String name){
        this.name = name;
        return this;
    }

    public NewProposalRequesterRequestBuilder withAddress(String address) {
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
        this.address = "Rua dos testes";
        this.salary = new BigDecimal("2000");
        return this;
    }

    public NewProposalRequesterRequest build(){
        return new NewProposalRequesterRequest(document, email, name, address, salary);
    }
}
