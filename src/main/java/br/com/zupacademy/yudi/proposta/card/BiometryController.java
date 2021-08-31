package br.com.zupacademy.yudi.proposta.card;

import br.com.zupacademy.yudi.proposta.shared.transaction.TransactionRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.created;

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

    @PostMapping("/{uuid}/biometries")
    public ResponseEntity<Void> create(@PathVariable("uuid") UUID uuid, @RequestBody @Valid BiometryRequest request,
                                       UriComponentsBuilder uriComponentsBuilder) {
        LOG.info("Searching for card = {}", uuid);
        Card card = manager.createQuery("select c from Card c where uuid = :puuid", Card.class)
                .setParameter("puuid", uuid)
                .getResultStream().findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Card must exist."));
        LOG.info("Card = {} found.", card.getUuid());

        Biometry biometry = request.toBiometry(card);
        card.addNewBiometry(biometry);
        LOG.info("Biometry = {} added to card = {}", biometry.getUuid(), uuid);
        transactionRunner.saveAndCommit(card);

        return created(uriComponentsBuilder.path("/api/cards/{card_uuid}/biometries/{uuid}")
                .buildAndExpand(uuid, biometry.getUuid()).toUri())
                .build();
    }
}
