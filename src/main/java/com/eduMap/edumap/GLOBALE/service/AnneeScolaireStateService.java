package com.eduMap.edumap.GLOBALE.service;

import com.eduMap.edumap.GLOBALE.Entity.AnneeScolaire;
import com.eduMap.edumap.GLOBALE.repository.AnneeScolaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class AnneeScolaireStateService {

    private final AtomicReference<AnneeScolaire> anneeActuelle = new AtomicReference<>();

    @Autowired
    private AnneeScolaireRepository anneeScolaireRepository;

    public void setAnneeActive(Long id) {
        AnneeScolaire annee = anneeScolaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Année non trouvée"));
        anneeActuelle.set(annee);
    }

    public AnneeScolaire getAnneeActive() {
        return anneeActuelle.get();
    }
}
