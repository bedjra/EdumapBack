package com.eduMap.edumap.A_PRIMAIRE.Controller;

import com.eduMap.edumap.A_PRIMAIRE.Dto.*;
import com.eduMap.edumap.A_PRIMAIRE.Entity.*;
import com.eduMap.edumap.A_PRIMAIRE.enums.ClassePRIMAIRE;
import com.eduMap.edumap.A_PRIMAIRE.enums.EvaluationPrimaire;
import com.eduMap.edumap.A_PRIMAIRE.service.*;
import com.eduMap.edumap.A_PRIMAIRE.service.PdfService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/Primaire")

@Tag(name = "Primaire", description = "Gestion du system PRIMAIRE")

public class PrimaireController {

    @Autowired
    private EleveService eleveService;

    @Autowired
    private TuteurService tuteurService;

    @Autowired
    private ScolariteService scolariteService;

    @Autowired
    private ProfesseurService professeurService;

    @Autowired
    private PaiementService paiementService;

    @Autowired
    private MatiereService matiereService;

    @Autowired
    private NoteService notePrimaireService;

    @Autowired
    private PdfService pdfService;

    @Operation(summary = "nbre eleve ")
    @GetMapping("/count")
    public long getNombreTotalEleves() {
        return eleveService.compterTotalEleves();
    }

    @GetMapping("/totaux")
    public Map<String, Long> getTotauxPaiement() {
        return paiementService.getMontantsGlobal();
    }

