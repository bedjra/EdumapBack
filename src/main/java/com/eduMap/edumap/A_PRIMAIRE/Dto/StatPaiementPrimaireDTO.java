package com.eduMap.edumap.A_PRIMAIRE.Dto;

import com.eduMap.edumap.A_PRIMAIRE.enums.ClassePRIMAIRE;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatPaiementPrimaireDTO {
    private ClassePRIMAIRE classe;
    private long total;
    private long nombreSolde;
    private long nombreEnCours;
}
