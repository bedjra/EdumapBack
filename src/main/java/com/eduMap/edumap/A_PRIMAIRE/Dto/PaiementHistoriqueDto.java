package skool.saas.skool.A_PRIMAIRE.Dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaiementHistoriqueDto {
    private LocalDate datePaiement;
    private long montantActuel;
    private long resteEcolage;


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