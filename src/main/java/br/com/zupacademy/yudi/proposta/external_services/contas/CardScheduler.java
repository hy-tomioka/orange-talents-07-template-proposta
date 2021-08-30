package br.com.zupacademy.yudi.proposta.external_services.contas;

import br.com.zupacademy.yudi.proposta.proposal.Proposal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Configuration
@EnableScheduling
public class CardScheduler {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @PersistenceContext
    private EntityManager manager;

    private TransactionTemplate transactionTemplate;

    @Autowired
    private CardClient client;

    private final Logger LOG = LoggerFactory.getLogger(CardScheduler.class);

    @Scheduled(fixedDelay = 5000)
    void include() {
        LOG.info("[Proposal card association scheduler running...]");
        List<Proposal> proposals = manager.createQuery("select p from Proposal p where p.card.id is NULL",
                Proposal.class).getResultList();
        LOG.info("Query result list size = {}", proposals.size());
        if (!proposals.isEmpty()) {
            Proposal proposal = proposals.stream().findFirst().get();
            CardResponse cardResponse = getCardResponse(proposal);
            saveCardAndMergeProposal(proposal, cardResponse);
        }
    }

    private CardResponse getCardResponse(Proposal proposal) {
        return sendRequestToContas(proposal);
    }

    private CardResponse sendRequestToContas(Proposal proposal) {
        CardResponse response = client.generate(new CardRequest(proposal.getDocument(), proposal.getName(),
                String.valueOf(proposal.getId())));
        LOG.info("Request sent for proposals document = {}", proposal.getDocument());
        return response;
    }

    private void saveCardAndMergeProposal(Proposal proposal, CardResponse cardResponse) {
        transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Card card = cardResponse.toCard(manager);
                manager.persist(card);
                LOG.info("Card number = {} saved on database", card.getNumber());
                proposal.setCard(card);
                manager.merge(proposal);
                LOG.info("Card number = {} associated with proposal document = {}", card.getNumber(), proposal.getDocument());
            }
        });
    }
}
