package skool.saas.skool.A_PRIMAIRE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skool.saas.skool.A_PRIMAIRE.Entity.Professeur;
import skool.saas.skool.A_PRIMAIRE.enums.ClassePRIMAIRE;

import java.util.Optional;

public interface ProfesseurRepository extends JpaRepository<Professeur, Long> {
    Optional<Professeur> findByClasse(ClassePRIMAIRE classe);

}
