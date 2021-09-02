package br.com.zupacademy.yudi.proposta.external_services.contas;

import javax.validation.constraints.NotBlank;

public class CardBlockerRequest {

    @NotBlank
    private String sistemaResponsavel;

    public CardBlockerRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}
