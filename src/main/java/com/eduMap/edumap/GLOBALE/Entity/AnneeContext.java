package com.eduMap.edumap.GLOBALE.Entity;

public class AnneeContext {

    private static final ThreadLocal<AnneeScolaire> anneeCourante = new ThreadLocal<>();

    public static void set(AnneeScolaire annee) {
        anneeCourante.set(annee);
    }

    public static AnneeScolaire get() {
        return anneeCourante.get();
    }

    public static void clear() {
        anneeCourante.remove();
    }
}
