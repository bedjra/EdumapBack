package com.eduMap.edumap.A_PRIMAIRE.service;

import com.eduMap.edumap.A_PRIMAIRE.Entity.Scolarite;
import com.eduMap.edumap.A_PRIMAIRE.enums.ClassePRIMAIRE;
import com.eduMap.edumap.A_PRIMAIRE.repository.ScolariteRepository;
import com.eduMap.edumap.GLOBALE.Entity.AnneeContext;
import com.eduMap.edumap.GLOBALE.Entity.AnneeScolaire;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ScolariteService {
    @Autowired
    private ScolariteRepository scolariteRepository;

    public Scolarite saveScolarite(Scolarite scolarite) {
        if (scolariteRepository.findByClasse(scolarite.getClasse()).isPresent()) {
            throw new IllegalStateException("Scolarité déjà définie pour cette classe");
        }
        // ✅ Affecter automatiquement l'année scolaire active
        AnneeScolaire anneeActive = AnneeContext.get();
        scolarite.setAnneeScolaire(anneeActive); // Assure-toi que ce champ existe dans ton entité Scolarite

        return scolariteRepository.save(scolarite);
    }

    public Scolarite updateScolarite(Long id, Long nouveauMontant) {
        Scolarite scolarite = scolariteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Scolarité introuvable"));

        scolarite.setMontant(nouveauMontant);
        return scolariteRepository.save(scolarite);
    }


    public List<Scolarite> getAllScolarites() {
        return scolariteRepository.findAll();
    }

    public Optional<Scolarite> getScolariteByClasse(ClassePRIMAIRE classe) {
        return scolariteRepository.findByClasse(classe);
    }
}
