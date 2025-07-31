package skool.saas.skool.A_PRIMAIRE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skool.saas.skool.A_PRIMAIRE.Entity.Eleve;
import skool.saas.skool.A_PRIMAIRE.Entity.Matiere;
import skool.saas.skool.A_PRIMAIRE.Entity.Note;
import skool.saas.skool.A_PRIMAIRE.enums.ClassePRIMAIRE;
import skool.saas.skool.A_PRIMAIRE.enums.EvaluationPrimaire;
import skool.saas.skool.A_PRIMAIRE.enums.MatierePrimaire;
import skool.saas.skool.GLOBALE.Entity.AnneeScolaire;

import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

    Optional<Note> findByEleveAndClasseAndAnneeScolaireAndMatiereAndEvaluationPrimaire(
            Eleve eleve,
            ClassePRIMAIRE classe,
            AnneeScolaire anneeScolaire,
            Matiere matiere,
            EvaluationPrimaire evaluationPrimaire
    );

    Optional<Note> findByEleveAndClasseAndAnneeScolaireAndMatierePrimaireAndEvaluationPrimaire(
            Eleve eleve,
            ClassePRIMAIRE classe,
            AnneeScolaire anneeScolaire,
            MatierePrimaire matierePrimaire,
            EvaluationPrimaire evaluationPrimaire
    );

}
