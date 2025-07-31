package skool.saas.skool.A_PRIMAIRE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skool.saas.skool.A_PRIMAIRE.Dto.NoteDto;
import skool.saas.skool.A_PRIMAIRE.Entity.Eleve;
import skool.saas.skool.A_PRIMAIRE.Entity.Matiere;
import skool.saas.skool.A_PRIMAIRE.Entity.Note;
import skool.saas.skool.A_PRIMAIRE.enums.EvaluationPrimaire;
import skool.saas.skool.A_PRIMAIRE.enums.MatierePrimaire;
import skool.saas.skool.A_PRIMAIRE.repository.EleveRepository;
import skool.saas.skool.A_PRIMAIRE.repository.MatiereRepository;
import skool.saas.skool.A_PRIMAIRE.repository.NoteRepository;
import skool.saas.skool.GLOBALE.Entity.AnneeContext;
import skool.saas.skool.GLOBALE.Entity.AnneeScolaire;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
public class NoteService {

    @Autowired
    private EleveRepository eleveRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private MatiereRepository matiereRepository;


    public void mettreAJourNotes(NoteDto dto) {
        Eleve eleve = eleveRepository.findById(dto.getEleveId())
                .orElseThrow(() -> new RuntimeException("Élève introuvable"));
        AnneeScolaire annee = AnneeContext.get();

        for (NoteDto.MatiereNote noteDto : dto.getNotes()) {
            Note note = null;

            if (noteDto.getMatiereId() != null) {
                Matiere matiere = matiereRepository.findById(noteDto.getMatiereId())
                        .orElseThrow(() -> new RuntimeException("Matière personnalisée introuvable"));

                note = noteRepository.findByEleveAndClasseAndAnneeScolaireAndMatiereAndEvaluationPrimaire(
                        eleve, dto.getClasse(), annee, matiere,
                        EvaluationPrimaire.valueOf(dto.getEvaluation())
                ).orElse(null);

                if (note == null) {
                    note = new Note();
                    note.setMatiere(matiere);
                }

            } else if (noteDto.getMatierePrimaire() != null) {
                MatierePrimaire matiereEnum = MatierePrimaire.valueOf(noteDto.getMatierePrimaire());

                note = noteRepository.findByEleveAndClasseAndAnneeScolaireAndMatierePrimaireAndEvaluationPrimaire(
                        eleve, dto.getClasse(), annee, matiereEnum,
                        EvaluationPrimaire.valueOf(dto.getEvaluation())
                ).orElse(null);

                if (note == null) {
                    note = new Note();
                    note.setMatierePrimaire(matiereEnum);
                }

            } else {
                throw new RuntimeException("Aucune matière spécifiée.");
            }

            // mise à jour ou création dans les deux cas
            note.setEleve(eleve);
            note.setAnneeScolaire(annee);
            note.setClasse(dto.getClasse());
            note.setValeurNote(noteDto.getValeurNote());
            note.setEvaluationPrimaire(EvaluationPrimaire.valueOf(dto.getEvaluation()));

            noteRepository.save(note);
        }
    }


    public List<NoteDto> getAllNotesAsDto() {
        List<Note> notes = noteRepository.findAll();

        // Grouper les notes par élève + classe + évaluation + année
        Map<String, NoteDto> regroupement = new LinkedHashMap<>();

        for (Note note : notes) {
            String key = note.getEleve().getId() + "|" + note.getClasse() + "|" +
                    note.getEvaluationPrimaire() + "|" + note.getAnneeScolaire().getId();

            NoteDto dto = regroupement.computeIfAbsent(key, k -> {
                NoteDto nd = new NoteDto();
                nd.setEleveId(note.getEleve().getId());
                nd.setClasse(note.getClasse());
                nd.setEvaluation(note.getEvaluationPrimaire().name());
                nd.setAnneeScolaireId(note.getAnneeScolaire().getId());
                nd.setNotes(new ArrayList<>());
                return nd;
            });

            NoteDto.MatiereNote matiereNote = new NoteDto.MatiereNote();
            matiereNote.setValeurNote(note.getValeurNote());

            if (note.getMatiere() != null) {
                matiereNote.setMatiereId(note.getMatiere().getId());
            } else if (note.getMatierePrimaire() != null) {
                matiereNote.setMatierePrimaire(note.getMatierePrimaire().name());
            }

            dto.getNotes().add(matiereNote);
        }

        return new ArrayList<>(regroupement.values());
    }


}

