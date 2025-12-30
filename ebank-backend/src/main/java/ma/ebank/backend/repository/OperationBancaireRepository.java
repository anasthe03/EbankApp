package ma.ebank.backend.repository;

import ma.ebank.backend.model.CompteBancaire;
import ma.ebank.backend.model.OperationBancaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationBancaireRepository extends JpaRepository<OperationBancaire, Long> {

    Page<OperationBancaire> findByCompteOrderByDateOperationDesc(
            CompteBancaire compte,
            Pageable pageable
    );
}
