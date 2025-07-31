package com.eduMap.edumap.GLOBALE.service;

import com.eduMap.edumap.GLOBALE.Entity.Licence;
import com.eduMap.edumap.GLOBALE.repository.LicenceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
@Service
public class LicenceService {

    @Autowired
    private LicenceRepository licenceRepository;

    public boolean isLicenceValide() {
        Licence licence = licenceRepository.findFirstByActiveTrue(); // méthode à créer
        if (licence == null) return false;
        return licence.getDateFin().isAfter(LocalDate.now());
    }
}
