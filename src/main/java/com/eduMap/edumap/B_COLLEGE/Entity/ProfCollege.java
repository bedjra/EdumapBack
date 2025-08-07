package com.eduMap.edumap.B_COLLEGE.Entity;

import com.eduMap.edumap.B_COLLEGE.enums.MatiereCollege;
import jakarta.persistence.*;

@Entity
@Table(name = "CollegeProfesseur")
public class ProfCollege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String adresse;

    //Relation avec Tuteur
    @ManyToOne
    @JoinColumn(name = "matiere_entity_id")
    private MatiereCollegeEntity matiereCollegeEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "matiere_enum", nullable = true)
    private MatiereCollege matiereCollege;


    @Column(nullable = false)
    private String diplome;

    @Column(nullable = false)
    private String telephone;

    // Nouveau champ pour savoir s'il est titulaire
    @Column(nullable = false)
    private Boolean titulaire;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public MatiereCollegeEntity getMatiereCollegeEntity() {
        return matiereCollegeEntity;
    }

    public void setMatiereCollegeEntity(MatiereCollegeEntity matiereCollegeEntity) {
        this.matiereCollegeEntity = matiereCollegeEntity;
    }

    public MatiereCollege getMatiereCollege() {
        return matiereCollege;
    }

    public void setMatiereCollege(MatiereCollege matiereCollege) {
        this.matiereCollege = matiereCollege;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean getTitulaire() {
        return titulaire;
    }

    public void setTitulaire(Boolean titulaire) {
        this.titulaire = titulaire;
    }
}

