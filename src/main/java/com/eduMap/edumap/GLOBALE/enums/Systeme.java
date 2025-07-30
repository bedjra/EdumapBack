package com.eduMap.edumap.GLOBALE.enums;

public enum Systeme {
    PRIMAIRE("PRI"),
    COLLEGE("COL"),
    LYCEE("LYC");

    private final String code;

    Systeme(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
