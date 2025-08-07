package com.eduMap.edumap.B_COLLEGE.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "CollegeMatiere")

public class MatiereCollegeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;


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
}
