package com.leodelmiro.builders;

import com.leodelmiro.proposal.newproposal.RequesterAddressRequest;

public class RequesterAddressRequestBuilder {
    private String address;
    private String number;
    private String cep;

    public RequesterAddressRequestBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public RequesterAddressRequestBuilder withNumber(String number) {
        this.number = number;
        return this;
    }

    public RequesterAddressRequestBuilder withCep(String cep) {
        this.cep = cep;
        return this;
    }

    public RequesterAddressRequestBuilder defaultValues() {
        this.address = "Rua dos Testes";
        this.number = "90";
        this.cep = "11740000";
        return this;
    }

    public RequesterAddressRequest build() {
        return new RequesterAddressRequest(address, number, cep);
    }
}
