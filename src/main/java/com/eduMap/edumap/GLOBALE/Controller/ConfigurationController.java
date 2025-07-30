package com.eduMap.edumap.GLOBALE.Controller;

import com.eduMap.edumap.GLOBALE.Dto.SystemAnneeResponse;
import com.eduMap.edumap.GLOBALE.Entity.Configuration;
import com.eduMap.edumap.GLOBALE.service.ConfigurationService;
import com.eduMap.edumap.GLOBALE.service.SystemAnneeScolaireViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Configuration", description = "Gestion de la configuration")

@RestController
@RequestMapping("/ecole")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private SystemAnneeScolaireViewService viewService;


    @Operation(summary = "Ajouter une ecole")
    @PostMapping
    public ResponseEntity<Configuration> saveConfiguration(@RequestBody Configuration config) {
        Configuration savedConfig = configurationService.saveConfiguration(config);
        return ResponseEntity.ok(savedConfig);
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
    @GetMapping
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
