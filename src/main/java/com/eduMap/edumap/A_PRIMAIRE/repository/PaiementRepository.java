package com.eduMap.edumap.A_PRIMAIRE.repository;


import com.eduMap.edumap.A_PRIMAIRE.Dto.StatPaiementPrimaireDTO;
import com.eduMap.edumap.A_PRIMAIRE.Entity.Eleve;
import com.eduMap.edumap.A_PRIMAIRE.Entity.Paiement;
import com.eduMap.edumap.A_PRIMAIRE.enums.ClassePRIMAIRE;
import com.eduMap.edumap.GLOBALE.Entity.AnneeScolaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    @Query("SELECT SUM(p.montantActuel) FROM Paiement p WHERE p.eleve = :eleve AND p.anneeScolaire = :anneeScolaire")
    Double sumMontantActuelByEleveAndAnnee(@Param("eleve") Eleve eleve, @Param("anneeScolaire") AnneeScolaire anneeScolaire);
    Optional<Paiement> findTopByEleveOrderByIdDesc(Eleve eleve);
    List<Paiement> findByEleveOrderByDatePaiementDesc(Eleve eleve);
    List<Paiement> findByEleve_ClasseAndResteEcolageNot(ClassePRIMAIRE classe, long reste);

}
