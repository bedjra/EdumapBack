package com.eduMap.edumap.A_PRIMAIRE.Entity;

import com.eduMap.edumap.A_PRIMAIRE.enums.ClassePRIMAIRE;
import com.eduMap.edumap.A_PRIMAIRE.enums.EvaluationPrimaire;
import com.eduMap.edumap.A_PRIMAIRE.enums.MatierePrimaire;
import com.eduMap.edumap.GLOBALE.Entity.AnneeScolaire;
import jakarta.persistence.*;

@Entity
@Table(name = "PrimaireNote")

public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private AnneeScolaire anneeScolaire;

    @ManyToOne
    @JoinColumn(name = "eleve_id")
    private Eleve eleve;

    @Enumerated(EnumType.STRING)
    private ClassePRIMAIRE classe;

    @ManyToOne
    @JoinColumn(name = "matiere_id")
    private Matiere matiere;

    @Enumerated(EnumType.STRING)
    private MatierePrimaire matierePrimaire;

    private Double valeurNote;


    @Enumerated(EnumType.STRING)
    private EvaluationPrimaire evaluationPrimaire;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnneeScolaire getAnneeScolaire() {
        return anneeScolaire;
    }

    public void setAnneeScolaire(AnneeScolaire anneeScolaire) {
        this.anneeScolaire = anneeScolaire;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public ClassePRIMAIRE getClasse() {
        return classe;
    }

    public void setClasse(ClassePRIMAIRE classe) {
        this.classe = classe;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public MatierePrimaire getMatierePrimaire() {
        return matierePrimaire;
    }

    public void setMatierePrimaire(MatierePrimaire matierePrimaire) {
        this.matierePrimaire = matierePrimaire;
    }

    public Double getValeurNote() {
        return valeurNote;
    }

    public void setValeurNote(Double valeurNote) {
        this.valeurNote = valeurNote;
    }


    public EvaluationPrimaire getEvaluationPrimaire() {
        return evaluationPrimaire;
    }

    public void setEvaluationPrimaire(EvaluationPrimaire evaluationPrimaire) {
        this.evaluationPrimaire = evaluationPrimaire;
    }
}
