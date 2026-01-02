package ma.ebank.backend.service.impl;

import jakarta.transaction.Transactional;
import ma.ebank.backend.dto.CreateVirementRequest;
import ma.ebank.backend.model.CompteBancaire;
import ma.ebank.backend.model.OperationBancaire;
import ma.ebank.backend.model.enums.AccountStatus;
import ma.ebank.backend.model.enums.OperationType;
import ma.ebank.backend.repository.BankAccountRepository;
import ma.ebank.backend.repository.ClientRepository;
import ma.ebank.backend.repository.OperationBancaireRepository;
import ma.ebank.backend.service.VirementService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VirementServiceImpl implements VirementService {
    private final BankAccountRepository compteRepository;
    private final OperationBancaireRepository operationRepository;
    private final ClientRepository clientRepository;

    public VirementServiceImpl(BankAccountRepository compteRepository,
                               OperationBancaireRepository operationRepository,
                               ClientRepository clientRepository) {
        this.compteRepository = compteRepository;
        this.operationRepository = operationRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public void effectuerVirement(CreateVirementRequest request, String clientLogin) {

        // Compte source
        CompteBancaire compteSource = compteRepository.findById(request.getCompteSourceId())
                .orElseThrow(() -> new RuntimeException("Compte source introuvable"));

        // RG_11 : statut compte
        if (compteSource.getStatut() != AccountStatus.OUVERT) {
            throw new RuntimeException("Compte source non autorisé pour virement");
        }

        // RG_12 : solde suffisant
        if (compteSource.getSolde() < request.getMontant()) {
            throw new RuntimeException("Solde insuffisant pour ce virement");
        }

        // Compte destinataire
        CompteBancaire compteDest = compteRepository.findByRib(request.getRibDestinataire())
                .orElseThrow(() -> new RuntimeException("Compte destinataire introuvable"));

        // Débit compte source
        compteSource.setSolde(compteSource.getSolde() - request.getMontant());
        compteRepository.save(compteSource);

        // Crédit compte destinataire
        compteDest.setSolde(compteDest.getSolde() + request.getMontant());
        compteRepository.save(compteDest);

        LocalDateTime now = LocalDateTime.now();

        // Tracer opération débit
        OperationBancaire opDebit = new OperationBancaire();
        opDebit.setCompte(compteSource);
        opDebit.setType(OperationType.DEBIT);
        opDebit.setMontant(request.getMontant());
        opDebit.setLibelle("Virement vers " + compteDest.getRib() + " - " + request.getMotif());
        opDebit.setDateOperation(now);
        operationRepository.save(opDebit);

        // Tracer opération crédit
        OperationBancaire opCredit = new OperationBancaire();
        opCredit.setCompte(compteDest);
        opCredit.setType(OperationType.CREDIT);
        opCredit.setMontant(request.getMontant());
        opCredit.setLibelle("Virement reçu de " + compteSource.getRib() + " - " + request.getMotif());
        opCredit.setDateOperation(now);
        operationRepository.save(opCredit);
    }
}
