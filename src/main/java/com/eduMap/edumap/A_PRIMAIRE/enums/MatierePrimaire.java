package skool.saas.skool.A_PRIMAIRE.enums;

public enum MatierePrimaire {
    LECTURE("Lecture"),
    ECRITURE("Écriture"),
    CALCUL("Calcul"),
    DESSIN("Dessin"),
    MUSIQUE("Musique");

    private final String label;

    MatierePrimaire(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }



}
