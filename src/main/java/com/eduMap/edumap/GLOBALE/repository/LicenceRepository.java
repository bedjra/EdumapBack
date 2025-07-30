package com.eduMap.edumap.GLOBALE.repository;

import com.eduMap.edumap.GLOBALE.Entity.Licence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LicenceRepository extends JpaRepository<Licence, Long> {
    Optional<Licence> findByLicenceKeyAndNomEcole(String licenceKey, String nomEcole);

}
