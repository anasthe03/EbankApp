package ma.ebank.backend.model;

import ma.ebank.backend.model.enums.AccountStatus;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "comptes")
public class CompteBancaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String rib;

    private double solde;

    @Enumerated(EnumType.STRING)
    private AccountStatus statut;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "compte", cascade = CascadeType.ALL)
    private List<OperationBancaire> operations;

    // getters & setters
}
