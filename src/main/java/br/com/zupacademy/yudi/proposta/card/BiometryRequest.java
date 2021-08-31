package br.com.zupacademy.yudi.proposta.card;

import br.com.zupacademy.yudi.proposta.shared.validation.Base64;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public class BiometryRequest {

    @NotBlank
    @Base64
    private String biometryId;

    public String getBiometryId() {
        return biometryId;
    }

    public Biometry toBiometry(Card card) {
        return new Biometry(biometryId, card);
    }
}