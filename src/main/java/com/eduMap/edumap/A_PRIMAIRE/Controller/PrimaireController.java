package skool.saas.skool.A_PRIMAIRE.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import skool.saas.skool.A_PRIMAIRE.Dto.*;
import skool.saas.skool.A_PRIMAIRE.Entity.Eleve;
import skool.saas.skool.A_PRIMAIRE.Entity.Matiere;
import skool.saas.skool.A_PRIMAIRE.Entity.Professeur;
import skool.saas.skool.A_PRIMAIRE.Entity.Scolarite;
import skool.saas.skool.A_PRIMAIRE.enums.ClassePRIMAIRE;
import skool.saas.skool.A_PRIMAIRE.service.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Primaire")

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

    @Operation(summary = "delete un eleve ")
    @DeleteMapping("/eleve/{id}")
    public ResponseEntity<Void> supprimerEleve(@PathVariable Long id) {
        eleveService.supprimerEleve(id);
        return ResponseEntity.noContent().build();
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
    @Operation(summary = "post un paiement")
    @PostMapping("/paiement")
    public ResponseEntity<PaiementDto> enregistrerPaiement(@RequestBody PaiementRequestDto dto) {
        try {
            PaiementDto paiementDto = paiementService.enregistrerPaiement(dto);
            return ResponseEntity.ok(paiementDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
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
    @GetMapping("/paiement/eleve/{classe}")
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
    @GetMapping("/paiement/historique/{eleveId}")
    public ResponseEntity<List<PaiementHistoriqueDto>> getHistoriqueSimple(@PathVariable Long eleveId) {
        try {
            List<PaiementHistoriqueDto> historique = paiementService.getHistoriquePaiementsParEleveId(eleveId);
            return ResponseEntity.ok(historique);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }




    // // // // // // // // // // // // // // // // // // // // // // //
    // // // // //// // //  Notes
    @Operation(summary = "Ajouter des notes pour un élève à partir de son nom, prénom et classe")
    @PostMapping("/note")
    public ResponseEntity<?> mettreAJourNotes(@RequestBody NoteDto noteDto) {
        try {
            notePrimaireService.mettreAJourNotes(noteDto);
            return ResponseEntity.ok("✅ Notes mises à jour avec succès.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("❌ Erreur : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Erreur serveur : " + e.getMessage());
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




}
