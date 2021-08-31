package br.com.zupacademy.yudi.proposta.card;

import br.com.zupacademy.yudi.proposta.shared.validation.Base64;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public class BiometryRequest {

    @NotBlank
    @Base64
    private String biometryId;
    private Logger LOG = LoggerFactory.getLogger(BiometryRequest.class);

    public String getBiometryId() {
        return biometryId;
    }

    public Biometry toBiometry(Card card) {
        Biometry biometry = new Biometry(biometryId, card);
        LOG.info("New biometry = {} for card = {}", biometry.getUuid(), card.getUuid());
        return biometry;
    }
}