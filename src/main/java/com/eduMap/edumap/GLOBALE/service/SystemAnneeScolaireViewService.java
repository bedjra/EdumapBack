package com.eduMap.edumap.GLOBALE.service;

import com.eduMap.edumap.GLOBALE.Dto.SystemAnneeResponse;
import com.eduMap.edumap.GLOBALE.Entity.AnneeScolaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemAnneeScolaireViewService {

    @Autowired
    private AnneeScolaireStateService anneeScolaireStateService;

    @Autowired
    private SystemStateService systemStateService;

    public SystemAnneeResponse getSystemeEtAnnee() {
        AnneeScolaire annee = anneeScolaireStateService.getAnneeActive();
        System systeme = systemStateService.getSystem();

        String anneeLibelle = (annee != null) ? annee.getLibelle() : null;

        return new SystemAnneeResponse(systeme, anneeLibelle);
    }
}
