package skool.saas.skool.A_PRIMAIRE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skool.saas.skool.A_PRIMAIRE.Entity.Matiere;
import skool.saas.skool.A_PRIMAIRE.enums.MatierePrimaire;
import skool.saas.skool.A_PRIMAIRE.repository.MatiereRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MatiereService {

    @Autowired
    private MatiereRepository matiereRepository;

    public List<String> getMatieresFromEnum() {
        return Arrays.stream(MatierePrimaire.values())
                .map(MatierePrimaire::getLabel)
                .toList();
    }

    public List<Matiere> getMatieresFromDB() {
        return matiereRepository.findAll();
    }

    public Map<String, Object> getAllMatieres() {
        Map<String, Object> result = new HashMap<>();
        result.put("enumMatieres", getMatieresFromEnum());
        result.put("dbMatieres", getMatieresFromDB());
        return result;
    }


    // Ajouter une matière
    public Matiere createMatiere(Matiere matiere) {
        return matiereRepository.save(matiere);
    }

    // Modifier une matière
    public Matiere updateMatiere(Long id, Matiere matiereDetails) {
        Matiere matiere = matiereRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matière introuvable avec ID " + id));
        matiere.setNom(matiereDetails.getNom());
        return matiereRepository.save(matiere);
    }

    // Supprimer une matière
    public void deleteMatiere(Long id) {
        if (!matiereRepository.existsById(id)) {
            throw new RuntimeException("Matière introuvable avec ID " + id);
        }
        matiereRepository.deleteById(id);
    }
}
