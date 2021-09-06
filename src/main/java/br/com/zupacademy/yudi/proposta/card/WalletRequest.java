package br.com.zupacademy.yudi.proposta.card;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public class WalletRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String carteira;

    public WalletRequest(String email, String carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getCarteira() {
        return carteira;
    }
}
