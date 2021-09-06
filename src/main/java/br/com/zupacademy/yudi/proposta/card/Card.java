package br.com.zupacademy.yudi.proposta.card;

import br.com.zupacademy.yudi.proposta.proposal.Proposal;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static br.com.zupacademy.yudi.proposta.card.CardStatus.*;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.EnumType.STRING;
import static org.springframework.util.Assert.notNull;
import static org.springframework.util.Assert.state;

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

    @OneToMany(mappedBy = "card", cascade = PERSIST)
    private Set<Biometry> biometries = new HashSet<>();

    @OneToMany(mappedBy = "card", cascade = PERSIST)
    private Set<Block> blocks = new HashSet<>();

    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    private CardStatus status = AVAILABLE;

    @OneToMany(mappedBy = "card", cascade = PERSIST)
    private Set<TravelNotification> travelNotifications = new HashSet<>();

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

    public CardStatus getStatus() {
        return status;
    }

    public void addNewBiometry(Biometry biometry) {
        notNull(biometry, "Biometry is required.");
        this.biometries.add(biometry);
    }

    public void addBlockAttempt(HttpServletRequest request) {
        notNull(request, "Request information are required.");
        state(status == AVAILABLE, "Card must not be blocked.");
        this.blocks.add(new Block(request.getRemoteAddr(), request.getHeader("User-Agent"), this));
        this.status = TEMPORARY_BLOCKED;
    }

    public boolean isBlocked() {
        notNull(status, "Card must have a state, whether if it is blocked or not.");
        return status == TEMPORARY_BLOCKED;
    }

    public void fullBlock() {
        this.status = BLOCKED;
    }

    public void addTravelNotification(TravelNotification travelNotification) {
        this.travelNotifications.add(travelNotification);
    }
}
