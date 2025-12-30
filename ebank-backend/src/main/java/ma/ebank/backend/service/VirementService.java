package ma.ebank.backend.service;

import ma.ebank.backend.dto.CreateVirementRequest;

public interface VirementService {
    void effectuerVirement(CreateVirementRequest request, String clientLogin);
}
