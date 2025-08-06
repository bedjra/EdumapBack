package com.eduMap.edumap.A_PRIMAIRE.Dto;

import com.eduMap.edumap.A_PRIMAIRE.enums.ClassePRIMAIRE;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class StatPaiementPrimaireDTO {
    private ClassePRIMAIRE classe;
    private long total;
    private long nombreSolde;
    private long nombreEnCours;

    public StatPaiementPrimaireDTO(ClassePRIMAIRE classe, long total, long nombreSolde, long nombreEnCours) {
        this.classe = classe;
        this.total = total;
        this.nombreSolde = nombreSolde;
        this.nombreEnCours = nombreEnCours;
    }

    public ClassePRIMAIRE getClasse() {
        return classe;
    }

    public void setClasse(ClassePRIMAIRE classe) {
        this.classe = classe;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getNombreSolde() {
        return nombreSolde;
    }

    public void setNombreSolde(long nombreSolde) {
        this.nombreSolde = nombreSolde;
    }

    public long getNombreEnCours() {
        return nombreEnCours;
    }

    public void setNombreEnCours(long nombreEnCours) {
        this.nombreEnCours = nombreEnCours;
    }
}
