package skool.saas.skool.A_PRIMAIRE.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "PrimaireMatiere")

public class Matiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;


    public Matiere() {}


    // ✅ Getter pour id
    public Long getId() {
        return id;
    }

    // ✅ Getter et setter pour nom (si tu ne les as pas)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

