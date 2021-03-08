package com.leodelmiro.proposal.newproposal;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class RequesterAddress {

    @NotBlank
    private String address;

    @NotBlank
    private String number;

    @NotBlank
    private String cep;

    /**
     * @Deprecated only for framework
     */
    public RequesterAddress() {
    }

    public RequesterAddress(@NotBlank String address, @NotBlank String number, @NotBlank String cep) {
        this.address = address;
        this.number = number;
        this.cep = cep;
    }
}
