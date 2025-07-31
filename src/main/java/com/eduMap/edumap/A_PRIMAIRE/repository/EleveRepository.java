package skool.saas.skool.A_PRIMAIRE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skool.saas.skool.A_PRIMAIRE.Entity.Eleve;
import skool.saas.skool.A_PRIMAIRE.enums.ClassePRIMAIRE;

import java.util.List;
import java.util.Optional;

public interface EleveRepository extends JpaRepository<Eleve, Long> {
    List<Eleve> findByClasse(ClassePRIMAIRE classe);
    Optional<Eleve> findByNomIgnoreCaseAndPrenomIgnoreCase(String nom, String prenom);
    long countByClasseAndSexeIgnoreCase(ClassePRIMAIRE classe, String sexe);
    long countByClasse(ClassePRIMAIRE classe);
    Optional<Eleve> findById(Long id);
    Optional<Eleve> findByNomAndPrenom(String nom, String prenom);
    Optional<Eleve> findByNomAndPrenomAndClasse(String nom, String prenom, ClassePRIMAIRE classe);

}
