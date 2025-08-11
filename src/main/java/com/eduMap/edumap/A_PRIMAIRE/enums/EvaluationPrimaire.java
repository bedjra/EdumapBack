package com.eduMap.edumap.A_PRIMAIRE.enums;

public enum EvaluationPrimaire {
    OCTOBRE("Octobre"),
    NOVEMBRE("Novembre"),
    TRIMESTRE_1("Trimestre 1"),
    JANVIER("Janvier"),
    FEVRIER("Février"),
    TRIMESTRE_2("Trimestre 2"),
    AVRIL("Avril"),
    MAI("Mai"),
    TRIMESTRE_3("Trimestre 3");

    private final String label;

    EvaluationPrimaire(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

