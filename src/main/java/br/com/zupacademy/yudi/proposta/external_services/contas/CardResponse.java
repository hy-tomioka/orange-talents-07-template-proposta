package br.com.zupacademy.yudi.proposta.external_services.contas;

import br.com.zupacademy.yudi.proposta.proposal.Proposal;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public class CardResponse {

    @JsonAlias("id")
    private String cardNumber;
    private LocalDateTime emitidoEm;
    private String titular;
    private String idProposta;

    @Override
    public String toString() {
        return "CardResponse{" +
                "id='" + cardNumber + '\'' +
                ", emitidoEm=" + emitidoEm +
                ", titular='" + titular + '\'' +
                ", idProposta='" + idProposta + '\'' +
                '}';
    }

    public Card toCard(EntityManager manager) {
        Proposal proposal = manager.find(Proposal.class, Long.valueOf(idProposta));
        return new Card(cardNumber, emitidoEm, titular, proposal);
    }
}
