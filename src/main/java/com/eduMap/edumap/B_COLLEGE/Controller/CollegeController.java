package com.eduMap.edumap.B_COLLEGE.Controller;

import com.eduMap.edumap.B_COLLEGE.Dto.CollegeStatistiqueDto;
import com.eduMap.edumap.B_COLLEGE.Dto.EleveCollegeDto;
import com.eduMap.edumap.B_COLLEGE.Dto.TuteurCollegeSimpleDto;
import com.eduMap.edumap.B_COLLEGE.Entity.EleveCollege;
import com.eduMap.edumap.B_COLLEGE.Entity.MatiereCollegeEntity;
import com.eduMap.edumap.B_COLLEGE.Entity.ProfCollege;
import com.eduMap.edumap.B_COLLEGE.Entity.ScolariteCollege;
import com.eduMap.edumap.B_COLLEGE.enums.ClasseCOLLEGE;
import com.eduMap.edumap.B_COLLEGE.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;



import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/College")

@Tag(name = "College", description = "Gestion du system COLLEGE")

public class CollegeController {

    @Autowired
    private EleveCollegeService eleveCollegeService;

    @Autowired
    private TuteurCollegeService tuteurCollegeService;

    @Autowired
    private ScolariteCollegeService scolariteCollegeService;

    @Autowired
    private MatiereCollegeService matiereCollegeService;

    @Autowired
    private ProfCollegeService profCollegeService;

    @Operation(summary = "Ajout d'un eleve ")
    @PostMapping("/eleve")
    public ResponseEntity<EleveCollege> ajouterEleve(@RequestBody EleveCollegeDto dto) {
       EleveCollege eleveCree = eleveCollegeService.ajouterEleveEtTuteur(dto);
        return ResponseEntity.ok(eleveCree);
    }

    @Operation(summary = "modifier d'un eleve ")
    @PutMapping("/eleve/{id}")
    public ResponseEntity<EleveCollege> modifierEleveParId(
            @PathVariable("id") Long id,
            @RequestBody EleveCollegeDto dto) {

        EleveCollege eleveMisAJour = eleveCollegeService.modifierEleveEtTuteur(id, dto);
        return ResponseEntity.ok(eleveMisAJour);
    }

