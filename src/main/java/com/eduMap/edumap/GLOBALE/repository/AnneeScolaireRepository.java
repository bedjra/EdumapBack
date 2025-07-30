package com.eduMap.edumap.GLOBALE.repository;

import com.eduMap.edumap.GLOBALE.Entity.AnneeScolaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnneeScolaireRepository extends JpaRepository<AnneeScolaire, Long> {
    Optional<AnneeScolaire> findByLibelle(String libelle);
    Optional<AnneeScolaire> findByActiveTrue();
}
