package skool.saas.skool.A_PRIMAIRE.Entity;

import jakarta.persistence.*;
import lombok.Data;
import skool.saas.skool.A_PRIMAIRE.enums.ClassePRIMAIRE;
import skool.saas.skool.A_PRIMAIRE.enums.StatutScolarite;
import skool.saas.skool.GLOBALE.Entity.AnneeScolaire;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "PrimairePaiement")

public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate datePaiement;
    private long resteEcolage;
    private long montantDejaPaye;
    private long montantActuel;


    @ManyToOne
    @JoinColumn(name = "eleve_id")
    private Eleve eleve;

    @ManyToOne
    @JoinColumn(name = "scolarite_id")
    private Scolarite scolarite;


    @Enumerated(EnumType.STRING)
    private ClassePRIMAIRE classe;

    @Enumerated(EnumType.STRING)
    private StatutScolarite statut;

    @ManyToOne
    private AnneeScolaire anneeScolaire;


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

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public Scolarite getScolarite() {
        return scolarite;
    }

    public void setScolarite(Scolarite scolarite) {
        this.scolarite = scolarite;
    }

    public ClassePRIMAIRE getClasse() {
        return classe;
    }

    public void setClasse(ClassePRIMAIRE classe) {
        this.classe = classe;
    }

    public StatutScolarite getStatut() {
        return statut;
    }

    public void setStatut(StatutScolarite statut) {
        this.statut = statut;
    }

    public AnneeScolaire getAnneeScolaire() {
        return anneeScolaire;
    }

    public void setAnneeScolaire(AnneeScolaire anneeScolaire) {
        this.anneeScolaire = anneeScolaire;
    }
}