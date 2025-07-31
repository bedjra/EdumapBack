package com.eduMap.edumap.GLOBALE.Controller;

import com.eduMap.edumap.GLOBALE.Dto.SystemAnneeResponse;
import com.eduMap.edumap.GLOBALE.Entity.Configuration;
import com.eduMap.edumap.GLOBALE.enums.Systeme;
import com.eduMap.edumap.GLOBALE.service.ConfigurationService;
import com.eduMap.edumap.GLOBALE.service.SystemAnneeScolaireViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Tag(name = "Configuration", description = "Gestion de la configuration")

@RestController
@RequestMapping("/api/ecole")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private SystemAnneeScolaireViewService viewService;


    @Operation(summary = "Ajouter une école")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> enregistrerConfiguration(
            @RequestPart("nom") String nom,
            @RequestPart("adresse") String adresse,
            @RequestPart("tel") String tel,
            @RequestPart("cel") String cel,
            @RequestPart("bp") String bp,
            @RequestPart("devise") String devise,
            @RequestPart("systeme") String systeme,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        Configuration config = new Configuration();
        config.setNom(nom);
        config.setAdresse(adresse);
        config.setTel(tel);
        config.setCel(cel);
        config.setBp(bp);
        config.setDevise(devise);
        config.setSysteme(Systeme.valueOf(systeme));
        if (image != null && !image.isEmpty()) {
            config.setImage(image.getBytes());
        }

        // 🔁 Utilise ton service pour encapsuler toute la logique métier
        Configuration saved = configurationService.saveConfiguration(config);
        return ResponseEntity.ok(saved);
    }



    @Operation(summary = "Modifier une ecole")
    @PutMapping("/{id}")
    public ResponseEntity<Configuration> updateConfiguration(
            @PathVariable Long id,
            @RequestBody Configuration config) {
        Configuration updated = configurationService.updateConfiguration(id, config);
        return ResponseEntity.ok(updated);
    }



    @Operation(summary = "Recuperer une ecole")
    @GetMapping
    public List<Configuration> getAllConfigurations() {
        return configurationService.getAllConfigurations();
    }


    @Operation(summary = "Récupérer une configuration par ID")
    @GetMapping("/{id}")
    public ResponseEntity<Configuration> getConfigurationById(@PathVariable Long id) {
        Optional<Configuration> config = configurationService.getConfigurationById(id);
        return config.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "GET image d une ecole")
    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage() {
        byte[] image = configurationService.getImage();

        if (image == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "image/png"); // Modifier selon le format réel de l'image (ex: image/jpeg)

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }


    @GetMapping("/api")
    public SystemAnneeResponse getSystemeEtAnneeActive() {
        return viewService.getSystemeEtAnnee();
    }
}
