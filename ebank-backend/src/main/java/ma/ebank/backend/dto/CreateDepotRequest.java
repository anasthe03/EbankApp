package ma.ebank.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreateDepotRequest {
    @NotNull(message = "L'ID du compte est obligatoire")
    private Long compteId;

    @NotNull(message = "Le montant est obligatoire")
    @Min(value = 1, message = "Le montant doit être supérieur à 0")
    private Double montant;

    private String motif; // Optionnel : "Dépôt initial", "Alimentation compte", etc.

    //getters et setters

    public Long getCompteId() {
        return compteId;
    }

    public Double getMontant() {
        return montant;
    }

    public String getMotif() {
        return motif;
    }

    public void setCompteId(Long compteId) {
        this.compteId = compteId;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }
}
