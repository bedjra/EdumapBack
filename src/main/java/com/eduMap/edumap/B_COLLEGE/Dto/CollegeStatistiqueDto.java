package com.eduMap.edumap.B_COLLEGE.Dto;

public class CollegeStatistiqueDto {
    private String classe;
    private long total;
    private long garcons;
    private long filles;

    public CollegeStatistiqueDto(String classe, long total, long garcons, long filles) {
        this.classe = classe;
        this.total = total;
        this.garcons = garcons;
        this.filles = filles;
    }

    // Getters/setters ou @Data si tu utilises Lombok

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getGarcons() {
        return garcons;
    }

    public void setGarcons(long garcons) {
        this.garcons = garcons;
    }

    public long getFilles() {
        return filles;
    }

    public void setFilles(long filles) {
        this.filles = filles;
    }
}
