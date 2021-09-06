package br.com.zupacademy.yudi.proposta.card;

import br.com.zupacademy.yudi.proposta.external_services.contas.CardClient;
import br.com.zupacademy.yudi.proposta.external_services.contas.WalletResponse;
import br.com.zupacademy.yudi.proposta.shared.transaction.TransactionRunner;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/cards/")
public class WalletController {

    @Autowired
    private CardClient client;

    private final CardRepository cardRepository;
    private final TransactionRunner transactionRunner;
    private Logger LOG = LoggerFactory.getLogger(WalletController.class);

    public WalletController(CardRepository cardRepository, TransactionRunner transactionRunner) {
        this.cardRepository = cardRepository;
        this.transactionRunner = transactionRunner;
    }

    @PostMapping("/{cardId}/wallet")
    public ResponseEntity<Void> includeService(@PathVariable("cardId") UUID cardId, @RequestBody @Valid WalletRequest request,
                                               UriComponentsBuilder uriComponentsBuilder) {

        Card card = cardRepository.findByUuid(cardId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Card must exist."));

        try {
            WalletResponse response = client.includeWallet(card.getNumber(), request);
            return processResponse(response, request, card, uriComponentsBuilder);
        } catch (FeignException exception) {
            LOG.warn("Application CONTAS returned status {} for card = {}", exception.status(), card.getUuid());
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    private ResponseEntity<Void> processResponse(WalletResponse response, WalletRequest request, Card card,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        if (response.getResultado().toUpperCase().equals("ASSOCIADA")) {
            Wallet wallet = response.toWallet(request.getCarteira(), card);
            transactionRunner.saveAndCommit(wallet);
            LOG.info("wallet = {} saved on database", wallet.getUuid());
            URI uri = uriComponentsBuilder.path("/api/cards/{cardId}/wallet/{walletId}")
                    .buildAndExpand(card.getUuid(), wallet.getUuid()).toUri();
            return ResponseEntity.created(uri).build();
        }
        return ResponseEntity.unprocessableEntity().build();
    }
}
