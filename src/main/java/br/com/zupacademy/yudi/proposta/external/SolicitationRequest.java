package br.com.zupacademy.yudi.proposta.external;

import br.com.zupacademy.yudi.proposta.proposal.Proposal;

public class SolicitationRequest {

    private String documento;
    private String nome;
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
