package com.eduMap.edumap.A_PRIMAIRE.enums;

public enum EvaluationPrimaire {
    OCTOBRE("Octobre"),
    NOVEMBRE("Novembre"),
    TRIMESTRE1("Trimestre1"),
    JANVIER("Janvier"),
    FEVRIER("Février"),
    TRIMESTRE2("Trimestre2"),
    AVRIL("Avril"),
    MAI("Mai"),
    TRIMESTRE3("Trimestre3");

    private final String label;

    EvaluationPrimaire(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

