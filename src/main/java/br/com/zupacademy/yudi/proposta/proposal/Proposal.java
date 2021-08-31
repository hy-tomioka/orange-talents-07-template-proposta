package br.com.zupacademy.yudi.proposta.proposal;

import br.com.zupacademy.yudi.proposta.card.Card;
import br.com.zupacademy.yudi.proposta.shared.validation.CpfOrCnpj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "proposals")
public class Proposal {

    private static final Logger LOG = LoggerFactory.getLogger(Proposal.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @CpfOrCnpj
    private String document;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @NotBlank
    private String address;

    @Column(nullable = false)
    @NotNull
    @Positive
    private BigDecimal salary;

    @Enumerated(STRING)
    private ProposalStatus status;

    @OneToOne
    @JoinColumn(unique = true, name = "card_id")
    private Card card;

    @Column(nullable = false)
    @NotNull
    private UUID uuid = UUID.randomUUID();

    @Deprecated
    private Proposal() {
    }

    public Proposal(String document, String email, String name, String address, BigDecimal salary) {
        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public String getDocument() {
        return document;
    }

    public String getName() {
        return name;
    }

    public ProposalStatus getStatus() {
        return status;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "Proposal{" +
                "id=" + id +
                ", document='" + document + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                ", status=" + status +
                '}';
    }

    public void setStatus(ProposalStatus status) {
        this.status = status;
        LOG.info("Analysis result = {} for document = {}", this.status, this.document);
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
