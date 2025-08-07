package com.eduMap.edumap.B_COLLEGE.enums;

public enum MatiereCollege {

    MATHEMATIQUES("Mathématiques"),
    PHYSIQUE_CHIMIE("Physique-Chimie"),
    SVT("SVT"),
    ANGLAIS("Anglais"),
    FRANCAIS("Français"),
    ECM("ECM"),
    HISTOIRE_GEOGRAPHIE("Histoire-Géographie"),
    EPS("EPS"),
    DESSIN("Dessin"),
    MUSIQUE("Musique");

    private final String label;

    // ✅ Constructeur correct avec le bon nom
    MatiereCollege(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
