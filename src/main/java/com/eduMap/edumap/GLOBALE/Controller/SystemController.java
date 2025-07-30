package com.eduMap.edumap.GLOBALE.Controller;
import com.eduMap.edumap.GLOBALE.service.SystemStateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Tag(name = "System", description = "Gestion du choix de system")

@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemStateService systemStateService;


    @GetMapping
    @Operation(summary = "Récupérer le système actuellement sélectionné")
    public ResponseEntity<System> getCurrentSystem() {
        System system = systemStateService.getSystem();
        if (system == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(system);
    }


    @PostMapping
    @Operation(summary = "Le choix du système")
    public ResponseEntity<String> selectSystem(@RequestParam System system) {
        if (system == null) {
            return ResponseEntity.badRequest().body("Système invalide");
        }
        systemStateService.setSystem(system);
        return ResponseEntity.ok("Système sélectionné: " + system);
    }

}
