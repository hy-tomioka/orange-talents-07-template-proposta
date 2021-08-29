package br.com.zupacademy.yudi.proposta.proposal;

import static org.springframework.util.Assert.notNull;
import static org.springframework.util.Assert.state;

public enum ProposalStatus {

    NAO_ELEGIVEL,
    ELEGIVEL;


    public static ProposalStatus fromValue(String solicitationResult) {
        notNull(solicitationResult, "[Analise] results must not be null.");
        state(solicitationResult.toUpperCase().equals("SEM_RESTRICAO") ||
                solicitationResult.toUpperCase().equals("COM_RESTRICAO"),
                "[Analise] results returned unexpected results.");
        if (solicitationResult.toUpperCase().equals("SEM_RESTRICAO")) {
            return ELEGIVEL;
        }
        return NAO_ELEGIVEL;
    }
}
