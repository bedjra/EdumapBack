package com.eduMap.edumap.GLOBALE.enums;

public enum System {
    PRIMAIRE("PRI"),
    COLLEGE("COL"),
    LYCEE("LYC");

    private final String code;

    System(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
