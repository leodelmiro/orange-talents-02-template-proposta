package com.leodelmiro.proposal.proposal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class ProposalRequesterAddress {

    @NotBlank
    @Column(nullable = false)
    private String address;

    @NotBlank
    @Column(nullable = false)
    private String number;

    @NotBlank
    @Column(nullable = false)
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
