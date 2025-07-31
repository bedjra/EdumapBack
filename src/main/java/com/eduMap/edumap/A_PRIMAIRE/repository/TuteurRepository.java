package skool.saas.skool.A_PRIMAIRE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skool.saas.skool.A_PRIMAIRE.Entity.Tuteur;

import java.util.Optional;

public interface TuteurRepository extends JpaRepository<Tuteur, Long> {

    Optional<Tuteur> findByNomIgnoreCaseAndPrenomIgnoreCase(String nom, String prenom);


}
