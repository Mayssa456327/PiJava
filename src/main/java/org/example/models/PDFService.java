package org.example.models;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;

public class PDFService {

    public static void genererPDF(String nom_patient, String date_debut, String date_fin, String email, String hopitalNom, String cheminFichier) {
        try {
            // Créez un document PDF
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Créez un nouveau contenu pour la page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Ajoutez le logo centré sur la page
            File file = new File("src/main/resources/logo.png");
            PDImageXObject logo = PDImageXObject.createFromFileByContent(file, document);
            float logoWidth = logo.getWidth() / 4;
            float logoHeight = logo.getHeight() / 4;
            float centerX = (page.getMediaBox().getWidth() - logoWidth) / 2;
            float centerY = page.getMediaBox().getHeight() - 100 - logoHeight; // Ajustez la position verticale du logo
            contentStream.drawImage(logo, centerX, centerY, logoWidth, logoHeight);

            // Ajoutez le titre centré sur la page
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
            contentStream.beginText();
            contentStream.newLineAtOffset((page.getMediaBox().getWidth() - 100) / 2, centerY - 50);
            contentStream.showText("Reservation");
            contentStream.endText();

            // Dessiner une ligne de séparation
            float separationLineY = centerY - 100; // Ajustez la position verticale de la ligne de séparation
            contentStream.moveTo(50, separationLineY);
            contentStream.lineTo(page.getMediaBox().getWidth() - 50, separationLineY);
            contentStream.stroke();

            // Ajoutez des lignes de texte avec les informations nécessaires
            float tableStartY = centerY - 150;
            float lineHeight = 20;
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, tableStartY);
            contentStream.showText("Nom Patient:");
            contentStream.newLineAtOffset(150, 0);
            contentStream.showText(nom_patient);
            contentStream.newLineAtOffset(-150, -lineHeight);
            contentStream.showText("Date de Début:");
            contentStream.newLineAtOffset(150, 0);
            contentStream.showText(date_debut);
            contentStream.newLineAtOffset(-150, -lineHeight);
            contentStream.showText("Date de Fin:");
            contentStream.newLineAtOffset(150, 0);
            contentStream.showText(date_fin);
            contentStream.newLineAtOffset(-150, -lineHeight);
            contentStream.showText("Email:");
            contentStream.newLineAtOffset(150, 0);
            contentStream.showText(email);
            contentStream.newLineAtOffset(-150, -lineHeight);
            contentStream.showText("Hopital Name:");
            contentStream.newLineAtOffset(150, 0);
            contentStream.showText(hopitalNom);
            contentStream.endText();
            // Fermez le contenu de la page
            contentStream.close();

            // Enregistrez le document PDF
            document.save(cheminFichier);

            // Fermez le document
            document.close();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les exceptions liées à la génération du PDF
        }
    }
}
