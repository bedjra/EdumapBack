package com.eduMap.edumap.B_COLLEGE.Dto;

import java.time.LocalDate;

public class PaiementCollegeHistoriqueDto {
    private LocalDate datePaiement;
    private long montantActuel;
    private long resteEcolage;



    // Getters et Setters
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

    public long getResteEcolage() {
        return resteEcolage;
    }

    public void setResteEcolage(long resteEcolage) {
        this.resteEcolage = resteEcolage;
    }
}
