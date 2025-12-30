package ma.ebank.backend.service;

import ma.ebank.backend.dto.CreateDepotRequest;

public interface DepotService {
    /**
     * Effectue un dépôt sur un compte bancaire
     * @param request Requête contenant compteId, montant et motif
     * @param clientLogin Login du client effectuant le dépôt
     */
    void effectuerDepot(CreateDepotRequest request, String clientLogin);
}
