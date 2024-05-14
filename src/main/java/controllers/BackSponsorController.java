
package controllers;


import entities.Evenement;
import entities.Sponsor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import services.ServiceEvenement3;
import services.ServiceSponsor3;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class BackSponsorController implements Initializable {

    @FXML
    private Button backBtn;

    @FXML
    private VBox SponsorVBox;

    @FXML
    private TextField adresse;

    @FXML
    private TextField budget;

    @FXML
    private TextField email_sponsor;

    @FXML
    private ChoiceBox<String>evenement_id;

    @FXML
    private Button modiferBtn;

    @FXML
    private TextField nom_sponsor;

    @FXML
    private Button suppBtn;

    @FXML
    private PieChart pieChart;

    private final ServiceSponsor3 SS = new ServiceSponsor3();
    private final ServiceEvenement3 SE = new ServiceEvenement3();

    private ServiceSponsor3 serviceSponsor;
    private ServiceEvenement3 serviceEvenement;
    private Sponsor selectedSponsor;
    private Evenement selectedEvenement;

    @FXML
    private Button statBtn;

    @FXML
    void OnClickedBack(ActionEvent event) {
        SE.changeScreen(event, "/admin.fxml", " Accueil ");
    }

    @FXML
    public void OnClickPDF() {
        try {
            // Create a new PDF document
            PDDocument document = new PDDocument();

            // Add a page to the document
            PDPage page = new PDPage();
            document.addPage(page);

            // Create a new content stream for the page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Set the font and text size
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

            // PDF template
            float margin = 50;
            float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
            float yStart = page.getMediaBox().getHeight() - (2 * margin);
            float yPosition = yStart;
            float rowHeight = 10; // Adjusted row height
            float tableMargin = 10;

            // Title
            String title = "Liste des événements";

            // Center the title horizontally
            float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(title) / 1000 * 14;
            float titlePosition = (page.getMediaBox().getWidth() - titleWidth) / 2;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
            contentStream.newLineAtOffset(titlePosition, yStart + 10);
            contentStream.showText(title);
            contentStream.endText();

            // Add the logo at the top of the page
            float centerY = 0;
            try {
                File file = new File("C:/Users/mayss/Desktop/PIDEV/public/uploads/logo.jpg");
                PDImageXObject logo = PDImageXObject.createFromFileByContent(file, document);
                float logoWidth = logo.getWidth() / 10; // Adjust logo size
                float logoHeight = logo.getHeight() / 10; // Adjust logo size
                float centerX = (page.getMediaBox().getWidth() - logoWidth) / 2;
                centerY = yStart - logoHeight - 20;
                contentStream.drawImage(logo, centerX, centerY, logoWidth, logoHeight);

                // Draw line below the logo
                contentStream.setStrokingColor(Color.BLUE);
                contentStream.setLineWidth(1f);
                contentStream.moveTo(margin, centerY - 10); // Adjusted for logo height and spacing
                contentStream.lineTo(margin + tableWidth, centerY - 10); // Adjusted for logo height and spacing
                contentStream.stroke();

            } catch (IOException ex) {
                // Handle image loading error
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur est survenue lors du chargement de l'image : " + ex.getMessage());
                alert.showAndWait();
            }

            // Table headers
            String[] headers = {
                    "Nom",
                    "email",
                    "adresse",
                    "Nom d'événement",
                    "Budget"
            };

            // Display headers
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
            yPosition = centerY - 30; // Adjust position below the line
            for (int i = 0; i < headers.length; i++) {
                float headerWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(headers[i]) / 1000 * 10;
                float headerPosition = margin + tableMargin + (tableWidth / headers.length) * i + ((tableWidth / headers.length) - headerWidth) / 2;
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(headerPosition, yPosition - 10);
                contentStream.showText(headers[i]);
                contentStream.endText();
            }

            // Iterate over the list of sponsors and write details to the PDF
            contentStream.setFont(PDType1Font.HELVETICA, 10);
            List<Sponsor> sponsors = SS.getAll();
            for (Sponsor sponsor : sponsors) {
                yPosition -= rowHeight;
                contentStream.beginText();
                contentStream.newLineAtOffset(margin + tableMargin, yPosition - 10);

                // Add sponsor details to the table
                String[] details = {
                        sponsor.getNom_sponsor(),
                        //sponsor.getType_evenement(), // Modify the method name according to the Sponsor class
                        sponsor.getEmail_sponsor(),
                        sponsor.getAdresse(),
                        String.valueOf(sponsor.getBudget())
                };

                // Display details in each column
                for (int i = 0; i < details.length; i++) {
                    float detailWidth = PDType1Font.HELVETICA.getStringWidth(details[i]) / 1000 * 10;
                    float detailPosition = margin + tableMargin + (tableWidth / headers.length) * i + ((tableWidth / headers.length) - detailWidth) / 2;
                    contentStream.showText(details[i]);
                    contentStream.newLineAtOffset(detailPosition, 0);
                }

                contentStream.endText();
            }

            contentStream.close();

            // Save the PDF document
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le fichier PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
            File saveFile = fileChooser.showSaveDialog(null);
            if (saveFile != null) {
                document.save(saveFile);
            }
            document.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF généré");
            alert.setHeaderText(null);
            alert.setContentText("Le PDF a été généré avec succès !");
            alert.showAndWait();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de la génération du PDF : " + e.getMessage());
            alert.showAndWait();
        }
    }





    @FXML
    void OnClickedModifierSponsor(ActionEvent event) throws SQLException {
        if (selectedSponsor != null) {
            String budgetText = budget.getText();
            Integer eventId = SE.getEventIdByName(evenement_id.getValue());
            String nomSponsorText = nom_sponsor.getText();
            String emailSponsorText = email_sponsor.getText();
            String adresseText = adresse.getText();

            if (budgetText.isEmpty() || eventId == null || nomSponsorText.isEmpty() ||
                    emailSponsorText.isEmpty() || adresseText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis.");
                return;
            }

            if (!emailSponsorText.toLowerCase().endsWith("@gmail.com")) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "L’e-mail doit se terminer par @gmail.com");
                return;
            }

            try {
                selectedSponsor.setBudget(Double.parseDouble(budgetText));
                selectedSponsor.setEvenement_id(eventId);
                selectedSponsor.setNom_sponsor(nomSponsorText);
                selectedSponsor.setEmail_sponsor(emailSponsorText);
                selectedSponsor.setAdresse(adresseText);
                serviceSponsor.update(selectedSponsor);
                loadReservations();
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Budget invalide.");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la mise à jour du sponsor.");
            }
        }
    }


    @FXML
    void OnClcickedStatSponsor(ActionEvent event) {
        generateStatistics();
    }

    @FXML
    void OnClickedSupprimerSponsor(ActionEvent event) {
        if (selectedSponsor != null) {
            try {
                serviceSponsor.delete(selectedSponsor);
                loadReservations();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression du sponsor.");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceSponsor = new ServiceSponsor3();
        loadEventNames();
        loadReservations();
        generateStatistics();

        budget.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                budget.setText(oldValue);
            }
        });
    }

    private void loadEventNames() {
        try {
            List<String> eventNames = SE.getAllEventNames();
            evenement_id.setItems(FXCollections.observableArrayList(eventNames));
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des noms d'événement.");
        }
    }

    private void generateStatistics() {
        pieChart.getData().clear();
        try {
            List<Sponsor> sponsors = SS.getAll();
            sponsors.sort(Comparator.comparing(Sponsor::getNom_sponsor));
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            double totalBudget = sponsors.stream().mapToDouble(Sponsor::getBudget).sum();
            for (Sponsor sponsor : sponsors) {
                double percentage = (sponsor.getBudget() / totalBudget) * 100;
                PieChart.Data slice = new PieChart.Data(sponsor.getNom_sponsor(), sponsor.getBudget());
                slice.nodeProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        newValue.setOnMouseClicked(event -> showAlert(Alert.AlertType.INFORMATION, "Pourcentage",
                                String.format("%.2f%%", percentage)));
                    }
                });
                pieChartData.add(slice);
            }
            pieChart.setData(pieChartData);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert1(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la génération des statistiques.");
        }
    }
    private void addPercentageLabel(PieChart.Data slice, String percentage) {
        Tooltip tooltip = new Tooltip(percentage);
        Tooltip.install(slice.getNode(), tooltip);
    }


    private void showAlert1(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void loadReservations() {
        SponsorVBox.getChildren().clear();
        try {
            List<Sponsor> sponsors = SS.getAll();
            for (Sponsor sponsor : sponsors) {
                VBox sponsorContainer = new VBox();
                sponsorContainer.setStyle("-fx-background-color: #f2f2f2; -fx-padding: 10px; -fx-spacing: 5px;");

                GridPane gridPane = new GridPane();
                gridPane.setStyle("-fx-padding: 5px;");
                gridPane.setVgap(5);
                gridPane.setHgap(10);

                Label nomLabel = new Label("Nom:");
                Label nomValue = new Label(sponsor.getNom_sponsor());
                Label evenementLabel = new Label("Nom de l'événement:");
                Label evenementValue = new Label(SE.getNomEvenementById(sponsor.getEvenement_id()));
                Label emailLabel = new Label("Email:");
                Label emailValue = new Label(sponsor.getEmail_sponsor());
                Label adresseLabel = new Label("Adresse:");
                Label adresseValue = new Label(sponsor.getAdresse());
                Label budgetLabel = new Label("Budget:");
                Label budgetValue = new Label(String.valueOf(sponsor.getBudget()));

                gridPane.addRow(0, nomLabel, nomValue);
                gridPane.addRow(1, evenementLabel, evenementValue);
                gridPane.addRow(2, emailLabel, emailValue);
                gridPane.addRow(3, adresseLabel, adresseValue);
                gridPane.addRow(4, budgetLabel, budgetValue);

                Button selectButton = new Button("Sélectionner");
                selectButton.setOnAction(event -> {
                    selectedSponsor = sponsor;
                    budget.setText(String.valueOf(sponsor.getBudget()));
                    evenement_id.setValue(String.valueOf(sponsor.getEvenement_id()));
                    nom_sponsor.setText(sponsor.getNom_sponsor());
                    email_sponsor.setText(sponsor.getEmail_sponsor());
                    adresse.setText(sponsor.getAdresse());
                });
                selectButton.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white;");
                sponsorContainer.getChildren().addAll(gridPane, selectButton);
                SponsorVBox.getChildren().add(sponsorContainer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des sponsors.");
        }

    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