    @Operation(summary = "delete un eleve ")
    @DeleteMapping("/eleve/{id}")
    public ResponseEntity<Void> supprimerEleveParId(@PathVariable("id") Long id) {
        eleveCollegeService.supprimerEleve(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Récupérer tous les tuteurs ")
    @GetMapping("/tuteur")
    public List<TuteurCollegeSimpleDto> getAllTuteurs() {
        return tuteurCollegeService.getAllTuteurs();
    }


    @Operation(summary = "get by nom et prenom ")
    @GetMapping("/getby")
    public ResponseEntity<?> getEleveByNomAndPrenom(
            @RequestParam String nom,
            @RequestParam String prenom) {

        return eleveCollegeService.getEleveByNomAndPrenom(nom, prenom)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "get les stats ")
    @GetMapping("/stats")
    public List<CollegeStatistiqueDto> getStatistiquesCollege() {
        return eleveCollegeService.getStatistiquesParClasseCollege();
    }

    @Operation(summary = "get les eleves par classe  ")
    @GetMapping("/{classe}")
    public List<EleveCollege> getElevesByClasseCollege(@PathVariable("classe") String classeStr) {
        ClasseCOLLEGE classe;

        try {
            classe = ClasseCOLLEGE.valueOf(classeStr.toUpperCase()); // Ex: "cp1" => CP1
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Classe invalide : " + classeStr);
        }

        return eleveCollegeService.getElevesByClasseCollege(classe);
    }


    @Operation(summary = "get un eleve par son id")
    @GetMapping("/eleve/{id}")
    public ResponseEntity<EleveCollegeDto> getEleveParId(@PathVariable Long id) {
        EleveCollegeDto dto = eleveCollegeService.getEleveDtoById(id);
        return ResponseEntity.ok(dto);
    }


    @Operation(summary = "Récupérer tous les classes")
    @GetMapping("/classes")
    public List<ClasseCOLLEGE> getAllClassesCollege() {
        return Arrays.asList(ClasseCOLLEGE.values());
    }


    // // // // // // // // // // // // // // // // // // // // // // //
     // // // //// // //  Scolarité
    @Operation(summary = "ajout de scolarite")
    @PostMapping("/scolarite")
    public ResponseEntity<ScolariteCollege> createScolarite(@RequestBody ScolariteCollege scolarite) {
        ScolariteCollege saved = scolariteCollegeService.saveScolarite(scolarite);
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "put scolarite par son id")
    @PutMapping("/scolarite/{id}")
    public ResponseEntity<ScolariteCollege> updateScolarite(@PathVariable Long id, @RequestBody Long montant) {
        ScolariteCollege updated = scolariteCollegeService.updateScolarite(id, montant);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "get les scolarites")
    @GetMapping("/scolarite")
    public ResponseEntity<List<ScolariteCollege>> getAllScolarites() {
        return ResponseEntity.ok(scolariteCollegeService.getAll());
    }

    @Operation(summary = "get scolarite by classe")
    @GetMapping("/scolarite/{classe}")
    public ResponseEntity<ScolariteCollege> getByClasse(@PathVariable ClasseCOLLEGE classe) {
        return ResponseEntity.ok(scolariteCollegeService.getByClasse(classe));
    }


    // // // // // // // // // // // // // // // // // // // // // // //
    // // // // //// // //  Matiere
    @Operation(summary = "get les matieres ")
    @GetMapping("/matiere")
    public Map<String, Object> getAllMatieres() {
        return matiereCollegeService.getAllMatieres();
    }

    @Operation(summary = "add matiere ")
    @PostMapping("/matiere")
    public MatiereCollegeEntity createMatiere(@RequestBody MatiereCollegeEntity matiere) {
        return matiereCollegeService.createMatiere(matiere);
    }

    @Operation(summary = "put matiere ")
    @PutMapping("/matiere/{id}")
    public MatiereCollegeEntity updateMatiere(@PathVariable Long id, @RequestBody MatiereCollegeEntity matiere) {
        return matiereCollegeService.updateMatiere(id, matiere);
    }

    @Operation(summary = "delete matiere ")
    @DeleteMapping("/matiere/{id}")
    public void deleteMatiere(@PathVariable Long id) {
        matiereCollegeService.deleteMatiere(id);
    }


    // // // // // // // // // // // // // // // // // // // // // // //
    // // // // //// // //  Professeur
    @Operation(summary = "ajout de prof")
    @PostMapping("/prof")
    public ResponseEntity<ProfCollege> ajouterProf(@RequestBody ProfCollege prof,
                                                   @RequestParam(required = false) Long matiereId) {
        ProfCollege nouveauProf = profCollegeService.enregistrerProf(prof, matiereId);
        return ResponseEntity.ok(nouveauProf);
    }

    @Operation(summary = "get profs")
    @GetMapping("/profs")
    public ResponseEntity<List<ProfCollege>> getAll() {
        List<ProfCollege> liste = profCollegeService.getAllProfs();
        return ResponseEntity.ok(liste);
    }

    @Operation(summary = "put prof par son id")
    @GetMapping("/prof/{id}")
    public ResponseEntity<ProfCollege> getById(@PathVariable Long id) {
        Optional<ProfCollege> prof = profCollegeService.getProfById(id);
        return prof.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "delete prof par son id")
    @DeleteMapping("/prof/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        profCollegeService.supprimerProf(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "put prof par son id")
    @PutMapping("/prof/{id}")
    public ResponseEntity<ProfCollege> mettreAJour(@PathVariable Long id,
                                                   @RequestBody ProfCollege prof,
                                                   @RequestParam(required = false) Long matiereId) {
        Optional<ProfCollege> updated = profCollegeService.mettreAJourProf(id, prof, matiereId);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    // // // // // // // // // // // // // // // // // // // // // // //
    // // // // //// // //  Paiement



    // // // // // // // // // // // // // // // // // // // // // // //
    // // // // //// // //  Notes


}
