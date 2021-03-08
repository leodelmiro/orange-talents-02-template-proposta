package com.leodelmiro.proposal.proposal;

import javax.validation.constraints.NotBlank;

public class RequesterAddressResponse {

    private String address;
    private String number;
    private String cep;

    public RequesterAddressResponse(ProposalRequesterAddress entity) {
        this.address = entity.getAddress();
        this.number = entity.getNumber();
        this.cep = entity.getCep();
    }

    public String getAddress() {
        return address;
    }

    public String getNumber() {
        return number;
    }

    public String getCep() {
        return cep;
    }
}
