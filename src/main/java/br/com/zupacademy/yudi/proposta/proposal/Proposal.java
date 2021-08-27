package br.com.zupacademy.yudi.proposta.proposal;

import br.com.zupacademy.yudi.proposta.shared.validation.CpfOrCnpj;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "proposals")
public class Proposal {

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

    @Override
    public String toString() {
        return "Proposal{" +
                "id=" + id +
                ", document='" + document + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                '}';
    }
}
