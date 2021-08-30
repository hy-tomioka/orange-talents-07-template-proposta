package br.com.zupacademy.yudi.proposta.proposal.dto;

import br.com.zupacademy.yudi.proposta.proposal.Proposal;
import br.com.zupacademy.yudi.proposta.proposal.ProposalStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public class ProposalStatusResponse {

    private ProposalStatus status;

    public ProposalStatusResponse(Proposal proposal) {
        this.status = proposal.getStatus();
    }
}
