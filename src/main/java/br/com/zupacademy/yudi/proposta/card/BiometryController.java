package br.com.zupacademy.yudi.proposta.card;

import br.com.zupacademy.yudi.proposta.shared.transaction.TransactionRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/cards")
public class BiometryController {

    private final EntityManager manager;
    private final TransactionRunner transactionRunner;

    private final Logger LOG = LoggerFactory.getLogger(BiometryController.class);

    public BiometryController(EntityManager manager, TransactionRunner transactionRunner) {
        this.manager = manager;
        this.transactionRunner = transactionRunner;
    }

    @PostMapping("/{uuid}/biometry")
    public void create(@PathVariable("uuid") UUID uuid, @RequestBody @Valid BiometryRequest request) {

        Card card = manager.createQuery("select c from Card c where uuid = :puuid", Card.class)
                .setParameter("puuid", uuid)
                .getResultStream().findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Card must exist."));

        Biometry biometry = request.toBiometry(card);
        card.addNewBiometry(biometry);

        transactionRunner.saveAndCommit(card);

        LOG.info("UUID do cartao = {} - biometricId = {}", uuid, request.getBiometryId());
    }
}
