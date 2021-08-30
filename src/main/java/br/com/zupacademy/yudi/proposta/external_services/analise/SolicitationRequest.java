package br.com.zupacademy.yudi.proposta.external_services.analise;

import br.com.zupacademy.yudi.proposta.proposal.Proposal;

import javax.validation.constraints.NotBlank;

public class SolicitationRequest {

    @NotBlank
    private String documento;
    @NotBlank
    private String nome;
    @NotBlank
    private String idProposta;

    public SolicitationRequest(Proposal proposal) {
        this.documento = proposal.getDocument();
        this.nome = proposal.getName();
        this.idProposta = String.valueOf(proposal.getId());
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
