package com.eduMap.edumap.GLOBALE.service;



import com.eduMap.edumap.GLOBALE.Entity.Configuration;
import com.eduMap.edumap.GLOBALE.Entity.Utilisateur;
import com.eduMap.edumap.GLOBALE.repository.ConfigurationRepository;
import com.eduMap.edumap.GLOBALE.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Autowired
    private LicenceService licenceService;



    public String getRoleByEmail(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email);
        if (utilisateur != null && utilisateur.getRole() != null) {
            return utilisateur.getRole().name();
        }
        return null;
    }


    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }


    @Transactional
    public Utilisateur updateUtilisateur(Long id, Utilisateur updatedData) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l’ID: " + id));

        utilisateur.setEmail(updatedData.getEmail());
        utilisateur.setPassword(updatedData.getPassword());
        utilisateur.setRole(updatedData.getRole());

        return utilisateurRepository.save(utilisateur);
    }

    public void deleteUtilisateur(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new EntityNotFoundException("Utilisateur non trouvé avec l’ID: " + id);
        }
        utilisateurRepository.deleteById(id);
    }


    public List<Utilisateur> getAll() {
        return utilisateurRepository.findAll();
    }


    public Utilisateur getUtilisateurConnecte() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        String email = authentication.getName(); 

        return utilisateurRepository.findByEmail(email);
    }




    public Utilisateur findByEmailAndPassword(String email, String password) {
        // Bloquer la connexion si la licence est expirée
        if (!licenceService.isLicenceValide()) {
            throw new IllegalStateException("La licence est expirée. Veuillez contacter l'administrateur.");
        }

        return utilisateurRepository.findByEmailAndPassword(email, password);
    }



    public Map<String, Object> login(String email, String password) {
        Map<String, Object> response = new HashMap<>();

        Utilisateur utilisateur = findByEmailAndPassword(email, password);

        if (utilisateur == null) {
            throw new IllegalArgumentException("Email ou mot de passe incorrect");
        }

        response.put("success", true);
        response.put("message", "Login réussi");
        response.put("role", utilisateur.getRole().name());

        Configuration config = configurationRepository.findAll()
                .stream().findFirst()
                .orElse(null);

        if (config != null) {
            response.put("systeme", config.getSysteme());
        } else {
            response.put("systeme", null);
        }

        return response;
    }

}
