package com.eduMap.edumap.GLOBALE.service;

import com.eduMap.edumap.GLOBALE.Entity.AnneeScolaire;
import com.eduMap.edumap.GLOBALE.repository.AnneeScolaireRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AnneeScolaireService {

      @Autowired
    private AnneeScolaireRepository anneeScolaireRepository;

    // Appelée automatiquement au démarrage
    @PostConstruct
    public void initialiserOuActiverAnneeScolaire() {
        String libelleActuel = genererLibelleAnneeScolaire();

        Optional<AnneeScolaire> existante = anneeScolaireRepository.findByLibelle(libelleActuel);

        // Si elle existe, on s'assure qu'elle est active
        if (existante.isPresent()) {
            desactiverToutes();
            AnneeScolaire annee = existante.get();
            annee.setActive(true);
            anneeScolaireRepository.save(annee);
        } else {
            // Sinon on la crée
            desactiverToutes();
            AnneeScolaire nouvelle = new AnneeScolaire();
            nouvelle.setLibelle(libelleActuel);
            nouvelle.setActive(true);
            anneeScolaireRepository.save(nouvelle);
        }
    }

    // Calcule le libellé selon la date actuelle
    public String genererLibelleAnneeScolaire() {
        LocalDate now = LocalDate.now();
        int startYear = (now.getMonthValue() >= 9) ? now.getYear() : now.getYear() - 1;
        int endYear = startYear + 1;
        return startYear + "-" + endYear;
    }

    private void desactiverToutes() {
        anneeScolaireRepository.findAll().forEach(annee -> {
            if (annee.isActive()) {
                annee.setActive(false);
                anneeScolaireRepository.save(annee);
            }
        });
    }

    public AnneeScolaire getAnneeActive() {
        return anneeScolaireRepository.findByActiveTrue().orElse(null);
    }

    public List<AnneeScolaire> getAll() {
        return anneeScolaireRepository.findAll(Sort.by(Sort.Direction.DESC, "libelle"));
    }

}
