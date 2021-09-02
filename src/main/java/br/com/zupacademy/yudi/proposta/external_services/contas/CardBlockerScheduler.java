package br.com.zupacademy.yudi.proposta.external_services.contas;

import br.com.zupacademy.yudi.proposta.card.Card;
import br.com.zupacademy.yudi.proposta.card.CardRepository;
import br.com.zupacademy.yudi.proposta.shared.transaction.TransactionRunner;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.zupacademy.yudi.proposta.card.CardStatus.TEMPORARY_BLOCKED;

@Configuration
@EnableScheduling
public class CardBlockerScheduler {

    private Logger LOG = LoggerFactory.getLogger(CardBlockerScheduler.class);
    private final EntityManager manager;

    @Autowired
    private PlatformTransactionManager transactionManager;
    private TransactionTemplate transactionTemplate;

    @Autowired
    private CardClient cardClient;
    private final CardRepository cardRepository;

    public CardBlockerScheduler(EntityManager manager, TransactionRunner transactionRunner, CardRepository cardRepository) {
        this.manager = manager;
        this.cardRepository = cardRepository;
    }

    @Scheduled(fixedDelay = 11000)
    void block() {
        LOG.info("[Legacy application card blocker scheduler running...]");
        Page<Card> cards = cardRepository.findAllByStatus(TEMPORARY_BLOCKED, PageRequest.of(0, 5));
        LOG.info("Total cards to block = {}", cards.getTotalElements());
        cards.forEach(this::execute);
        List<Card> blockedCards = cards.stream().collect(Collectors.toList());
        updateStatus(blockedCards);
    }

    private void execute(Card card) {
        try {
            CardBlockerResponse response = cardClient.block(card.getNumber(),
                    new CardBlockerRequest("propostas"));
            if (response.getResultado().equals("BLOQUEADO")) {
                card.fullBlock();
                LOG.info("Card = {} blocked on legacy application.", card.getUuid());
            }
        } catch (FeignException exception) {
            LOG.info("Feign threw an Exception, message = {}", exception.getMessage());
            if (exception.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()) {
                LOG.info("Card = {} is already blocked on legacy application", card.getUuid());
                card.fullBlock();
            }
        }
        LOG.info("Card = {} status set to {}", card.getUuid(), card.getStatus());
    }

    private void updateStatus(List<Card> cards) {
        if (cards.isEmpty()) return;
        transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                cardRepository.saveAll(cards);
            }
        });
        LOG.info("{} blocked cards saved on database", cards.size());
    }
}
