package br.com.zupacademy.yudi.proposta.card;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TravelNotificationRepository extends PagingAndSortingRepository<TravelNotification, Long> {
    Page<TravelNotification> findAllByStatus(TravelNotificationStatus notNotified, Pageable pagination);
}
