package br.com.zupacademy.yudi.proposta.card;

import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends Repository<Card, Long> {
    Optional<Card> findByUuid(UUID uuid);
}
