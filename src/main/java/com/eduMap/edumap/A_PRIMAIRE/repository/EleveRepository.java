package com.eduMap.edumap.A_PRIMAIRE.repository;

import com.eduMap.edumap.A_PRIMAIRE.Entity.Eleve;
import com.eduMap.edumap.A_PRIMAIRE.enums.ClassePRIMAIRE;
import org.springframework.data.jpa.repository.JpaRepository;

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
    Eleve findByMatricule(String matricule);
    long count(); // Hérité, mais tu peux aussi l'écrire explicitement

}
