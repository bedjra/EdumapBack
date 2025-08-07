package skool.saas.skool.B_COLLEGE.service;

import com.eduMap.edumap.B_COLLEGE.Dto.CollegeStatistiqueDto;
import com.eduMap.edumap.B_COLLEGE.Dto.EleveCollegeDto;
import com.eduMap.edumap.B_COLLEGE.Entity.EleveCollege;
import com.eduMap.edumap.B_COLLEGE.Entity.TuteurCollege;
import com.eduMap.edumap.B_COLLEGE.enums.ClasseCOLLEGE;
import com.eduMap.edumap.B_COLLEGE.repository.EleveCollegeRepository;
import com.eduMap.edumap.B_COLLEGE.repository.TuteurCollegeRepository;
import com.eduMap.edumap.GLOBALE.Entity.AnneeContext;
import com.eduMap.edumap.GLOBALE.Entity.AnneeScolaire;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EleveCollegeService {


    @Autowired
    private TuteurCollegeRepository tuteurCollegeRepository;

    @Autowired
    private EleveCollegeRepository eleveCollegeRepository;


    @Transactional
    public EleveCollege ajouterEleveEtTuteur(EleveCollegeDto dto) {

        // 1. Vérifier si un tuteur avec ce nom et prénom existe déjà
        Optional<TuteurCollege> optionalTuteur = tuteurCollegeRepository
                .findByNomIgnoreCaseAndPrenomIgnoreCase(dto.getTuteurNom(), dto.getTuteurPrenom());

        TuteurCollege tuteur;

        if (optionalTuteur.isPresent()) {
            // Tuteur déjà existant
            tuteur = optionalTuteur.get();
        } else {
            // Nouveau tuteur
            tuteur = new TuteurCollege();
            tuteur.setNom(dto.getTuteurNom());
            tuteur.setPrenom(dto.getTuteurPrenom());
            tuteur.setTelephone(dto.getTuteurTelephone());
            tuteur.setProfession(dto.getTuteurProfession());
            tuteur = tuteurCollegeRepository.save(tuteur);
        }

        // Création de l’élève
        EleveCollege eleve = new EleveCollege();
        eleve.setNom(dto.getNom());
        eleve.setPrenom(dto.getPrenom());
        eleve.setAdresse(dto.getAdresse());
        eleve.setClasse(dto.getClasse());
        eleve.setSexe(dto.getSexe());
        eleve.setLieuNais(dto.getLieuNais());
        eleve.setEtblProv(dto.getEtblProv());
        eleve.setNationnalite(dto.getNationnalite());
        eleve.setDateNaiss(dto.getDateNaiss());
        eleve.setPhoto(dto.getPhoto());

        eleve.setTuteurCollege(tuteur);

        // ✅ Affectation automatique de l’année scolaire active
        AnneeScolaire anneeActive = AnneeContext.get();
        eleve.setAnneeScolaire(anneeActive);
        return eleveCollegeRepository.save(eleve);
    }

    @Transactional
    public EleveCollege modifierEleveEtTuteur(Long eleveId, EleveCollegeDto dto) {
        EleveCollege eleve = eleveCollegeRepository.findById(eleveId)
                .orElseThrow(() -> new RuntimeException("Élève non trouvé"));

        // Mise à jour des informations de l’élève
        eleve.setNom(dto.getNom());
        eleve.setPrenom(dto.getPrenom());
        eleve.setAdresse(dto.getAdresse());
        eleve.setClasse(dto.getClasse());
        eleve.setSexe(dto.getSexe());
        eleve.setLieuNais(dto.getLieuNais());
        eleve.setEtblProv(dto.getEtblProv());
        eleve.setNationnalite(dto.getNationnalite());
        eleve.setDateNaiss(dto.getDateNaiss());
        eleve.setPhoto(dto.getPhoto());

        // Mise à jour ou récupération du tuteur
        Optional<TuteurCollege> optionalTuteur = tuteurCollegeRepository
                .findByNomIgnoreCaseAndPrenomIgnoreCase(dto.getTuteurNom(), dto.getTuteurPrenom());

        TuteurCollege tuteur;

        if (optionalTuteur.isPresent()) {
            tuteur = optionalTuteur.get();
        } else {
            tuteur = new TuteurCollege();
            tuteur.setNom(dto.getTuteurNom());
            tuteur.setPrenom(dto.getTuteurPrenom());
            tuteur.setTelephone(dto.getTuteurTelephone());
            tuteur.setProfession(dto.getTuteurProfession());
            tuteur = tuteurCollegeRepository.save(tuteur);
        }

        eleve.setTuteurCollege(tuteur);

        return eleveCollegeRepository.save(eleve);
    }



    @Transactional
    public void supprimerEleve(Long eleveId) {
        EleveCollege eleve = eleveCollegeRepository.findById(eleveId)
                .orElseThrow(() -> new RuntimeException("Élève non trouvé"));

        eleveCollegeRepository.delete(eleve);
    }


    public Optional<EleveCollege> getEleveByNomAndPrenom(String nom, String prenom) {
        return eleveCollegeRepository.findByNomIgnoreCaseAndPrenomIgnoreCase(nom, prenom);
    }


    public List<CollegeStatistiqueDto> getStatistiquesParClasseCollege() {
        List<CollegeStatistiqueDto> stats = new ArrayList<>();

        for (ClasseCOLLEGE classeEnum : ClasseCOLLEGE.values()) {
            String classe = classeEnum.name();
            long total = eleveCollegeRepository.countByClasse(ClasseCOLLEGE.valueOf(classe));
            long garcons = eleveCollegeRepository.countByClasseAndSexeIgnoreCase(ClasseCOLLEGE.valueOf(classe), "Masculin");
            long filles = eleveCollegeRepository.countByClasseAndSexeIgnoreCase(ClasseCOLLEGE.valueOf(classe), "Feminin");

            stats.add(new CollegeStatistiqueDto(classe, total, garcons, filles));
        }

        return stats;
    }

    public List<EleveCollege> getElevesByClasseCollege(ClasseCOLLEGE classe) {
        return eleveCollegeRepository.findByClasse(ClasseCOLLEGE.valueOf(classe.name()));
    }

    public EleveCollegeDto getEleveDtoById(Long id) {
        EleveCollege eleve = eleveCollegeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Élève non trouvé avec l'ID : " + id));

        return mapToDto(eleve);
    }


    private EleveCollegeDto mapToDto(EleveCollege eleve) {
        EleveCollegeDto dto = new EleveCollegeDto();

        dto.setNom(eleve.getNom());
        dto.setPrenom(eleve.getPrenom());
        dto.setAdresse(eleve.getAdresse());
        dto.setClasse(eleve.getClasse());
        dto.setSexe(eleve.getSexe());
        dto.setLieuNais(eleve.getLieuNais());
        dto.setEtblProv(eleve.getEtblProv());
        dto.setNationnalite(eleve.getNationnalite());
        dto.setDateNaiss(eleve.getDateNaiss());

        if (eleve.getTuteurCollege() != null) {
            dto.setTuteurNom(eleve.getTuteurCollege().getNom());
            dto.setTuteurPrenom(eleve.getTuteurCollege().getPrenom());
            dto.setTuteurTelephone(eleve.getTuteurCollege().getTelephone());
            dto.setTuteurProfession(eleve.getTuteurCollege().getProfession());
        }

        return dto;
    }



}
