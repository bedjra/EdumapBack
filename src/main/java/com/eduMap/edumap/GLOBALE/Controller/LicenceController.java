package com.eduMap.edumap.GLOBALE.Controller;

import com.eduMap.edumap.GLOBALE.service.LicenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Tag(name = "Licence ", description = "Gestion de la licence")

@RestController
@RequestMapping("/licence")
public class LicenceController {


    @Autowired
    private LicenceService licenceService;


//    @PostMapping
//    public ResponseEntity<?> validerLicence(@RequestBody Map<String, String> payload) {
//        String licenceKey = payload.get("licenceKey");
//        if (licenceKey == null || licenceKey.isEmpty()) {
//            return ResponseEntity.badRequest().body(Map.of("message", "Clé de licence manquante"));
//        }
//
//        boolean estValide = licenceService.validerLicence(licenceKey);
//
//        if (estValide) {
//            return ResponseEntity.ok(Map.of("message", "Licence valide"));
//        } else {
//            return ResponseEntity.status(403).body(Map.of("message", "Licence invalide ou expirée"));
//        }
//    }
}