    @Operation(summary = "Ajout d'un eleve ")
    @PostMapping("/eleve")
    public ResponseEntity<Eleve> ajouterEleve(@RequestBody EleveDto dto) {
        Eleve eleveCree = eleveService.ajouterEleveEtTuteur(dto);
        return ResponseEntity.ok(eleveCree);
    }
    @Operation(summary = "modifier d'un eleve ")
    @PutMapping("/eleve/{id}")
    public ResponseEntity<Eleve> modifierEleve(
            @PathVariable Long id,
            @RequestBody EleveDto dto) {
        Eleve updated = eleveService.modifierEleve(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "delete un eleve")
    @DeleteMapping("/eleve/{id}")
    @PreAuthorize("hasRole('ADMIN')")  // Seul le rôle ADMIN peut appeler cette méthode
    public ResponseEntity<Void> supprimerEleve(@PathVariable Long id) {
        try {
            eleveService.supprimerEleve(id);
            return ResponseEntity.noContent().build();  // 204 No Content si succès
        } catch (EntityNotFoundException e) {
            // Élève non trouvé : on renvoie 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Pour toute autre erreur, renvoyer 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @Operation(summary = "Récupérer tous les tuteurs ")
    @GetMapping("/tuteur")
    public List<TuteurSimpleDto> getAllTuteurs() {
        return tuteurService.getAllTuteurs();
    }


    @Operation(summary = "get by nom et prenom ")
    @GetMapping("/getby")
    public ResponseEntity<?> getEleveByNomAndPrenom(
            @RequestParam String nom,
            @RequestParam String prenom) {

        return eleveService.getEleveByNomAndPrenom(nom, prenom)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "get les stats ")
    @GetMapping("/stats")
    public List<ClasseStatistiqueDto> getStatistiquesPrimaire() {
        return eleveService.getStatistiquesParClassePrimaire();
    }

    @Operation(summary = "get les eleves par classe  ")
    @GetMapping("/{classe}")
    public List<Eleve> getElevesByClasse(@PathVariable("classe") String classeStr) {
        ClassePRIMAIRE classe;

        try {
            classe = ClassePRIMAIRE.valueOf(classeStr.toUpperCase()); // Ex: "cp1" => CP1
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Classe invalide : " + classeStr);
        }

        return eleveService.getElevesByClasse(classe);
    }


    @Operation(summary = "Récupérer tous les classes")
    @GetMapping("/classes")
    public List<String> getAllClassesPrimaire() {
        return Arrays.stream(ClassePRIMAIRE.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Operation(summary = "get un eleve par son id")
    @GetMapping("/eleve/{id}")
    public EleveDto getEleveById(@PathVariable Long id) {
        return eleveService.getEleveById(id);
    }

    @Operation(summary = "Get un élève par son matricule")
    @GetMapping("/eleve/matricule/{matricule}")
    public ResponseEntity<EleveDto> getEleveByMatricule(@PathVariable String matricule) {
        EleveDto eleveDto = eleveService.getEleveByMatricule(matricule);
        return ResponseEntity.ok(eleveDto);
    }


    // // // // // // // // // // // // // // // // // // // // // // //
    // // // // //// // //  Scolarité
    @Operation(summary = "ajout de scolarite")
    @PostMapping("/scolarite")
    public ResponseEntity<Scolarite> createScolarite(@RequestBody Scolarite scolarite) {
        Scolarite saved = scolariteService.saveScolarite(scolarite);
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "put scolarite par son id")
    @PutMapping("/scolarite/{id}")
    public ResponseEntity<Scolarite> updateScolarite(
            @PathVariable Long id,
            @RequestParam Long montant) {
        Scolarite updated = scolariteService.updateScolarite(id, montant);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "get les scolarites")
    @GetMapping("/scolarite")
    public List<Scolarite> getAllScolarites() {
        return scolariteService.getAllScolarites();
    }

    @Operation(summary = "get scolarite by classe")
    @GetMapping("/scolarite/{classe}")
    public ResponseEntity<Scolarite> getScolariteByClasse(@RequestParam ClassePRIMAIRE classe) {
        return scolariteService.getScolariteByClasse(classe)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    // // // // // // // // // // // // // // // // // // // // // // //
    // // // // //// // //  Matiere
    @Operation(summary = "get les matieres ")
    @GetMapping("/matiere")
    public Map<String, Object> getAllMatieres() {
        return matiereService.getAllMatieres();
    }

    @Operation(summary = "add matiere ")
    @PostMapping("/matiere")
    public ResponseEntity<Matiere> createMatiere(@RequestBody Matiere matiere) {
        Matiere saved = matiereService.createMatiere(matiere);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "put matiere ")
    @PutMapping("/matiere/{id}")
    public ResponseEntity<Matiere> updateMatiere(@PathVariable Long id, @RequestBody Matiere matiereDetails) {
        Matiere updated = matiereService.updateMatiere(id, matiereDetails);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "delete matiere ")
    @DeleteMapping("/matiere/{id}")
    public ResponseEntity<Void> deleteMatiere(@PathVariable Long id) {
        matiereService.deleteMatiere(id);
        return ResponseEntity.noContent().build();
    }



    // // // // // // // // // // // // // // // // // // // // // // //
    // // // // //// // //  Professeur
    @Operation(summary = "ajout de prof")
    @PostMapping("/prof")
    public ResponseEntity<Professeur> createProf(@RequestBody Professeur prof) {
        Professeur saved = professeurService.saveProfesseur(prof);
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "get profs")
    @GetMapping("/profs")
    public List<Professeur> getAllProfesseurs() {
        return professeurService.getAllProfesseurs();
    }


    @Operation(summary = "put prof par son id")
    @PutMapping("/prof/{id}")
    public ResponseEntity<Professeur> updateProf(@PathVariable Long id, @RequestBody Professeur prof) {
        Professeur updated = professeurService.updateProfesseur(id, prof);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "delete prof par son id")
    @DeleteMapping("/prof/{id}")
    public ResponseEntity<Void> deleteProf(@PathVariable Long id) {
        professeurService.deleteProfesseur(id);
        return ResponseEntity.noContent().build();
    }



    // // // // // // // // // // // // // // // // // // // // // // //
    // // // // //// // //  Paiement
    @Operation(summary = "Enregistrer un paiement et générer le reçu PDF")
    @PostMapping("/paiement")
    public ResponseEntity<PaiementDto> enregistrerPaiement(@RequestBody PaiementRequestDto dto) {
        try {
            PaiementDto paiementDto = paiementService.enregistrerPaiement(dto);

            // 📂 Génération et stockage du PDF dans Recu/
            pdfService.genererRecuPaiement(paiementDto);

            // ✅ Retourne juste le PaiementDto (pas le PDF)
            return ResponseEntity.ok(paiementDto);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @Operation(summary = "Récupérer le reçu PDF d’un paiement")
    @GetMapping("/paiement/recu/{id}")
    public ResponseEntity<byte[]> getRecuPaiement(@PathVariable Long id) {
        try {
            // 📂 Chemin vers ton dossier Recu/
            Path pdfPath = Paths.get("Recu/recu_" + id + ".pdf");

            if (!Files.exists(pdfPath)) {
                return ResponseEntity.notFound().build();
            }

            byte[] pdfBytes = Files.readAllBytes(pdfPath);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=recu_" + id + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }





    @Operation(summary = "get les paiement by id")
    @GetMapping("/paiement/{eleveId}")
    public ResponseEntity<PaiementDto> getPaiementParEleveId(@PathVariable Long eleveId) {
        try {
            PaiementDto dto = paiementService.getPaiementParEleveId(eleveId);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // ou badRequest selon le message
        }
    }


    @Operation(summary = "get les paiements d'une classe")
    @GetMapping("/paie/eleve/{classe}")
    public ResponseEntity<?> getPaiementsParClasse(@PathVariable ClassePRIMAIRE classe) {
        try {
            List<PaiementDto> paiements = paiementService.getPaiementsParClasse(classe);
            return ResponseEntity.ok(paiements);
        } catch (Exception e) {
            e.printStackTrace(); // Log dans la console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @Operation(summary = "Historique simple des paiements d'un élève (date, montant, reste)")
    @GetMapping("/paie/his/{eleveId}")
    public ResponseEntity<List<PaiementHistoriqueDto>> getHistoriqueSimple(@PathVariable Long eleveId) {
        try {
            List<PaiementHistoriqueDto> historique = paiementService.getHistoriquePaiementsParEleveId(eleveId);
            return ResponseEntity.ok(historique);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/paiement/stat")
    public ResponseEntity<List<StatPaiementPrimaireDTO>> getStatistiquesPaiementPrimaire() {
        List<PaiementDto> paiements = paiementService.getPaiementsPrimaire(); // à adapter selon ta source
        List<StatPaiementPrimaireDTO> statistiques = paiementService.genererStatistiquesPaiements(paiements);
        return ResponseEntity.ok(statistiques);
    }

    @GetMapping("/paiement/renvoi/{classe}")
    public ResponseEntity<List<PaiementDto>> getDerniersPaiementsParClasse(
            @PathVariable("classe") ClassePRIMAIRE classe) {
        List<PaiementDto> paiements = paiementService.getDerniersPaiementsParClasseAvecReste(classe);
        return ResponseEntity.ok(paiements);
    }


    @GetMapping("/paie/search")
    public ResponseEntity<List<PaiementHistoriqueDto>> rechercherPaiement(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String prenom) {

        List<PaiementHistoriqueDto> resultats = paiementService.rechercherHistoriquePaiement(nom, prenom);
        return ResponseEntity.ok(resultats);
    }

    // // // // // // // // // // // // // // // // // // // // // // //
    // // // // //// // //  Notes
    @Operation(summary = "Ajouter des notes pour un élève à partir de son nom, prénom et classe")
    @PostMapping("/note")
    public ResponseEntity<Map<String, String>> mettreAJourNotes(@RequestBody NoteDto noteDto) {
        Map<String, String> response = new HashMap<>();
        try {
            notePrimaireService.mettreAJourNotes(noteDto);
            response.put("message", "✅ Notes mises à jour avec succès.");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("message", "❌ Erreur : " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("message", "❌ Erreur serveur : " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }



    @GetMapping("/note/all")
    public ResponseEntity<?> getAllNotesDto() {
        try {
            List<NoteDto> notes = notePrimaireService.getAllNotesAsDto();
            return ResponseEntity.ok(notes);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Erreur serveur : " + e.getMessage());
        }
    }

    @GetMapping("/note/{eleveId}")
    public ResponseEntity<List<Note>> getAllNotes(@PathVariable Long eleveId) {
        return ResponseEntity.ok(notePrimaireService.getAllNotes(eleveId));
    }

    @Operation(summary = "Récupérer les notes d'un élève pour une évaluation donnée")
    @GetMapping("/note/{eleveId}/{evaluation}")
    public ResponseEntity<?> getNotesByEvaluation(
            @PathVariable Long eleveId,
            @PathVariable EvaluationPrimaire evaluation) {
        try {
            List<NoteDto> notesDto = notePrimaireService.getNotesByEvaluation(eleveId, evaluation);

            if (notesDto.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("❌ Aucune note trouvée pour cet élève.");
            }

            return ResponseEntity.ok(notesDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("❌ Erreur : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Erreur serveur : " + e.getMessage());
        }
    }




}
