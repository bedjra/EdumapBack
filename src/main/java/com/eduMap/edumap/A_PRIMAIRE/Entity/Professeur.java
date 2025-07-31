package skool.saas.skool.A_PRIMAIRE.Entity;

import jakarta.persistence.*;
import skool.saas.skool.A_PRIMAIRE.enums.ClassePRIMAIRE;
@Entity
@Table(name = "PrimaireProfesseur")
public class Professeur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String adresse;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClassePRIMAIRE classe;

    @Column(nullable = false)
    private String diplome;

    @Column(nullable = false)
    private String telephone;

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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public ClassePRIMAIRE getClasse() {
        return classe;
    }

    public void setClasse(ClassePRIMAIRE classe) {
        this.classe = classe;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
