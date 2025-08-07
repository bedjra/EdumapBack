//package skool.saas.skool.B_COLLEGE.service;
//
//import com.eduMap.edumap.B_COLLEGE.Dto.PaiementCollegeDto;
//import com.eduMap.edumap.B_COLLEGE.Dto.PaiementCollegeHistoriqueDto;
//import com.eduMap.edumap.B_COLLEGE.Dto.PaiementCollegeRequestDto;
//import com.eduMap.edumap.B_COLLEGE.Entity.EleveCollege;
//import com.eduMap.edumap.B_COLLEGE.Entity.PaiementCollege;
//import com.eduMap.edumap.B_COLLEGE.Entity.ScolariteCollege;
//import com.eduMap.edumap.B_COLLEGE.enums.ClasseCOLLEGE;
//import com.eduMap.edumap.B_COLLEGE.enums.StttScoCollg;
//import com.eduMap.edumap.B_COLLEGE.repository.EleveCollegeRepository;
//import com.eduMap.edumap.B_COLLEGE.repository.PaiementCollegeRepository;
//import com.eduMap.edumap.B_COLLEGE.repository.ScolariteCollegeRepository;
//import com.eduMap.edumap.GLOBALE.Entity.AnneeContext;
//import com.eduMap.edumap.GLOBALE.Entity.AnneeScolaire;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class PaiementCollegeService {
//
//    @Autowired
//    private PaiementCollegeRepository paiementRepository;
//
//    @Autowired
//    private ScolariteCollegeRepository scolariteRepository;
//
//    @Autowired
//    private EleveCollegeRepository eleveRepository;
//
//    public PaiementCollegeDto enregistrerPaiement(PaiementCollegeRequestDto dto) {
//        EleveCollege eleve = eleveRepository.findByNomAndPrenom(dto.getEleveNom(), dto.getElevePrenom())
//                .orElseThrow(() -> new RuntimeException("Élève introuvable"));
//
//        ScolariteCollege scolarite = scolariteRepository.findByClasseCOLLEGE(eleve.getClasseCOLLEGE())
//                .orElseThrow(() -> new RuntimeException("Montant scolarité introuvable"));
//
//        AnneeScolaire anneeActive = AnneeContext.get();
//
//        double montantScolarite = scolarite.getMontant().doubleValue();
//        Double dejaPaye = paiementRepository.sumMontantActuelByEleveAndAnnee(eleve, anneeActive);
//        if (dejaPaye == null) dejaPaye = 0.0;
//
//        double montantActuel = dto.getMontantActuel();
//        double total = dejaPaye + montantActuel;
//
//        if (total > montantScolarite) {
//            throw new IllegalArgumentException("Le montant payé dépasse le reste à payer");
//        }
//
//        PaiementCollege paiement = new PaiementCollege();
//        paiement.setEleveCollege(eleve);
//        paiement.setDatePaiement(dto.getDatePaiement());
//        paiement.setMontantActuel((long) montantActuel);
//        paiement.setMontantDejaPaye((long) total);
//        paiement.setResteEcolage((long) (montantScolarite - total));
//        paiement.setStttScoCollg(total == montantScolarite ? StttScoCollg.SOLDÉ : StttScoCollg.EN_COURS);
//        paiement.setScolariteCollege(scolarite);
//        paiement.setClasseCOLLEGE(eleve.getClasseCOLLEGE());
//        paiement.setAnneeScolaire(anneeActive);
//
//        paiementRepository.save(paiement);
//
//        PaiementCollegeDto response = new PaiementCollegeDto();
//        response.setEleveId(eleve.getId());
//        response.setEleveNom(eleve.getNom());
//        response.setElevePrenom(eleve.getPrenom());
//        response.setClasseCOLLEGE(eleve.getClasseCOLLEGE());
//        response.setDatePaiement(paiement.getDatePaiement());
//        response.setMontantActuel(paiement.getMontantActuel());
//        response.setMontantDejaPaye(paiement.getMontantDejaPaye());
//        response.setResteEcolage(paiement.getResteEcolage());
//        response.setStttScoCollg(paiement.getStttScoCollg());
//        response.setMontantScolarite(montantScolarite);
//
//        return response;
//    }
//
//    public PaiementCollegeDto getPaiementParEleveId(Long eleveId) {
//        EleveCollege eleve = eleveRepository.findById(eleveId)
//                .orElseThrow(() -> new RuntimeException("Élève non trouvé avec l'ID : " + eleveId));
//
//        ClasseCOLLEGE classe = eleve.getClasseCOLLEGE();
//        Optional<ScolariteCollege> scolariteOpt = scolariteRepository.findByClasseCOLLEGE(classe);
//        double montantScolarite = scolariteOpt.map(s -> s.getMontant().doubleValue()).orElse(0.0);
//
//        PaiementCollegeDto dto = new PaiementCollegeDto();
//        dto.setEleveId(eleve.getId());
//        dto.setEleveNom(eleve.getNom());
//        dto.setElevePrenom(eleve.getPrenom());
//        dto.setClasseCOLLEGE(classe);
//        dto.setMontantScolarite(montantScolarite);
//
//        PaiementCollege dernierPaiement = paiementRepository.findTopByEleveCollegeOrderByIdDesc(eleve).orElse(null);
//
//        if (dernierPaiement != null) {
//            dto.setDatePaiement(dernierPaiement.getDatePaiement());
//            dto.setMontantActuel(dernierPaiement.getMontantActuel());
//            dto.setMontantDejaPaye(dernierPaiement.getMontantDejaPaye());
//            dto.setResteEcolage(dernierPaiement.getResteEcolage());
//            dto.setStttScoCollg(dernierPaiement.getStttScoCollg());
//        } else {
//            dto.setMontantActuel(0);
//            dto.setMontantDejaPaye(0);
//            dto.setResteEcolage((long) montantScolarite);
//            dto.setStttScoCollg(StttScoCollg.EN_COURS);
//            dto.setDatePaiement(null);
//        }
//
//        return dto;
//    }
//
//    public List<PaiementCollegeDto> getPaiementsParClasse(ClasseCOLLEGE classe) {
//        List<EleveCollege> eleves = eleveRepository.findByClasseCOLLEGE(classe);
//        Optional<ScolariteCollege> scolariteOpt = scolariteRepository.findByClasseCOLLEGE(classe);
//        double montantScolarite = scolariteOpt.map(s -> s.getMontant().doubleValue()).orElse(0.0);
//
//        return eleves.stream().map(eleve -> {
//            PaiementCollegeDto dto = new PaiementCollegeDto();
//            dto.setEleveId(eleve.getId());
//            dto.setEleveNom(eleve.getNom());
//            dto.setElevePrenom(eleve.getPrenom());
//            dto.setClasseCOLLEGE(classe);
//            dto.setMontantScolarite(montantScolarite);
//
//            PaiementCollege dernierPaiement = paiementRepository.findTopByEleveCollegeOrderByIdDesc(eleve).orElse(null);
//
//            if (dernierPaiement != null) {
//                dto.setDatePaiement(dernierPaiement.getDatePaiement());
//                dto.setMontantActuel(dernierPaiement.getMontantActuel());
//                dto.setMontantDejaPaye(dernierPaiement.getMontantDejaPaye());
//                dto.setResteEcolage(dernierPaiement.getResteEcolage());
//                dto.setStttScoCollg(dernierPaiement.getStttScoCollg());
//            } else {
//                dto.setMontantActuel(0);
//                dto.setMontantDejaPaye(0);
//                dto.setResteEcolage((long) montantScolarite);
//                dto.setStttScoCollg(StttScoCollg.EN_COURS);
//                dto.setDatePaiement(null);
//            }
//
//            return dto;
//        }).collect(Collectors.toList());
//    }
//
//    public List<PaiementCollegeHistoriqueDto> getHistoriquePaiementsParEleveId(Long eleveId) {
//        EleveCollege eleve = eleveRepository.findById(eleveId)
//                .orElseThrow(() -> new RuntimeException("Élève non trouvé avec l'ID : " + eleveId));
//
//        List<PaiementCollege> paiements = paiementRepository.findByEleveCollegeOrderByDatePaiementDesc(eleve);
//
//        return paiements.stream().map(paiement -> {
//            PaiementCollegeHistoriqueDto dto = new PaiementCollegeHistoriqueDto();
//            dto.setDatePaiement(paiement.getDatePaiement());
//            dto.setMontantActuel(paiement.getMontantActuel());
//            dto.setResteEcolage(paiement.getResteEcolage());
//            return dto;
//        }).collect(Collectors.toList());
//    }
//}