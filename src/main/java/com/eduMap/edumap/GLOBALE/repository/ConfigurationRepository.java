package com.eduMap.edumap.GLOBALE.repository;

import com.eduMap.edumap.GLOBALE.Entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
    Optional<Configuration> findFirstByOrderByIdAsc();

    @Query("SELECT c.image FROM Configuration c WHERE c.id = (SELECT MIN(c2.id) FROM Configuration c2)")
    byte[] findImage();
}
