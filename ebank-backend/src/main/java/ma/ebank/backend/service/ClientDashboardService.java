package ma.ebank.backend.service;

import ma.ebank.backend.dto.ClientDashboardResponse;

public interface ClientDashboardService {
    ClientDashboardResponse getDashboard(
            String clientLogin,
            Long compteId,
            int page,
            int size
    );
}
