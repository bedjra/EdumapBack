package com.eduMap.edumap.GLOBALE.service;

import com.eduMap.edumap.GLOBALE.Entity.Configuration;
import com.eduMap.edumap.GLOBALE.repository.ConfigurationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConfigurationService {

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Autowired
    private AnneeScolaireService anneeScolaireService;


    public Configuration saveConfiguration(Configuration config) {
        Configuration saved = configurationRepository.save(config);

        // Si c’est la première configuration, on initialise l’année scolaire
        if (configurationRepository.count() == 1) {
            anneeScolaireService.initialiserOuActiverAnneeScolaire();
        }

        return saved;
    }



    public Configuration updateConfiguration(Long id, Configuration config) {
        Configuration existing = configurationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Configuration non trouvée"));

        // Met à jour les champs manuellement si besoin
        existing.setNom(config.getNom());
        existing.setAdresse(config.getAdresse());
        existing.setTel(config.getTel());
        existing.setCel(config.getCel());
        existing.setBp(config.getBp());
        existing.setDevise(config.getDevise());
        existing.setImage(config.getImage());
        existing.setSysteme(config.getSysteme());

        return configurationRepository.save(existing);
    }


    // Récupérer toutes les configurations
    public List<Configuration> getAllConfigurations() {
        return configurationRepository.findAll();
    }

    // Récupérer une configuration par ID
    public Optional<Configuration> getConfigurationById(Long id) {
        return configurationRepository.findById(id);
    }

    // Supprimer une configuration
    public void deleteConfiguration(Long id) {
        configurationRepository.deleteById(id);
    }

    public byte[] getImage() {
        return configurationRepository.findImage();
    }





}
