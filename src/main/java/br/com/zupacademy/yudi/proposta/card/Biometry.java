package br.com.zupacademy.yudi.proposta.card;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "biometries")
public class Biometry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private UUID uuid = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Deprecated
    private Biometry() {
    }

    public Biometry(String biometryId, Card card) {
        this.code = biometryId;
        this.card = card;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Biometry biometry = (Biometry) o;
        return Objects.equals(code, biometry.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
