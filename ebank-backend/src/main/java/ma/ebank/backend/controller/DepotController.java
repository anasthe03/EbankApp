package ma.ebank.backend.controller;

import jakarta.validation.Valid;
import ma.ebank.backend.dto.CreateDepotRequest;
import ma.ebank.backend.service.DepotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/depot")
@CrossOrigin(origins = "*") // À adapter selon vos besoins de sécurité
public class DepotController {
    private final DepotService depotService;

    public DepotController(DepotService depotService) {
        this.depotService = depotService;
    }

    /**
     * Endpoint pour effectuer un dépôt
     * POST /api/depot
     * Body: { "compteId": 1, "montant": 1000.0, "motif": "Dépôt initial" }
     */
    @PostMapping
    public ResponseEntity<?> effectuerDepot(
            @Valid @RequestBody CreateDepotRequest request,
            Authentication authentication) {

        try {
            // Récupérer le login du client authentifié
            String clientLogin = authentication.getName();

            // Effectuer le dépôt
            depotService.effectuerDepot(request, clientLogin);

            // Réponse de succès
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Dépôt effectué avec succès");
            response.put("montant", request.getMontant());
            response.put("compteId", request.getCompteId());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            // Gestion des erreurs métier
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse);

        } catch (Exception e) {
            // Erreur technique inattendue
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Erreur technique lors du dépôt");

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }
}
