package skool.saas.skool.A_PRIMAIRE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skool.saas.skool.A_PRIMAIRE.Entity.Scolarite;
import skool.saas.skool.A_PRIMAIRE.enums.ClassePRIMAIRE;

import java.util.Optional;

public interface ScolariteRepository extends JpaRepository<Scolarite, Long> {
    Optional<Scolarite> findByClasse(ClassePRIMAIRE classe);

}
