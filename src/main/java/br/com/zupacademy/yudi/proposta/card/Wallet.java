package br.com.zupacademy.yudi.proposta.card;

import javax.persistence.*;

import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod service;

    @Column(nullable = false)
    private UUID uuid;

    @OneToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    public Wallet(UUID uuid, PaymentMethod service, Card card) {
        this.uuid = uuid;
        this.service = service;
        this.card = card;
    }

    public UUID getUuid() {
        return uuid;
    }
}
