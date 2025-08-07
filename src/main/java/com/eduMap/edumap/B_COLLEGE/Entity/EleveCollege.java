package com.eduMap.edumap.B_COLLEGE.Entity;

import com.eduMap.edumap.B_COLLEGE.enums.ClasseCOLLEGE;
import com.eduMap.edumap.GLOBALE.Entity.AnneeScolaire;
import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name = "CollegeEleve")

public class EleveCollege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String adresse;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClasseCOLLEGE classe;

    @Column(nullable = false)
    private String sexe;

    @Column(nullable = false)
    private String lieuNais;

    @Column(nullable = false)
    private String etblProv;

    private LocalDate dateIns;

    @Column(nullable = false)
    private String nationnalite;

    private LocalDate dateNaiss;

    @Lob
    @Column(name = "photo", columnDefinition = "LONGBLOB")
    private byte[] photo;


    //Relation avec Tuteur
    @ManyToOne
    @JoinColumn(name = "tuteur_id")
    private TuteurCollege tuteurCollege;


    // Cette méthode s'exécute automatiquement avant la persistance (insert)
    @PrePersist
    public void prePersist() {
        if (dateIns == null) {
            dateIns = LocalDate.now();
        }

    }


    @ManyToOne
    private AnneeScolaire anneeScolaire;

    // Getters et Setters...
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

    public ClasseCOLLEGE getClasse() {
        return classe;
    }

    public void setClasse(ClasseCOLLEGE classe) {
        this.classe = classe;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getLieuNais() {
        return lieuNais;
    }

    public void setLieuNais(String lieuNais) {
        this.lieuNais = lieuNais;
    }

    public String getEtblProv() {
        return etblProv;
    }

    public void setEtblProv(String etblProv) {
        this.etblProv = etblProv;
    }

    public LocalDate getDateIns() {
        return dateIns;
    }

    public void setDateIns(LocalDate dateIns) {
        this.dateIns = dateIns;
    }

    public String getNationnalite() {
        return nationnalite;
    }

    public void setNationnalite(String nationnalite) {
        this.nationnalite = nationnalite;
    }

    public LocalDate getDateNaiss() {
        return dateNaiss;
    }

    public void setDateNaiss(LocalDate dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public TuteurCollege getTuteurCollege() {
        return tuteurCollege;
    }

    public void setTuteurCollege(TuteurCollege tuteurCollege) {
        this.tuteurCollege = tuteurCollege;
    }

    public AnneeScolaire getAnneeScolaire() {
        return anneeScolaire;
    }

    public void setAnneeScolaire(AnneeScolaire anneeScolaire) {
        this.anneeScolaire = anneeScolaire;
    }
}
