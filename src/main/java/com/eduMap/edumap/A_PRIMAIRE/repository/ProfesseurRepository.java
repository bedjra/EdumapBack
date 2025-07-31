package com.eduMap.edumap.A_PRIMAIRE.repository;

import com.eduMap.edumap.A_PRIMAIRE.Entity.Professeur;
import com.eduMap.edumap.A_PRIMAIRE.enums.ClassePRIMAIRE;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfesseurRepository extends JpaRepository<Professeur, Long> {
    Optional<Professeur> findByClasse(ClassePRIMAIRE classe);

}
