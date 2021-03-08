package com.leodelmiro.proposal.proposal;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class ProposalRequesterAddress {

    @NotBlank
    private String address;

    @NotBlank
    private String number;

    @NotBlank
    private String cep;

    /**
     * @Deprecated only for framework
     */
    public ProposalRequesterAddress() {
    }

    public ProposalRequesterAddress(@NotBlank String address, @NotBlank String number, @NotBlank String cep) {
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
}
