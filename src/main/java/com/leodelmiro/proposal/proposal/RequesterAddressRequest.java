package com.leodelmiro.proposal.proposal;

import javax.validation.constraints.NotBlank;

public class RequesterAddressRequest {

    @NotBlank
    private String address;

    @NotBlank
    private String number;

    @NotBlank
    private String cep;

    public RequesterAddressRequest(@NotBlank String address, @NotBlank String number, @NotBlank String cep) {
        this.address = address;
        this.number = number;
        this.cep = cep;
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

    public ProposalRequesterAddress toModel() {
        return new ProposalRequesterAddress(address, number, cep);
    }
}
