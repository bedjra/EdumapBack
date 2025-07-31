package skool.saas.skool.A_PRIMAIRE.Dto;



import skool.saas.skool.A_PRIMAIRE.Entity.Scolarite;
import skool.saas.skool.A_PRIMAIRE.enums.ClassePRIMAIRE;
import skool.saas.skool.A_PRIMAIRE.enums.StatutScolarite;

import java.time.LocalDate;

public class PaiementDto {

    private Long eleveId;
    private String eleveNom;
    private String elevePrenom;
    private ClassePRIMAIRE classe;
    private LocalDate datePaiement;
    private long montantActuel;
    private long resteEcolage;
    private long montantDejaPaye;
    private StatutScolarite statut;
    private Double montantScolarite;


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

    public ClassePRIMAIRE getClasse() {
        return classe;
    }

    public void setClasse(ClassePRIMAIRE classe) {
        this.classe = classe;
    }

    public Double getMontantScolarite() {
        return montantScolarite;
    }

    public void setMontantScolarite(Double montantScolarite) {
        this.montantScolarite = montantScolarite;
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

    public StatutScolarite getStatut() {
        return statut;
    }

    public void setStatut(StatutScolarite statut) {
        this.statut = statut;
    }
}
