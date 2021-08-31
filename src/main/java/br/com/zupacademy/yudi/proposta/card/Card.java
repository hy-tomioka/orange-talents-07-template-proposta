package br.com.zupacademy.yudi.proposta.card;

import br.com.zupacademy.yudi.proposta.proposal.Proposal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;
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

    @Column(nullable = false)
    private UUID uuid = UUID.randomUUID();

    @OneToMany(mappedBy = "card", cascade = ALL)
    private Set<Biometry> biometries = new HashSet<>();

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

    public UUID getUuid() {
        return uuid;
    }

    public void addNewBiometry(Biometry biometry) {
        this.biometries.add(biometry);
    }
}
