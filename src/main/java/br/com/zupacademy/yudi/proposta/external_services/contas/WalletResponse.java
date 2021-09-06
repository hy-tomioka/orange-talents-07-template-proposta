package br.com.zupacademy.yudi.proposta.external_services.contas;

import br.com.zupacademy.yudi.proposta.card.Card;
import br.com.zupacademy.yudi.proposta.card.PaymentMethod;
import br.com.zupacademy.yudi.proposta.card.Wallet;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public class WalletResponse {

    private String resultado;
    private String id;

    public Wallet toWallet(String wallet, Card card) {
        return new Wallet(UUID.fromString(id), PaymentMethod.fromValue(wallet), card);
    }

    public String getResultado() {
        return resultado;
    }
}
