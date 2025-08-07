package com.eduMap.edumap.B_COLLEGE.Entity;

import com.eduMap.edumap.B_COLLEGE.enums.ClasseCOLLEGE;
import com.eduMap.edumap.B_COLLEGE.enums.StttScoCollg;
import com.eduMap.edumap.GLOBALE.Entity.AnneeScolaire;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;

@Data
@Entity
@Table(name = "CollegePaiement")

public class PaiementCollege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate datePaiement;
    private long resteEcolage;
    private long montantDejaPaye;
    private long montantActuel;

    @ManyToOne
    @JoinColumn(name = "eleve_id")
    private EleveCollege eleveCollege;

    @ManyToOne
    @JoinColumn(name = "scolarite_id")
    private ScolariteCollege scolariteCollege;

//
//    @Enumerated(EnumType.STRING)
//    private ClasseCOLLEGE classeCOLLEGE;

    @Enumerated(EnumType.STRING)
    private StttScoCollg stttScoCollg;

    @ManyToOne
    private AnneeScolaire anneeScolaire;



    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public long getResteEcolage() {
        return resteEcolage;
    }

    public void setResteEcolage(long resteEcolage) {
        this.resteEcolage = resteEcolage;
    }

    public long getMontantDejaPaye() {
        return montantDejaPaye;
    }

    public void setMontantDejaPaye(long montantDejaPaye) {
        this.montantDejaPaye = montantDejaPaye;
    }

    public long getMontantActuel() {
        return montantActuel;
    }

    public void setMontantActuel(long montantActuel) {
        this.montantActuel = montantActuel;
    }

    public EleveCollege getEleveCollege() {
        return eleveCollege;
    }

    public void setEleveCollege(EleveCollege eleveCollege) {
        this.eleveCollege = eleveCollege;
    }

    public ScolariteCollege getScolariteCollege() {
        return scolariteCollege;
    }

    public void setScolariteCollege(ScolariteCollege scolariteCollege) {
        this.scolariteCollege = scolariteCollege;
    }



    public StttScoCollg getStttScoCollg() {
        return stttScoCollg;
    }

    public void setStttScoCollg(StttScoCollg stttScoCollg) {
        this.stttScoCollg = stttScoCollg;
    }

    public AnneeScolaire getAnneeScolaire() {
        return anneeScolaire;
    }

    public void setAnneeScolaire(AnneeScolaire anneeScolaire) {
        this.anneeScolaire = anneeScolaire;
    }
}
