package skool.saas.skool.A_PRIMAIRE.enums;


public enum ClassePRIMAIRE {
    CI("01"),
    CP1("02"),
    CP2("03"),
    CE1("04"),
    CE2("05"),
    CM1("06"),
    CM2("07");

    private final String code;

    ClassePRIMAIRE(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
