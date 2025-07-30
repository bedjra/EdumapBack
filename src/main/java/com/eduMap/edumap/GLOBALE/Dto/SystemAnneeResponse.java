package com.eduMap.edumap.GLOBALE.Dto;

public class SystemAnneeResponse {

    private System systeme;
    private String anneeLibelle;

    public SystemAnneeResponse(System systeme, String anneeLibelle) {
        this.systeme = systeme;
        this.anneeLibelle = anneeLibelle;
    }

    public System getSysteme() {
        return systeme;
    }

    public void setSysteme(System systeme) {
        this.systeme = systeme;
    }

    public String getAnneeLibelle() {
        return anneeLibelle;
    }

    public void setAnneeLibelle(String anneeLibelle) {
        this.anneeLibelle = anneeLibelle;
    }
}