package com.eduMap.edumap.B_COLLEGE.Entity;

import com.eduMap.edumap.B_COLLEGE.enums.ClasseCOLLEGE;
import jakarta.persistence.*;

@Entity
public class TitulaireCollege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ClasseCOLLEGE classeCOLLEGE;


    private String nom;


    // Constructeurs, Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClasseCOLLEGE getClasseCOLLEGE() {
        return classeCOLLEGE;
    }

    public void setClasseCOLLEGE(ClasseCOLLEGE classeCOLLEGE) {
        this.classeCOLLEGE = classeCOLLEGE;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
