package br.com.zupacademy.yudi.proposta.external_services.contas;

import br.com.zupacademy.yudi.proposta.card.TravelNotification;
import br.com.zupacademy.yudi.proposta.card.TravelNotificationRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

import static br.com.zupacademy.yudi.proposta.card.TravelNotificationStatus.NOT_NOTIFIED;

@EnableScheduling
@Configuration
public class TravelNotifierScheduler {

    private final Logger LOG = LoggerFactory.getLogger(TravelNotifierScheduler.class);

    @Autowired
    private CardClient cardClient;

    private PlatformTransactionManager transactionManager;
    private TransactionTemplate transactionTemplate;

    private final TravelNotificationRepository repository;

    public TravelNotifierScheduler(TravelNotificationRepository repository,
                                   PlatformTransactionManager transactionManager) {
        this.repository = repository;
        this.transactionManager = transactionManager;
    }

    @Scheduled(fixedDelay = 15000)
    void notifyTravel() {
        LOG.info("[Legacy application travel notifier scheduler running...]");
        Page<TravelNotification> travelNotificationList =
                repository.findAllByStatus(NOT_NOTIFIED, PageRequest.of(0, 5));
        LOG.info("Number of NOT_NOTIFIED notifications = {}", travelNotificationList.getTotalElements());
        if (!travelNotificationList.isEmpty()) {
            travelNotificationList.forEach(this::execute);
            updateStatus(travelNotificationList.toList());
        }
    }

    private void execute(TravelNotification travel) {
        try {
            TravelNotifierResponse response =
                    cardClient.notifyTravel(travel.getCardNumber(),
                            new TravelNotifierRequest(travel.getDestiny(), travel.getCheckout()));
            LOG.info("Request sent to card = {} to notify travel = {}",
                    travel.getCardIdentification(), travel.getUuid());
            if (response.getResultado().toUpperCase().equals("CRIADO")) {
                travel.setStatusToNotified();
                LOG.info("Legacy application notified.");
            }
        } catch (FeignException exception) {
            LOG.info("Feign threw an Exception, message = {}", exception.getMessage());
        }
        LOG.info("Status set to {} for travel notification = {}", travel.getStatus(), travel.getUuid());
    }

    private void updateStatus(List<TravelNotification> notifications) {
        transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                repository.saveAll(notifications);
            }
        });
        LOG.info("{} travel notifications saved on database", notifications.size());
    }
}
