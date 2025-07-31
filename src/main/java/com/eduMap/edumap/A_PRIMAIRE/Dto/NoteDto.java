package skool.saas.skool.A_PRIMAIRE.Dto;

import skool.saas.skool.A_PRIMAIRE.enums.ClassePRIMAIRE;

import java.util.List;

public class NoteDto {
    private Long eleveId;
    private String evaluation;
    private ClassePRIMAIRE classe;
    private Long anneeScolaireId;
    private List<MatiereNote> notes;

    public static class MatiereNote {
        private Long matiereId;
        private Double valeurNote;
        private String matierePrimaire; // ✅ ce champ manquait

        public Long getMatiereId() {
            return matiereId;
        }

        public void setMatiereId(Long matiereId) {
            this.matiereId = matiereId;
        }

        public Double getValeurNote() {
            return valeurNote;
        }

        public void setValeurNote(Double valeurNote) {
            this.valeurNote = valeurNote;
        }

        public String getMatierePrimaire() {
            return matierePrimaire;
        }

        public void setMatierePrimaire(String matierePrimaire) {
            this.matierePrimaire = matierePrimaire;
        }
    }

    // (getters et setters pour les autres champs déjà présents)


    public Long getEleveId() {
        return eleveId;
    }

    public void setEleveId(Long eleveId) {
        this.eleveId = eleveId;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public ClassePRIMAIRE getClasse() {
        return classe;
    }

    public void setClasse(ClassePRIMAIRE classe) {
        this.classe = classe;
    }

    public Long getAnneeScolaireId() {
        return anneeScolaireId;
    }

    public void setAnneeScolaireId(Long anneeScolaireId) {
        this.anneeScolaireId = anneeScolaireId;
    }

    public List<MatiereNote> getNotes() {
        return notes;
    }

    public void setNotes(List<MatiereNote> notes) {
        this.notes = notes;
    }
}
