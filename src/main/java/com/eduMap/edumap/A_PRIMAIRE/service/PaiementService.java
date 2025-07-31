package skool.saas.skool.A_PRIMAIRE.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skool.saas.skool.A_PRIMAIRE.Dto.PaiementDto;
import skool.saas.skool.A_PRIMAIRE.Dto.PaiementHistoriqueDto;
import skool.saas.skool.A_PRIMAIRE.Dto.PaiementRequestDto;
import skool.saas.skool.A_PRIMAIRE.Entity.Eleve;
import skool.saas.skool.A_PRIMAIRE.Entity.Paiement;
import skool.saas.skool.A_PRIMAIRE.Entity.Scolarite;
import skool.saas.skool.A_PRIMAIRE.enums.ClassePRIMAIRE;
import skool.saas.skool.A_PRIMAIRE.enums.StatutScolarite;
import skool.saas.skool.A_PRIMAIRE.repository.EleveRepository;
import skool.saas.skool.A_PRIMAIRE.repository.PaiementRepository;
import skool.saas.skool.A_PRIMAIRE.repository.ScolariteRepository;
import skool.saas.skool.GLOBALE.Entity.AnneeContext;
import skool.saas.skool.GLOBALE.Entity.AnneeScolaire;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaiementService {

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private ScolariteRepository scolariteRepository;

    @Autowired
    private EleveRepository eleveRepository;

    public PaiementDto enregistrerPaiement(PaiementRequestDto dto) {
        Eleve eleve = eleveRepository.findByNomAndPrenom(dto.getEleveNom(), dto.getElevePrenom())
                .orElseThrow(() -> new RuntimeException("Élève introuvable"));

        Scolarite scolarite = scolariteRepository.findByClasse(eleve.getClasse())
                .orElseThrow(() -> new RuntimeException("Montant scolarité introuvable"));

        AnneeScolaire anneeActive = AnneeContext.get();

        double montantScolarite = scolarite.getMontant().doubleValue();
        Double dejaPaye = paiementRepository.sumMontantActuelByEleveAndAnnee(eleve, anneeActive);
        if (dejaPaye == null) dejaPaye = 0.0;

        double montantActuel = dto.getMontantActuel();
        double total = dejaPaye + montantActuel;

        if (total > montantScolarite) {
            throw new IllegalArgumentException("Le montant payé dépasse le reste à payer");
        }

        Paiement paiement = new Paiement();
        paiement.setEleve(eleve);
        paiement.setDatePaiement(dto.getDatePaiement());
        paiement.setMontantActuel((long) montantActuel);
        paiement.setMontantDejaPaye((long) total);
        paiement.setResteEcolage((long) (montantScolarite - total));
        paiement.setStatut(total == montantScolarite ? StatutScolarite.SOLDÉ : StatutScolarite.EN_COURS);
        paiement.setScolarite(scolarite);
        paiement.setAnneeScolaire(anneeActive);

        paiementRepository.save(paiement);

        // Construire et retourner la réponse DTO
        PaiementDto response = new PaiementDto();
        response.setEleveId(eleve.getId());
        response.setEleveNom(eleve.getNom());
        response.setElevePrenom(eleve.getPrenom());
        response.setClasse(eleve.getClasse());
        response.setDatePaiement(paiement.getDatePaiement());
        response.setMontantActuel(paiement.getMontantActuel());
        response.setMontantDejaPaye(paiement.getMontantDejaPaye());
        response.setResteEcolage(paiement.getResteEcolage());
        response.setStatut(paiement.getStatut());
        response.setMontantScolarite(montantScolarite);

        return response;
    }


    public PaiementDto getPaiementParEleveId(Long eleveId) {
        // Récupération de l'élève
        Eleve eleve = eleveRepository.findById(eleveId)
                .orElseThrow(() -> new RuntimeException("Élève non trouvé avec l'ID : " + eleveId));

        // Récupération de sa classe
        ClassePRIMAIRE classe = eleve.getClasse();

        // Recherche de la scolarité associée à la classe
        Optional<Scolarite> scolariteOpt = scolariteRepository.findByClasse(classe);
        double montantScolarite = scolariteOpt.map(s -> s.getMontant().doubleValue()).orElse(0.0);

        // Préparation du DTO
        PaiementDto dto = new PaiementDto();
        dto.setEleveId(eleve.getId());
        dto.setEleveNom(eleve.getNom());
        dto.setElevePrenom(eleve.getPrenom());
        dto.setClasse(classe);
        dto.setMontantScolarite(montantScolarite);

        // Récupération du dernier paiement (ordre par ID décroissant pour fiabilité)
        Paiement dernierPaiement = paiementRepository
                .findTopByEleveOrderByIdDesc(eleve)
                .orElse(null);

        if (dernierPaiement != null) {
            // Paiement trouvé : remplir les infos
            dto.setDatePaiement(dernierPaiement.getDatePaiement());
            dto.setMontantActuel(dernierPaiement.getMontantActuel());
            dto.setMontantDejaPaye(dernierPaiement.getMontantDejaPaye());
            dto.setResteEcolage(dernierPaiement.getResteEcolage());

            Long reste = dernierPaiement.getResteEcolage();
            boolean estSolde = (reste != null && reste == 0);
            dto.setStatut(estSolde ? StatutScolarite.SOLDÉ : StatutScolarite.EN_COURS);
        } else {
            // Aucun paiement existant
            dto.setMontantDejaPaye(0);
            dto.setResteEcolage((long) montantScolarite);
            dto.setStatut(StatutScolarite.EN_COURS);
            dto.setDatePaiement(null); // optionnel
        }

        return dto;
    }


    public List<PaiementDto> getPaiementsParClasse(ClassePRIMAIRE classe) {
        List<Eleve> eleves = eleveRepository.findByClasse(classe);
        Optional<Scolarite> scolariteOpt = scolariteRepository.findByClasse(classe);
        double montantScolarite = scolariteOpt.map(s -> s.getMontant().doubleValue()).orElse(0.0);

        return eleves.stream().map(eleve -> {
            PaiementDto dto = new PaiementDto();
            dto.setEleveId(eleve.getId());
            dto.setEleveNom(eleve.getNom());
            dto.setElevePrenom(eleve.getPrenom());
            dto.setClasse(classe);
            dto.setMontantScolarite(montantScolarite);

            // Récupération du dernier paiement inséré pour chaque élève
            Paiement dernierPaiement = paiementRepository
                    .findTopByEleveOrderByIdDesc(eleve)
                    .orElse(null);

            if (dernierPaiement != null) {
                dto.setDatePaiement(dernierPaiement.getDatePaiement());
                dto.setMontantActuel(dernierPaiement.getMontantActuel());
                dto.setMontantDejaPaye(dernierPaiement.getMontantDejaPaye());
                dto.setResteEcolage(dernierPaiement.getResteEcolage());

                Long reste = dernierPaiement.getResteEcolage();
                boolean estSolde = reste != null && reste == 0;
                dto.setStatut(estSolde ? StatutScolarite.SOLDÉ : StatutScolarite.EN_COURS);
            } else {
                dto.setMontantActuel(0);
                dto.setMontantDejaPaye(0);
                dto.setResteEcolage((long) montantScolarite);
                dto.setStatut(StatutScolarite.EN_COURS);
                dto.setDatePaiement(null); // si tu veux l'afficher
            }

            return dto;
        }).collect(Collectors.toList());
    }

    // historique //
    public List<PaiementHistoriqueDto> getHistoriquePaiementsParEleveId(Long eleveId) {
        Eleve eleve = eleveRepository.findById(eleveId)
                .orElseThrow(() -> new RuntimeException("Élève non trouvé avec l'ID : " + eleveId));

        List<Paiement> paiements = paiementRepository.findByEleveOrderByDatePaiementDesc(eleve);

        return paiements.stream().map(paiement -> {
            PaiementHistoriqueDto dto = new PaiementHistoriqueDto();
            dto.setDatePaiement(paiement.getDatePaiement());
            dto.setMontantActuel(paiement.getMontantActuel());
            dto.setResteEcolage(paiement.getResteEcolage());
            return dto;
        }).collect(Collectors.toList());
    }


}


