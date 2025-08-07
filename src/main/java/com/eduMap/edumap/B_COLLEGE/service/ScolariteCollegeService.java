package skool.saas.skool.B_COLLEGE.service;

import com.eduMap.edumap.B_COLLEGE.Entity.ScolariteCollege;
import com.eduMap.edumap.B_COLLEGE.enums.ClasseCOLLEGE;
import com.eduMap.edumap.B_COLLEGE.repository.ScolariteCollegeRepository;
import com.eduMap.edumap.GLOBALE.Entity.AnneeContext;
import com.eduMap.edumap.GLOBALE.Entity.AnneeScolaire;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ScolariteCollegeService {
    @Autowired
    private ScolariteCollegeRepository scolariteCollegeRepository;

    public ScolariteCollege saveScolarite(ScolariteCollege scolarite) {
        Optional<ScolariteCollege> existing = scolariteCollegeRepository.findByClasse(scolarite.getClasse());
        if (existing.isPresent()) {
            throw new IllegalStateException("Une scolarité existe déjà pour la classe : " + scolarite.getClasse());
        }

        AnneeScolaire anneeActive = AnneeContext.get();
        scolarite.setAnneeScolaire(anneeActive);

        return scolariteCollegeRepository.save(scolarite);
    }

    public ScolariteCollege updateScolarite(Long id, Long nouveauMontant) {
        ScolariteCollege scolarite = scolariteCollegeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Scolarité introuvable avec l'ID : " + id));
        scolarite.setMontant(nouveauMontant);
        return scolariteCollegeRepository.save(scolarite);
    }

    public List<ScolariteCollege> getAll() {
        return scolariteCollegeRepository.findAll();
    }

    public ScolariteCollege getByClasse(ClasseCOLLEGE classe) {
        return scolariteCollegeRepository.findByClasse(classe)
                .orElseThrow(() -> new EntityNotFoundException("Aucune scolarité trouvée pour la classe : " + classe));
    }
}
