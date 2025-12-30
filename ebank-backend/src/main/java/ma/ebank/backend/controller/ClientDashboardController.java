package ma.ebank.backend.controller;

import ma.ebank.backend.dto.ClientDashboardResponse;
import ma.ebank.backend.service.ClientDashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client/dashboard")
public class ClientDashboardController {
    private final ClientDashboardService dashboardService;

    public ClientDashboardController(ClientDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ClientDashboardResponse getDashboard(
            Authentication authentication,
            @RequestParam(required = false) Long compteId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        String login = authentication.getName();

        return dashboardService.getDashboard(
                login,
                compteId,
                page,
                size
        );
    }
}
