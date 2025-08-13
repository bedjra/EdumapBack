package com.eduMap.edumap.A_PRIMAIRE.repository;

import com.eduMap.edumap.A_PRIMAIRE.Entity.Eleve;
import com.eduMap.edumap.A_PRIMAIRE.Entity.Matiere;
import com.eduMap.edumap.A_PRIMAIRE.Entity.Note;
import com.eduMap.edumap.A_PRIMAIRE.enums.ClassePRIMAIRE;
import com.eduMap.edumap.A_PRIMAIRE.enums.EvaluationPrimaire;
import com.eduMap.edumap.A_PRIMAIRE.enums.MatierePrimaire;
import com.eduMap.edumap.GLOBALE.Entity.AnneeScolaire;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
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

    List<Note> findByEleveAndAnneeScolaire(Eleve eleve, AnneeScolaire annee);

    List<Note> findByEleveAndAnneeScolaireAndEvaluationPrimaire(
            Eleve eleve, AnneeScolaire annee, EvaluationPrimaire evaluation
    );

}
