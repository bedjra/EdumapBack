package com.eduMap.edumap.A_PRIMAIRE.repository;

import com.eduMap.edumap.A_PRIMAIRE.Entity.Tuteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TuteurRepository extends JpaRepository<Tuteur, Long> {

    Optional<Tuteur> findByNomIgnoreCaseAndPrenomIgnoreCase(String nom, String prenom);


}
