package skool.saas.skool.A_PRIMAIRE.Dto;

import java.time.LocalDate;

public class PaiementRequestDto {
    private String eleveNom;
    private String elevePrenom;
    private long montantActuel;
    private LocalDate datePaiement;


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

    public long getMontantActuel() {
        return montantActuel;
    }

    public void setMontantActuel(long montantActuel) {
        this.montantActuel = montantActuel;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }
}
