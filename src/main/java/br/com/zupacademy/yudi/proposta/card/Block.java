package br.com.zupacademy.yudi.proposta.card;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "blocks")
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime blockedAt;

    private String client;

    private String userAgent;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Deprecated
    private Block() {
    }

    public Block(String client, String userAgent, Card card) {
        this.client = client;
        this.userAgent = userAgent;
        this.card = card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return Objects.equals(blockedAt, block.blockedAt) && Objects.equals(client, block.client) && Objects.equals(userAgent, block.userAgent) && Objects.equals(card, block.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockedAt, client, userAgent, card);
    }
}
