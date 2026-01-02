package ma.ebank.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateVirementRequest {
    @NotNull
    private Long compteSourceId;

    @NotBlank
    private String ribDestinataire;

    @Min(1)
    private double montant;

    private String motif;

    // getters & setters

    public Long getCompteSourceId() {
        return compteSourceId;
    }

    public String getRibDestinataire() {
        return ribDestinataire;
    }

    public double getMontant() {
        return montant;
    }

    public String getMotif() {
        return motif;
    }

    public void setCompteSourceId(Long compteSourceId) {
        this.compteSourceId = compteSourceId;
    }

    public void setRibDestinataire(String ribDestinataire) {
        this.ribDestinataire = ribDestinataire;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }
}
