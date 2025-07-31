package skool.saas.skool.A_PRIMAIRE.Entity;

import jakarta.persistence.*;
import skool.saas.skool.A_PRIMAIRE.enums.ClassePRIMAIRE;
import skool.saas.skool.GLOBALE.Entity.AnneeScolaire;

@Entity
@Table(name = "PrimaireScolarite")
public class Scolarite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long montant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClassePRIMAIRE classe;

    @ManyToOne
    private AnneeScolaire anneeScolaire;


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

    public ClassePRIMAIRE getClasse() {
        return classe;
    }

    public void setClasse(ClassePRIMAIRE classe) {
        this.classe = classe;
    }

    public AnneeScolaire getAnneeScolaire() {
        return anneeScolaire;
    }

    public void setAnneeScolaire(AnneeScolaire anneeScolaire) {
        this.anneeScolaire = anneeScolaire;
    }


}
