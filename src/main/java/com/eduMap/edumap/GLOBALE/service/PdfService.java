package com.eduMap.edumap.GLOBALE.service;

import com.eduMap.edumap.A_PRIMAIRE.Dto.PaiementDto;
import com.eduMap.edumap.GLOBALE.Entity.Configuration;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PdfService {

    @Autowired
    private ConfigurationService configurationService;

    public void genererRecuPaiement(PaiementDto paiement) throws Exception {
        // 📂 Dossier de sortie
        String dossier = "Recu/";
        Files.createDirectories(Paths.get(dossier));

        // 🔢 Numéro de facture formaté
        String numeroFacture = String.format("%04d", paiement.getId());

        // 📄 Nom du fichier
        String nomFichier = dossier + "recu_" + paiement.getId() + ".pdf";

        // 📌 Récupérer infos école (première config trouvée)
        List<Configuration> configs = configurationService.getAllConfigurations();
        if (configs.isEmpty()) {
            throw new IllegalStateException("Aucune configuration d'école trouvée");
        }
        Configuration config = configs.get(0);

        // 🖋 Création du document PDF
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(nomFichier));
        document.open();

        // Logo si présent
        if (config.getImage() != null) {
            Image logo = Image.getInstance(config.getImage());
            logo.scaleAbsolute(80, 80);
            document.add(logo);
        }

        // Informations école
        document.add(new Paragraph(config.getNom()));
        document.add(new Paragraph(config.getAdresse()));
        document.add(new Paragraph("Tel: " + config.getTel()));
        document.add(new Paragraph(" "));

        // Détails de la facture
        document.add(new Paragraph("FACTURE N°: " + numeroFacture));
        document.add(new Paragraph("Date: " + paiement.getDatePaiement()));
        document.add(new Paragraph(" "));

        // Infos élève
        document.add(new Paragraph("Nom élève: " + paiement.getEleveNom() + " " + paiement.getElevePrenom()));
        document.add(new Paragraph("Classe: " + paiement.getClasse()));
        document.add(new Paragraph("Montant payé: " + paiement.getMontantActuel() + " " + config.getDevise()));
        document.add(new Paragraph("Montant scolarité: " + paiement.getMontantScolarite() + " " + config.getDevise()));
        document.add(new Paragraph("Reste à payer: " + paiement.getResteEcolage() + " " + config.getDevise()));
        document.add(new Paragraph("Statut: " + paiement.getStatut()));
        document.add(new Paragraph(" "));

        document.add(new Paragraph("Signature: ___________________"));

        document.close();
    }
}
