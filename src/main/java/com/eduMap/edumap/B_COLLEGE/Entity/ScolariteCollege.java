package com.eduMap.edumap.B_COLLEGE.Entity;

import com.eduMap.edumap.B_COLLEGE.enums.ClasseCOLLEGE;
import com.eduMap.edumap.GLOBALE.Entity.AnneeScolaire;
import jakarta.persistence.*;

@Entity
@Table(name = "CollegeScolarite") // nom de table plus clair
public class ScolariteCollege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long montant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private ClasseCOLLEGE classe;


    @ManyToOne
    private AnneeScolaire anneeScolaire;

    // --- Getters & Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMontant() {
        return montant;
    }

    public void setMontant(Long montant) {
        this.montant = montant;
    }

    public ClasseCOLLEGE getClasse() {
        return classe;
    }

    public void setClasse(ClasseCOLLEGE classe) {
        this.classe = classe;
    }

    public AnneeScolaire getAnneeScolaire() {
        return anneeScolaire;
    }

    public void setAnneeScolaire(AnneeScolaire anneeScolaire) {
        this.anneeScolaire = anneeScolaire;
    }
}
