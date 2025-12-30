package ma.ebank.backend.service.impl;

import ma.ebank.backend.dto.ClientDashboardResponse;
import ma.ebank.backend.model.Client;
import ma.ebank.backend.model.CompteBancaire;
import ma.ebank.backend.model.OperationBancaire;
import ma.ebank.backend.repository.BankAccountRepository;
import ma.ebank.backend.repository.ClientRepository;
import ma.ebank.backend.repository.OperationBancaireRepository;
import ma.ebank.backend.service.ClientDashboardService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientDashboardServiceImpl implements ClientDashboardService {
    private final ClientRepository clientRepository;
    private final BankAccountRepository compteRepository;
    private final OperationBancaireRepository operationRepository;

    public ClientDashboardServiceImpl(ClientRepository clientRepository,
                                      BankAccountRepository compteRepository,
                                      OperationBancaireRepository operationRepository) {
        this.clientRepository = clientRepository;
        this.compteRepository = compteRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    public ClientDashboardResponse getDashboard(
            String clientLogin,
            Long compteId,
            int page,
            int size
    ) {

        Client client = clientRepository
                .findByUserLogin(clientLogin)
                .orElseThrow(() -> new RuntimeException("Client introuvable"));

        List<CompteBancaire> comptes = compteRepository.findByClient(client);

        if (comptes.isEmpty()) {
            throw new RuntimeException("Aucun compte bancaire");
        }

        CompteBancaire compteActif;

        if (compteId != null) {
            compteActif = compteRepository.findById(compteId)
                    .orElseThrow(() -> new RuntimeException("Compte introuvable"));
        } else {
            compteActif = comptes.get(0);
        }

        List<OperationBancaire> operations =
                operationRepository
                        .findByCompteOrderByDateOperationDesc(
                                compteActif,
                                PageRequest.of(page, size)
                        )
                        .getContent();

        ClientDashboardResponse response = new ClientDashboardResponse();
        response.setComptes(comptes);
        response.setCompteActif(compteActif);
        response.setOperations(operations);

        return response;
    }
}
