package br.com.zupacademy.yudi.proposta.external_services.contas;

public class CardRequest {

    private String documento;
    private String nome;
    private String idProposta;

    public CardRequest(String documento, String nome, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
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
