package skool.saas.skool.A_PRIMAIRE.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skool.saas.skool.A_PRIMAIRE.Entity.Scolarite;
import skool.saas.skool.A_PRIMAIRE.enums.ClassePRIMAIRE;
import skool.saas.skool.A_PRIMAIRE.repository.ScolariteRepository;
import skool.saas.skool.GLOBALE.Entity.AnneeContext;
import skool.saas.skool.GLOBALE.Entity.AnneeScolaire;

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
