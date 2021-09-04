package br.com.zupacademy.yudi.proposta.card;

import br.com.zupacademy.yudi.proposta.shared.transaction.TransactionRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.util.Assert.notNull;

@RestController
@RequestMapping("/api/cards")
public class TravelNotificationController {

    private final Logger LOG = LoggerFactory.getLogger(TravelNotificationController.class);
    private final CardRepository cardRepository;
    private final TransactionRunner transactionRunner;

    public TravelNotificationController(CardRepository cardRepository, TransactionRunner transactionRunner) {
        this.cardRepository = cardRepository;
        this.transactionRunner = transactionRunner;
    }

    @PostMapping("/{cardId}/notify-travel")
    public ResponseEntity<Void> notify(HttpServletRequest httpRequest, @PathVariable("cardId") UUID cardId,
                                       @RequestBody @Valid TravelNotificationRequest request,
                                       UriComponentsBuilder uriComponentsBuilder) {
        notNull(cardId, "Card identifier must not be null.");
        LOG.info("Searching for card = {}", cardId);
        Card card = cardRepository.findByUuid(cardId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Card must exist."));
        LOG.info("Card = {} found.", card.getUuid());

        TravelNotification travelNotification = request.toTravelNotification(httpRequest);
        transactionRunner.saveAndCommit(travelNotification);
        LOG.info("Travel notification = {} saved on database", travelNotification.getUuid());

        return ResponseEntity.created(uriComponentsBuilder
                .path("/api/travel-notifications/{uuid}")
                .buildAndExpand(travelNotification.getUuid())
                .toUri())
                .build();
    }
}
