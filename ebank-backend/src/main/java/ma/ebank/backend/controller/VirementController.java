package ma.ebank.backend.controller;

import jakarta.validation.Valid;
import ma.ebank.backend.dto.CreateVirementRequest;
import ma.ebank.backend.service.VirementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client/virements")
public class VirementController {
    private final VirementService virementService;

    public VirementController(VirementService virementService) {
        this.virementService = virementService;
    }

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> effectuerVirement(
            @Valid @RequestBody CreateVirementRequest request,
            Authentication authentication
    ) {
        String clientLogin = authentication.getName();

        virementService.effectuerVirement(request, clientLogin);

        return new ResponseEntity<>("Virement effectué avec succès", HttpStatus.OK);
    }
}
