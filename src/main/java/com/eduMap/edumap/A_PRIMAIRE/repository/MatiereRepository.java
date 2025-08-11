package com.eduMap.edumap.A_PRIMAIRE.repository;

import com.eduMap.edumap.A_PRIMAIRE.Entity.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatiereRepository extends JpaRepository<Matiere, Long> {
    Optional<Matiere> findByNomIgnoreCase(String nom);

}
