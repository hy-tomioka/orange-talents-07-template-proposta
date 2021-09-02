package br.com.zupacademy.yudi.proposta.card;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends PagingAndSortingRepository<Card, Long> {

    Page<Card> findAllByStatus(CardStatus status, Pageable pagination);

    Optional<Card> findByUuid(UUID uuid);
}
