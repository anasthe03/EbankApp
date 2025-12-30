package ma.ebank.backend.service.impl;

import jakarta.transaction.Transactional;
import ma.ebank.backend.dto.CreateDepotRequest;
import ma.ebank.backend.model.CompteBancaire;
import ma.ebank.backend.model.OperationBancaire;
import ma.ebank.backend.model.enums.AccountStatus;
import ma.ebank.backend.model.enums.OperationType;
import ma.ebank.backend.repository.BankAccountRepository;
import ma.ebank.backend.repository.OperationBancaireRepository;
import ma.ebank.backend.service.DepotService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DepotServiceImpl implements DepotService {
    private final BankAccountRepository compteRepository;
    private final OperationBancaireRepository operationRepository;

    public DepotServiceImpl(BankAccountRepository compteRepository,
                            OperationBancaireRepository operationRepository) {
        this.compteRepository = compteRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    @Transactional
    public void effectuerDepot(CreateDepotRequest request, String clientLogin) {

        // 1. Récupérer le compte
        CompteBancaire compte = compteRepository.findById(request.getCompteId())
                .orElseThrow(() -> new RuntimeException("Compte bancaire introuvable"));

        // 2. Vérifier que le compte est ouvert
        if (compte.getStatut() != AccountStatus.OUVERT) {
            throw new RuntimeException("Le compte n'est pas ouvert, dépôt impossible");
        }

        // 3. Vérifier que le montant est positif (normalement déjà validé par @Min)
        if (request.getMontant() <= 0) {
            throw new RuntimeException("Le montant du dépôt doit être supérieur à 0");
        }

        // 4. Créditer le compte
        double nouveauSolde = compte.getSolde() + request.getMontant();
        compte.setSolde(nouveauSolde);
        compteRepository.save(compte);

        // 5. Tracer l'opération de dépôt
        OperationBancaire operation = new OperationBancaire();
        operation.setCompte(compte);
        operation.setType(OperationType.CREDIT);
        operation.setMontant(request.getMontant());

        // Définir un libellé clair
        String libelle = request.getMotif() != null && !request.getMotif().isEmpty()
                ? "Dépôt - " + request.getMotif()
                : "Dépôt espèces";
        operation.setLibelle(libelle);
        operation.setDateOperation(LocalDateTime.now());

        operationRepository.save(operation);
    }
}
