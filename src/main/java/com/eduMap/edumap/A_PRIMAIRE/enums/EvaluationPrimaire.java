package skool.saas.skool.A_PRIMAIRE.enums;

public enum EvaluationPrimaire {
    MOIS_1("Mois 1"),
    MOIS_2("Mois 2"),
    TRIMESTRE_1("Trimestre 1"),
    MOIS_4("Mois 4"),
    MOIS_5("Mois 5"),
    TRIMESTRE_2("Trimestre 2"),
    MOIS_7("Mois 7"),
    MOIS_8("Mois 8"),
    TRIMESTRE_3("Trimestre 3");

    private final String label;

    EvaluationPrimaire(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

