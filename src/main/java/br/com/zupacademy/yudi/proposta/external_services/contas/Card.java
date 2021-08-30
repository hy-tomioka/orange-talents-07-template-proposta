package br.com.zupacademy.yudi.proposta.external_services.contas;

import br.com.zupacademy.yudi.proposta.proposal.Proposal;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String number;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String holder;

    @OneToOne(mappedBy = "card", cascade = PERSIST, orphanRemoval = true)
    @JoinColumn(name = "proposal_id")
    private Proposal proposal;

    @Deprecated
    private Card() {
    }

    public Card(String number, LocalDateTime createdAt, String holder, Proposal proposal) {
        this.number = number;
        this.createdAt = createdAt;
        this.holder = holder;
        this.proposal = proposal;
    }

    public String getNumber() {
        return number;
    }
}
