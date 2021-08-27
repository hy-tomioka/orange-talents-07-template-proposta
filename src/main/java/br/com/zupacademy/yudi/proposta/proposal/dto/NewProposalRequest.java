package br.com.zupacademy.yudi.proposta.proposal.dto;

import br.com.zupacademy.yudi.proposta.proposal.Proposal;
import br.com.zupacademy.yudi.proposta.shared.validation.CpfOrCnpj;
import br.com.zupacademy.yudi.proposta.shared.validation.UniqueValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class NewProposalRequest {

    @CpfOrCnpj
    @NotNull
    private String document;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotNull
    @Positive
    private BigDecimal salary;

    private final Logger logger = LoggerFactory.getLogger(NewProposalRequest.class);

    public NewProposalRequest(String document, String email, String name, String address, BigDecimal salary) {
        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    public Proposal toProposal() {
        logger.info("Proposal document={} and salary={} created", document, salary);
        return new Proposal(document, email, name, address, salary);
    }

    public String getDocument() {
        return document;
    }
}
