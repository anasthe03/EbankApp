package ma.ebank.backend.model;

import ma.ebank.backend.model.enums.OperationType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "operations")
public class OperationBancaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OperationType type;

    private double montant;

    private LocalDateTime dateOperation;

    private String libelle;

    @ManyToOne
    @JoinColumn(name = "compte_id")
    private CompteBancaire compte;

    // getters & setters

    public Long getId() {
        return id;
    }

    public OperationType getType() {
        return type;
    }

    public double getMontant() {
        return montant;
    }

    public LocalDateTime getDateOperation() {
        return dateOperation;
    }

    public String getLibelle() {
        return libelle;
    }

    public CompteBancaire getCompte() {
        return compte;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setDateOperation(LocalDateTime dateOperation) {
        this.dateOperation = dateOperation;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setCompte(CompteBancaire compte) {
        this.compte = compte;
    }
}
