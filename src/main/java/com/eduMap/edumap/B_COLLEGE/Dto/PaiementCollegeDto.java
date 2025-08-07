package com.eduMap.edumap.B_COLLEGE.Dto;


import com.eduMap.edumap.B_COLLEGE.enums.ClasseCOLLEGE;
import com.eduMap.edumap.B_COLLEGE.enums.StttScoCollg;

import java.time.LocalDate;

public class PaiementCollegeDto {
    private Long eleveId;
    private String eleveNom;
    private String elevePrenom;
    private ClasseCOLLEGE classeCOLLEGE;
    private LocalDate datePaiement;
    private long montantActuel;
    private long montantDejaPaye;
    private long resteEcolage;
    private StttScoCollg stttScoCollg;
    private Double montantScolarite;



    // Getters et Setters
    public Long getEleveId() {
        return eleveId;
    }

    public void setEleveId(Long eleveId) {
        this.eleveId = eleveId;
    }

    public String getEleveNom() {
        return eleveNom;
    }

    public void setEleveNom(String eleveNom) {
        this.eleveNom = eleveNom;
    }

    public String getElevePrenom() {
        return elevePrenom;
    }

    public void setElevePrenom(String elevePrenom) {
        this.elevePrenom = elevePrenom;
    }

    public ClasseCOLLEGE getClasseCOLLEGE() {
        return classeCOLLEGE;
    }

    public void setClasseCOLLEGE(ClasseCOLLEGE classeCOLLEGE) {
        this.classeCOLLEGE = classeCOLLEGE;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public long getMontantActuel() {
        return montantActuel;
    }

    public void setMontantActuel(long montantActuel) {
        this.montantActuel = montantActuel;
    }

    public long getMontantDejaPaye() {
        return montantDejaPaye;
    }

    public void setMontantDejaPaye(long montantDejaPaye) {
        this.montantDejaPaye = montantDejaPaye;
    }

    public long getResteEcolage() {
        return resteEcolage;
    }

    public void setResteEcolage(long resteEcolage) {
        this.resteEcolage = resteEcolage;
    }

    public StttScoCollg getStttScoCollg() {
        return stttScoCollg;
    }

    public void setStttScoCollg(StttScoCollg stttScoCollg) {
        this.stttScoCollg = stttScoCollg;
    }

    public Double getMontantScolarite() {
        return montantScolarite;
    }

    public void setMontantScolarite(Double montantScolarite) {
        this.montantScolarite = montantScolarite;
    }
}
