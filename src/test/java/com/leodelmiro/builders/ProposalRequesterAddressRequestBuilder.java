package com.leodelmiro.builders;

import com.leodelmiro.proposal.proposal.RequesterAddressRequest;

public class ProposalRequesterAddressRequestBuilder {
    private String address;
    private String number;
    private String cep;

    public ProposalRequesterAddressRequestBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public ProposalRequesterAddressRequestBuilder withNumber(String number) {
        this.number = number;
        return this;
    }

    public ProposalRequesterAddressRequestBuilder withCep(String cep) {
        this.cep = cep;
        return this;
    }

    public ProposalRequesterAddressRequestBuilder defaultValues() {
        this.address = "Rua dos Testes";
        this.number = "90";
        this.cep = "11740000";
        return this;
    }

    public RequesterAddressRequest build() {
        return new RequesterAddressRequest(address, number, cep);
    }
}
