package br.com.zupacademy.yudi.proposta.card;

import br.com.zupacademy.yudi.proposta.shared.transaction.TransactionRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.util.Assert.notNull;

@RestController
@RequestMapping("/api/cards")
public class CardBlockerController {

    private Logger LOG = LoggerFactory.getLogger(CardBlockerController.class);

    private final TransactionRunner transactionRunner;
    private final CardRepository cardRepository;

    public CardBlockerController(TransactionRunner transactionRunner, CardRepository cardRepository) {
        this.transactionRunner = transactionRunner;
        this.cardRepository = cardRepository;
    }

    @GetMapping("/block")
    public ResponseEntity<String> foo() {
        return ResponseEntity.badRequest().body("Card identifier is necessary.");
    }

    @GetMapping("/{uuid}/block")
    public ResponseEntity<Void> block(@PathVariable("uuid") UUID uuid, HttpServletRequest request) {
        notNull(uuid, "Card identifier must not be null.");

        LOG.info("Searching for card = {}", uuid);
        Card card = cardRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Card must exist."));
        LOG.info("Card = {} found.", card.getUuid());

        LOG.info("Verifying if card = {} is blocked.", card.getUuid());
        if (card.isBlocked()) throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Card is already blocked.");
        card.addBlockAttempt(request);
        LOG.info("Card = {} blocked successfully", card.getUuid());
        transactionRunner.saveAndCommit(card);

        return ResponseEntity.ok().build();
    }
}
