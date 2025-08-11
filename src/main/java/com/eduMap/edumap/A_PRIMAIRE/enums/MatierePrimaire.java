package com.eduMap.edumap.A_PRIMAIRE.enums;

public enum MatierePrimaire {
    Lecture("Lecture"),
    Ecriture("Ecriture"),
    Calcul("Calcul"),
    Dessin("Dessin"),
    Musique("Musique");

    private final String label;

    MatierePrimaire(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }



}
