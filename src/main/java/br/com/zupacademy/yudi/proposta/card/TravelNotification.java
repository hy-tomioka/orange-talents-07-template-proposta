package br.com.zupacademy.yudi.proposta.card;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

import static br.com.zupacademy.yudi.proposta.card.TravelNotificationStatus.NOTIFIED;
import static br.com.zupacademy.yudi.proposta.card.TravelNotificationStatus.NOT_NOTIFIED;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "travel_notifications")
public class TravelNotification {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String destiny;

    @CreationTimestamp
    private LocalDate notifiedAt;

    @Column(nullable = false)
    private LocalDate checkout;

    @Column(nullable = false)
    private String userAgent;

    @Column(nullable = false)
    private String clientIp;

    @Column(nullable = false)
    private UUID uuid = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @Column(nullable = false)
    @Enumerated(STRING)
    private TravelNotificationStatus status = NOT_NOTIFIED;

    @Deprecated
    private TravelNotification() {
    }

    public TravelNotification(String destiny, LocalDate checkout, Card card, String userAgent, String clientIp) {
        this.destiny = destiny;
        this.checkout = checkout;
        this.card = card;
        this.userAgent = userAgent;
        this.clientIp = clientIp;
    }

    public String getCardNumber() {
        return card.getNumber();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getDestiny() {
        return destiny;
    }

    public LocalDate getCheckout() {
        return checkout;
    }

    public TravelNotificationStatus getStatus() {
        return status;
    }

    public void setStatusToNotified() {
        this.status = NOTIFIED;
    }

    public UUID getCardIdentification() {
        return card.getUuid();
    }

    @Override
    public String toString() {
        return "TravelNotification{" +
                "checkout=" + checkout +
                ", status=" + status +
                '}';
    }
}
