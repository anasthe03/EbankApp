package ma.ebank.backend.dto;

import ma.ebank.backend.model.CompteBancaire;
import ma.ebank.backend.model.OperationBancaire;

import java.util.List;

public class ClientDashboardResponse {
    private List<CompteBancaire> comptes;
    private CompteBancaire compteActif;
    private List<OperationBancaire> operations;

    // getters & setters

    public List<CompteBancaire> getComptes() {
        return comptes;
    }

    public void setComptes(List<CompteBancaire> comptes) {
        this.comptes = comptes;
    }

    public CompteBancaire getCompteActif() {
        return compteActif;
    }

    public void setCompteActif(CompteBancaire compteActif) {
        this.compteActif = compteActif;
    }

    public List<OperationBancaire> getOperations() {
        return operations;
    }

    public void setOperations(List<OperationBancaire> operations) {
        this.operations = operations;
    }
}
