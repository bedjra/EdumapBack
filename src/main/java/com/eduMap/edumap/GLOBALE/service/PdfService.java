package com.eduMap.edumap.GLOBALE.service;

import com.eduMap.edumap.A_PRIMAIRE.Dto.PaiementDto;
import com.eduMap.edumap.GLOBALE.Entity.Configuration;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
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
    private ConfigurationService configurationService; // Service injecté par Spring
    public void genererRecuPaiement(PaiementDto paiement) throws Exception {
        String dossier = "Recu/";
        Files.createDirectories(Paths.get(dossier));

        // Si l'ID n'est pas encore défini, on en génère un provisoire
        Long idFacture = (paiement.getId() != null) ? paiement.getId() : System.currentTimeMillis();
        String numeroFacture = String.format("%04d", idFacture);
        String nomFichier = dossier + "recu_" + idFacture + ".pdf";

        // Récupération des infos école
        List<Configuration> configs = configurationService.getAllConfigurations();
        if (configs.isEmpty()) {
            throw new IllegalStateException("Aucune configuration trouvée");
        }
        Configuration config = configs.get(0);

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(nomFichier));
        document.open();

        // ==== 1. En-tête avec logo et infos école ====
        PdfPTable tableHeader = new PdfPTable(2); // 2 colonnes : logo + infos
        tableHeader.setWidthPercentage(100);
        tableHeader.setWidths(new float[]{1.5f, 4f}); // Largeur colonnes

        // Colonne 1 : Logo
        if (config.getImage() != null) {
            Image logo = Image.getInstance(config.getImage());
            logo.scaleAbsolute(80, 80);
            PdfPCell cellLogo = new PdfPCell(logo, false);
            cellLogo.setBorder(Rectangle.NO_BORDER);
            tableHeader.addCell(cellLogo);
        } else {
            PdfPCell cellEmpty = new PdfPCell(new Paragraph(""));
            cellEmpty.setBorder(Rectangle.NO_BORDER);
            tableHeader.addCell(cellEmpty);
        }

        // Colonne 2 : Infos établissement
        PdfPCell cellInfos = new PdfPCell();
        cellInfos.addElement(new Paragraph(config.getNom()));
        cellInfos.addElement(new Paragraph("BP: " + config.getBp()));
        cellInfos.addElement(new Paragraph("Tel: " + config.getTel() + " / " + config.getCel()));
        cellInfos.addElement(new Paragraph(config.getAdresse()));
        cellInfos.setBorder(Rectangle.NO_BORDER);
        tableHeader.addCell(cellInfos);

        document.add(tableHeader);

        document.add(new Paragraph(" "));

        // ==== 2. Date et numéro facture ====
        PdfPTable tableDateFacture = new PdfPTable(2);
        tableDateFacture.setWidthPercentage(100);
        tableDateFacture.setWidths(new float[]{2f, 1.5f});
        tableDateFacture.addCell(getCell("Date : " + paiement.getDatePaiement(), PdfPCell.ALIGN_LEFT));
        tableDateFacture.addCell(getCell("FACTURE N° : " + numeroFacture, PdfPCell.ALIGN_RIGHT));
        document.add(tableDateFacture);

        // ==== 3. Ligne horizontale ====
        Paragraph line = new Paragraph(" ");
        line.setSpacingAfter(5);
        document.add(line);
        document.add(new Paragraph("--------------------------------------------------------------"));
        document.add(new Paragraph(" "));

        // ==== 4. Tableau infos paiement ====
        PdfPTable tablePaiement = new PdfPTable(2);
        tablePaiement.setWidthPercentage(100);
        tablePaiement.setWidths(new float[]{2.5f, 2f});

        tablePaiement.addCell(getCell("Nom élève :", PdfPCell.ALIGN_LEFT));
        tablePaiement.addCell(getCell(paiement.getEleveNom() + " " + paiement.getElevePrenom(), PdfPCell.ALIGN_LEFT));


        tablePaiement.addCell(getCell("Montant payé :", PdfPCell.ALIGN_LEFT));

        tablePaiement.addCell(getCell("Montant scolarité :", PdfPCell.ALIGN_LEFT));
        tablePaiement.addCell(getCell(paiement.getMontantScolarite() + " " + config.getDevise(), PdfPCell.ALIGN_LEFT));

        tablePaiement.addCell(getCell("Reste à payer :", PdfPCell.ALIGN_LEFT));
        tablePaiement.addCell(getCell(paiement.getResteEcolage() + " " + config.getDevise(), PdfPCell.ALIGN_LEFT));

        tablePaiement.addCell(getCell("Statut :", PdfPCell.ALIGN_LEFT));

        document.add(tablePaiement);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Signature : ___________________"));

        document.close();
    }

    // Méthode utilitaire pour créer une cellule propre
    private PdfPCell getCell(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

}

