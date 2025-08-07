package com.eduMap.edumap.B_COLLEGE.service;

import com.eduMap.edumap.B_COLLEGE.Entity.MatiereCollegeEntity;
import com.eduMap.edumap.B_COLLEGE.enums.MatiereCollege;
import com.eduMap.edumap.B_COLLEGE.repository.MatiereCollegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MatiereCollegeService {

    @Autowired
    private MatiereCollegeRepository matiereCollegeRepository;

    // Récupérer les matières à partir de l'enum
    public List<String> getMatieresFromEnum() {
        return Arrays.stream(MatiereCollege.values())
                .map(MatiereCollege::getLabel)
                .collect(Collectors.toList());
    }

    // Récupérer les matières depuis la base de données
    public List<MatiereCollegeEntity> getMatieresFromDB() {
        return matiereCollegeRepository.findAll();
    }

    // Combiner les deux sources
    public Map<String, Object> getAllMatieres() {
        Map<String, Object> result = new HashMap<>();
        result.put("enumMatieres", getMatieresFromEnum());
        result.put("dbMatieres", getMatieresFromDB());
        return result;
    }

    // Ajouter une matière
    public MatiereCollegeEntity createMatiere(MatiereCollegeEntity matiere) {
        return matiereCollegeRepository.save(matiere);
    }

    // Modifier une matière
    public MatiereCollegeEntity updateMatiere(Long id, MatiereCollegeEntity matiereDetails) {
        MatiereCollegeEntity matiere = matiereCollegeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matière introuvable avec ID " + id));
        matiere.setNom(matiereDetails.getNom());
        return matiereCollegeRepository.save(matiere);
    }

    // Supprimer une matière
    public void deleteMatiere(Long id) {
        if (!matiereCollegeRepository.existsById(id)) {
            throw new RuntimeException("Matière introuvable avec ID " + id);
        }
        matiereCollegeRepository.deleteById(id);
    }

}
