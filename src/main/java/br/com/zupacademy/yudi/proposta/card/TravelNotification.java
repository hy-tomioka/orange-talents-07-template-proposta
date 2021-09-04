package br.com.zupacademy.yudi.proposta.card;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

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

    @Deprecated
    private TravelNotification() {
    }

    public TravelNotification(String destiny, LocalDate checkout, String userAgent, String clientIp) {
        this.destiny = destiny;
        this.checkout = checkout;
        this.userAgent = userAgent;
        this.clientIp = clientIp;
    }

    public UUID getUuid() {
        return uuid;
    }
}
