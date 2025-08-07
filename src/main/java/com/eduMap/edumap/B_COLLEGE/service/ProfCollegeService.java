package com.eduMap.edumap.B_COLLEGE.service;

import com.eduMap.edumap.B_COLLEGE.Entity.MatiereCollegeEntity;
import com.eduMap.edumap.B_COLLEGE.Entity.ProfCollege;
import com.eduMap.edumap.B_COLLEGE.repository.MatiereCollegeRepository;
import com.eduMap.edumap.B_COLLEGE.repository.ProfCollegeRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfCollegeService {
    @Autowired
    private ProfCollegeRep profCollegeRep;

    @Autowired
    private MatiereCollegeRepository matiereCollegeRepository;

    public ProfCollege enregistrerProf(ProfCollege prof, Long matiereId) {
        if (matiereId != null) {
            Optional<MatiereCollegeEntity> matiere = matiereCollegeRepository.findById(matiereId);
            matiere.ifPresent(prof::setMatiereCollegeEntity);
        }
        return profCollegeRep.save(prof);
    }

    public List<ProfCollege> getAllProfs() {
        return profCollegeRep.findAll();
    }

    public Optional<ProfCollege> getProfById(Long id) {
        return profCollegeRep.findById(id);
    }

    public void supprimerProf(Long id) {
        profCollegeRep.deleteById(id);
    }

    public Optional<ProfCollege> mettreAJourProf(Long id, ProfCollege nouveauProf, Long matiereId) {
        return profCollegeRep.findById(id).map(existing -> {
            existing.setNom(nouveauProf.getNom());
            existing.setPrenom(nouveauProf.getPrenom());
            existing.setAdresse(nouveauProf.getAdresse());
            existing.setDiplome(nouveauProf.getDiplome());
            existing.setTelephone(nouveauProf.getTelephone());
            existing.setTitulaire(nouveauProf.getTitulaire());

            if (matiereId != null) {
                matiereCollegeRepository.findById(matiereId)
                        .ifPresent(existing::setMatiereCollegeEntity);
            }

            return profCollegeRep.save(existing);
        });
    }
}
