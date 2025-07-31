package com.eduMap.edumap.A_PRIMAIRE.repository;

import com.eduMap.edumap.A_PRIMAIRE.Entity.Scolarite;
import com.eduMap.edumap.A_PRIMAIRE.enums.ClassePRIMAIRE;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ScolariteRepository extends JpaRepository<Scolarite, Long> {
    Optional<Scolarite> findByClasse(ClassePRIMAIRE classe);

}
