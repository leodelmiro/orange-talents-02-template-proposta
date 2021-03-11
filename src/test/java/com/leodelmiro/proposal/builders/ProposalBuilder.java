package com.leodelmiro.proposal.builders;

import com.leodelmiro.proposal.proposal.Proposal;
import com.leodelmiro.proposal.proposal.ProposalRequesterAddress;
import com.leodelmiro.proposal.proposal.RequesterAddressRequest;

import java.math.BigDecimal;

public class ProposalBuilder {

    private String document;
    private String email;
    private String name;
    private ProposalRequesterAddress address;
    private BigDecimal salary;


    public ProposalBuilder withDocument(String document) {
        this.document = document;
        return this;
    }

    public ProposalBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public ProposalBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProposalBuilder withAddress(ProposalRequesterAddress address) {
        this.address = address;
        return this;
    }

    public ProposalBuilder withSalary(BigDecimal salary) {
        this.salary = salary;
        return this;
    }

    public ProposalBuilder defaultValues() {
        this.document = "404.761.395-97";
        this.email = "test@test.com";
        this.name = "Testador";
        this.address = new ProposalRequesterAddress("Rua dos testes", "90", "11740000");
        this.salary = new BigDecimal("2000");
        return this;
    }

    public Proposal build() {
        return new Proposal(document, email, name, address, salary);
    }
}
