package br.com.zupacademy.yudi.proposta.external_services.contas;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public class CardBlockerResponse {

    private String resultado;

    public String getResultado() {
        return resultado;
    }
}
