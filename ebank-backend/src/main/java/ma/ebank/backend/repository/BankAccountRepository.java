package ma.ebank.backend.repository;

import ma.ebank.backend.model.Client;
import ma.ebank.backend.model.CompteBancaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<CompteBancaire, Long> {
    boolean existsByRib(String rib);
    List<CompteBancaire> findByClient(Client client);
    Optional<CompteBancaire> findByRib(String rib);

}
