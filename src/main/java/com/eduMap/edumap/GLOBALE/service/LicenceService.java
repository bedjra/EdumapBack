package com.eduMap.edumap.GLOBALE.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class LicenceService {
//    @Autowired
//    private LicenceRepository licenceRepository;
//
//
//    @Autowired
//    private ConfigurationRepository configurationRepository;
//
//
//    @Transactional
//    public boolean validerLicence(String licenceKey) {
//        // 1. Récupération du nom de l'école locale via la configuration
//        Optional<Configuration> configOpt = configurationRepository.findFirstByOrderByIdAsc();
//        if (configOpt.isEmpty()) return false;
//
//        String nomEcole = configOpt.get().getNom();
//
//        // 2. Rechercher une licence avec cette clé et ce nom d’école
//        Optional<Licence> licenceOpt = licenceRepository
//                .findByLicenceKeyAndNomEcole(licenceKey, nomEcole);
//
//        if (licenceOpt.isEmpty()) return false;
//
//        Licence licence = licenceOpt.get();
//
//        // 3. Vérification des dates et état actif
//        LocalDate today = LocalDate.now();
//        boolean valide = licence.isActive()
//                && !today.isBefore(licence.getDateDebut())
//                && !today.isAfter(licence.getDateFin());
//
//        if (valide) {
//            // Facultatif : marquer comme validée localement ou activer les fonctionnalités
//            // Exemple : licence.setLicenceValidee(true);
//            // licenceRepository.save(licence);
//            return true;
//        }
//
//        return false;
//    }

}
