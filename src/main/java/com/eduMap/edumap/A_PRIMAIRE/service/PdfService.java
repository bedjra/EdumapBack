package com.eduMap.edumap.A_PRIMAIRE.service;

import com.eduMap.edumap.A_PRIMAIRE.Dto.PaiementDto;
import com.eduMap.edumap.GLOBALE.Entity.Configuration;
import com.eduMap.edumap.GLOBALE.service.ConfigurationService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PdfService {

    @Autowired
    private ConfigurationService configurationService;


    public void genererRecuPaiement(PaiementDto paiement) throws Exception {
        String dossier = "Recu/";
        Files.createDirectories(Paths.get(dossier));

        String numeroFacture = String.format("%04d", paiement.getId());
        String nomFichier = dossier + "recu_" + paiement.getId() + ".pdf";

        List<Configuration> configs = configurationService.getAllConfigurations();
        if (configs.isEmpty()) {
            throw new IllegalStateException("Aucune configuration trouvée");
        }
        Configuration config = configs.get(0);

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(nomFichier));
        document.open();

        // -------- EN-TÊTE --------
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new int[]{1, 3});

        // Logo
        if (config.getImage() != null) {
            Image logo = Image.getInstance(config.getImage());
            logo.scaleAbsolute(80, 80);
            PdfPCell logoCell = new PdfPCell(logo);
            logoCell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(logoCell);
        } else {
            PdfPCell emptyCell = new PdfPCell(new Phrase(""));
            emptyCell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(emptyCell);
        }

        // Infos établissement
        PdfPCell infoCell = new PdfPCell();
        infoCell.addElement(new Paragraph(config.getNom(), new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD)));
        infoCell.addElement(new Paragraph("BP: " + config.getBp()));
        infoCell.addElement(new Paragraph("Tel: " + config.getTel() + " / Cel: " + config.getCel()));
        infoCell.addElement(new Paragraph("Devise: " + config.getDevise()));
        infoCell.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(infoCell);

        document.add(headerTable);

        document.add(new Paragraph(" "));

        // -------- DATE & FACTURE --------
        PdfPTable factureTable = new PdfPTable(2);
        factureTable.setWidthPercentage(100);
        factureTable.setWidths(new int[]{1, 1});

        factureTable.addCell(getCell("Date: " + paiement.getDatePaiement(), PdfPCell.ALIGN_LEFT));
        factureTable.addCell(getCell("FACTURE N°: " + numeroFacture, PdfPCell.ALIGN_RIGHT));

        document.add(factureTable);

        // -------- LIGNE DE SÉPARATION --------
        document.add(new LineSeparator());

        document.add(new Paragraph(" "));

        // -------- TABLEAU PAIEMENT --------
        PdfPTable paiementTable = new PdfPTable(2);
        paiementTable.setWidthPercentage(100);

        paiementTable.addCell(getCell("Nom élève:", PdfPCell.ALIGN_LEFT));
        paiementTable.addCell(getCell(paiement.getEleveNom() + " " + paiement.getElevePrenom(), PdfPCell.ALIGN_LEFT));

        paiementTable.addCell(getCell("Classe:", PdfPCell.ALIGN_LEFT));
        paiementTable.addCell(getCell(String.valueOf(paiement.getClasse()), PdfPCell.ALIGN_LEFT));

        paiementTable.addCell(getCell("Montant payé:", PdfPCell.ALIGN_LEFT));
        paiementTable.addCell(getCell(paiement.getMontantActuel() + " " + config.getDevise(), PdfPCell.ALIGN_LEFT));

        paiementTable.addCell(getCell("Montant scolarité:", PdfPCell.ALIGN_LEFT));
        paiementTable.addCell(getCell(paiement.getMontantScolarite() + " " + config.getDevise(), PdfPCell.ALIGN_LEFT));

        paiementTable.addCell(getCell("Reste à payer:", PdfPCell.ALIGN_LEFT));
        paiementTable.addCell(getCell(paiement.getResteEcolage() + " " + config.getDevise(), PdfPCell.ALIGN_LEFT));


        document.add(paiementTable);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Signature: ___________________"));

        document.close();
    }

    private PdfPCell getCell(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }



}

