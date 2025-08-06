package com.eduMap.edumap.A_PRIMAIRE.service;


import com.eduMap.edumap.A_PRIMAIRE.Dto.PaiementDto;
import com.eduMap.edumap.A_PRIMAIRE.Dto.PaiementHistoriqueDto;
import com.eduMap.edumap.A_PRIMAIRE.Dto.PaiementRequestDto;
import com.eduMap.edumap.A_PRIMAIRE.Dto.StatPaiementPrimaireDTO;
import com.eduMap.edumap.A_PRIMAIRE.Entity.Eleve;
import com.eduMap.edumap.A_PRIMAIRE.Entity.Paiement;
import com.eduMap.edumap.A_PRIMAIRE.Entity.Scolarite;
import com.eduMap.edumap.A_PRIMAIRE.enums.ClassePRIMAIRE;
import com.eduMap.edumap.A_PRIMAIRE.enums.StatutScolarite;
import com.eduMap.edumap.A_PRIMAIRE.repository.EleveRepository;
import com.eduMap.edumap.A_PRIMAIRE.repository.PaiementRepository;
import com.eduMap.edumap.A_PRIMAIRE.repository.ScolariteRepository;
import com.eduMap.edumap.GLOBALE.Entity.AnneeContext;
import com.eduMap.edumap.GLOBALE.Entity.AnneeScolaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
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


    public List<PaiementDto> getPaiementsPrimaire() {
        List<Paiement> paiements = paiementRepository.findAll();

        paiements.forEach(p -> System.out.println("Paiement id=" + p.getId() + " classe élève=" + (p.getEleve() != null ? p.getEleve().getClasse() : "null")));

        return paiements.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private PaiementDto convertToDto(Paiement paiement) {
        ClassePRIMAIRE classeEleve = paiement.getEleve() != null ? paiement.getEleve().getClasse() : null;

        if (classeEleve == null) {
            System.out.println("Paiement avec classe élève NULL, id paiement = " + paiement.getId());
        } else {
            System.out.println("Paiement avec classe élève : " + classeEleve);
        }

        PaiementDto dto = new PaiementDto();
        dto.setEleveId(paiement.getEleve() != null ? paiement.getEleve().getId() : null);
        dto.setEleveNom(paiement.getEleve() != null ? paiement.getEleve().getNom() : null);
        dto.setElevePrenom(paiement.getEleve() != null ? paiement.getEleve().getPrenom() : null);
        dto.setClasse(classeEleve);  // on set la classe de l'élève ici
        dto.setDatePaiement(paiement.getDatePaiement());
        dto.setMontantActuel(paiement.getMontantActuel());
        dto.setResteEcolage(paiement.getResteEcolage());
        dto.setMontantDejaPaye(paiement.getMontantDejaPaye());
        dto.setStatut(paiement.getStatut());
        return dto;
    }

    public List<StatPaiementPrimaireDTO> genererStatistiquesPaiements(List<PaiementDto> paiements) {
        Map<ClassePRIMAIRE, StatPaiementPrimaireDTO> statsParClasse = new HashMap<>();

        // Comptage des paiements soldés ou en cours par classe
        for (PaiementDto paiement : paiements) {
            ClassePRIMAIRE classe = paiement.getClasse();

            if (classe == null) {
                System.out.println("PaiementDto sans classe, id eleve=" + paiement.getEleveId());
                continue; // on ignore ceux sans classe
            }

            StatPaiementPrimaireDTO stat = statsParClasse.getOrDefault(classe, new StatPaiementPrimaireDTO(classe, 0, 0, 0));
            long solde = stat.getNombreSolde();
            long enCours = stat.getNombreEnCours();

            if (paiement.getStatut() == StatutScolarite.SOLDÉ) {
                solde++;
            } else if (paiement.getStatut() == StatutScolarite.EN_COURS) {
                enCours++;
            }

            statsParClasse.put(classe, new StatPaiementPrimaireDTO(classe, 0, solde, enCours));
        }

        // Mise à jour du total élèves par classe
        for (ClassePRIMAIRE classe : statsParClasse.keySet()) {
            long total = eleveRepository.countByClasse(classe);
            StatPaiementPrimaireDTO stat = statsParClasse.get(classe);
            statsParClasse.put(classe, new StatPaiementPrimaireDTO(classe, total, stat.getNombreSolde(), stat.getNombreEnCours()));
        }

        return new ArrayList<>(statsParClasse.values());
    }


    public List<PaiementDto> getDerniersPaiementsParClasseAvecReste(ClassePRIMAIRE classe) {
        List<Paiement> paiements = paiementRepository.findByEleve_ClasseAndResteEcolageNot(classe, 0);

        // Grouper les paiements par id élève et garder le dernier paiement (par id ou date)
        Map<Long, Paiement> derniersPaiementsParEleve = paiements.stream()
                .collect(Collectors.toMap(
                        p -> p.getEleve().getId(), // clé = id de l'élève
                        p -> p,                    // valeur = paiement
                        (p1, p2) -> p1.getId() > p2.getId() ? p1 : p2 // garder celui avec l'id le plus grand (le plus récent)
                ));

        // Convertir en DTO
        return derniersPaiementsParEleve.values()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }



    public List<PaiementHistoriqueDto> rechercherHistoriquePaiement(String nom, String prenom) {
        List<Paiement> paiements;

        if (nom != null && !nom.isEmpty() && prenom != null && !prenom.isEmpty()) {
            paiements = paiementRepository.findByEleveNomContainingIgnoreCaseAndElevePrenomContainingIgnoreCase(nom, prenom);
        } else if (nom != null && !nom.isEmpty()) {
            paiements = paiementRepository.findByEleveNomContainingIgnoreCase(nom);
        } else if (prenom != null && !prenom.isEmpty()) {
            paiements = paiementRepository.findByElevePrenomContainingIgnoreCase(prenom);
        } else {
            // Si aucun critère n'est donné, retourner une liste vide
            return List.of();
        }

        return paiements.stream()
                .map(this::convertToHistoriqueDto)
                .collect(Collectors.toList());
    }

    private PaiementHistoriqueDto convertToHistoriqueDto(Paiement paiement) {
        PaiementHistoriqueDto dto = new PaiementHistoriqueDto();
        dto.setDatePaiement(paiement.getDatePaiement());
        dto.setMontantActuel(paiement.getMontantActuel());
        dto.setResteEcolage(paiement.getResteEcolage());
        return dto;
    }


}


