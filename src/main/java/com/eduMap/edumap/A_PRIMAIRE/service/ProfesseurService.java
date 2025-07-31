package skool.saas.skool.A_PRIMAIRE.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skool.saas.skool.A_PRIMAIRE.Entity.Professeur;
import skool.saas.skool.A_PRIMAIRE.repository.ProfesseurRepository;
import skool.saas.skool.A_PRIMAIRE.repository.TuteurRepository;

import java.util.List;

@Service
public class ProfesseurService {


    @Autowired
    private ProfesseurRepository professeurRepository;

    public Professeur saveProfesseur(Professeur prof) {
        return professeurRepository.save(prof);
    }

    public Professeur updateProfesseur(Long id, Professeur newData) {
        Professeur existing = professeurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Professeur introuvable"));

        existing.setNom(newData.getNom());
        existing.setPrenom(newData.getPrenom());
        existing.setAdresse(newData.getAdresse());
        existing.setClasse(newData.getClasse());
        existing.setDiplome(newData.getDiplome());
        existing.setTelephone(newData.getTelephone());

        return professeurRepository.save(existing);
    }

    public void deleteProfesseur(Long id) {
        if (!professeurRepository.existsById(id)) {
            throw new EntityNotFoundException("Professeur introuvable");
        }
        professeurRepository.deleteById(id);
    }

    public List<Professeur> getAllProfesseurs() {
        return professeurRepository.findAll();
    }
}
