package com.example.pidevjava.controllers;

import com.example.pidevjava.models.Evenement;
import com.example.pidevjava.services.ServiceEvenement;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.rendering.PDFRenderer;




public class BackEvenementController implements Initializable {

    @FXML
    private Button pdfBtn;


    @FXML
    private Button backBtn;


    @FXML
    private Button AjouterBtn;

    @FXML
    private TextField Budget;

    @FXML
    private DatePicker DateDebut;

    @FXML
    private DatePicker DateFin;

    @FXML
    private VBox EventVBox;

    @FXML
    private Button ImageBtn;

    @FXML
    private TextField LieuEvenement;

    @FXML
    private TextField NomEvenement;

    @FXML
    private ScrollPane Scrollpane;

    @FXML
    private Button modifierBtn;

    @FXML
    private Button suppBtn;

    @FXML
    private TextField Image;

    @FXML
    private TextField searchBar;


    @FXML
    private Button triBtn;

    @FXML
    private TextField typeEvenement;
    private final ServiceEvenement SE = new ServiceEvenement();
    private String imagePath;

    private Evenement selectedEvenement;

    private String tri="ASC";
    private int i = 0;



    private final String imageDirectory = "C:/Users/mayss/Desktop/web/PIDEV/public/uploads/";


    @FXML
    void OnClickedPdf(ActionEvent event) {
        try {
            // Créez un nouveau document PDF
            PDDocument document = new PDDocument();

            // Ajoutez une page au document
            PDPage page = new PDPage();
            document.addPage(page);

            // Créez un nouveau flux de contenu pour la page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Définissez la police et la taille du texte
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

            // Template pour le PDF
            float margin = 50;
            float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
            float yStart = page.getMediaBox().getHeight() - (2 * margin);
            float yPosition = yStart;
            float rowHeight = 20;
            float tableMargin = 10;
// Ajouter un en-tête
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
            contentStream.newLineAtOffset(margin, yStart + 20);
            contentStream.showText("Liste des événements");
            contentStream.endText();

// Ajouter un pied de page
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 10);
            contentStream.newLineAtOffset(margin, margin - 10);
            contentStream.showText("Page 1 de X"); // Remplacer X par le nombre total de pages
            contentStream.endText();

// Utiliser des couleurs et des polices différentes pour les en-têtes
            PDColor headerFontColor = new PDColor(new float[]{1, 1, 1}, PDDeviceRGB.INSTANCE);
            contentStream.setNonStrokingColor(headerFontColor);
            contentStream.setFont(PDType1Font.TIMES_BOLD, 12);

            // Définir les en-têtes du tableau
            String[] headers = {"ID", "Nom", "Type d'événement", "Image", "Lieu", "Date de début", "Date de fin", "Budget"};

            // Couleurs
            PDColor blackColor = new PDColor(new float[]{0, 0, 0}, PDDeviceRGB.INSTANCE);
            PDColor headerColor = new PDColor(new float[]{0.2f, 0.2f, 0.8f}, PDDeviceRGB.INSTANCE);
            PDColor whiteColor = new PDColor(new float[]{1, 1, 1}, PDDeviceRGB.INSTANCE);

            // Dessiner le tableau
            contentStream.setNonStrokingColor(headerColor);
            contentStream.fillRect(margin, yPosition, tableWidth, rowHeight);
            contentStream.setNonStrokingColor(whiteColor);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(margin + tableMargin, yPosition - 15);
            for (String header : headers) {
                contentStream.showText(header);
                contentStream.newLineAtOffset((tableWidth / headers.length), 0);
            }
            contentStream.endText();

            // Parcourez la liste des événements et écrivez les détails dans le PDF
            contentStream.setFont(PDType1Font.HELVETICA, 10);
            contentStream.setNonStrokingColor(blackColor);
            yPosition -= rowHeight;
            List<Evenement> evenements = SE.getAll();

            for (Evenement evenement : evenements) {
                yPosition -= rowHeight;
                contentStream.beginText();
                contentStream.newLineAtOffset(margin + tableMargin, yPosition - 15);
                contentStream.showText(String.valueOf(evenement.getId()));
                contentStream.newLineAtOffset((tableWidth / headers.length), 0);
                contentStream.showText(evenement.getNom_evenement());
                contentStream.newLineAtOffset((tableWidth / headers.length), 0);
                contentStream.showText(evenement.getType_evenement());
                contentStream.newLineAtOffset((tableWidth / headers.length), 0);
                contentStream.showText(evenement.getImage_evenement());
                contentStream.newLineAtOffset((tableWidth / headers.length), 0);
                contentStream.showText(evenement.getLieu_evenement());
                contentStream.newLineAtOffset((tableWidth / headers.length), 0);
                contentStream.showText(String.valueOf(evenement.getDate_debut()));
                contentStream.newLineAtOffset((tableWidth / headers.length), 0);
                contentStream.showText(String.valueOf(evenement.getDate_fin()));
                contentStream.newLineAtOffset((tableWidth / headers.length), 0);
                contentStream.showText(String.valueOf(evenement.getBudget()));
                contentStream.endText();
            }

            // Fermez le flux de contenu
            contentStream.close();

            // Enregistrez le document PDF
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le fichier PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                document.save(file);
            }

            // Fermez le document
            document.close();

            // Affichez un message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF généré");
            alert.setHeaderText(null);
            alert.setContentText("Le PDF a été généré avec succès !");
            alert.showAndWait();

        } catch (IOException | SQLException e) {
            // Gérez les exceptions
            e.printStackTrace();
            // Affichez un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de la génération du PDF : " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void setupSearchBarListener() {
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterEvenement(newValue);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private boolean isNomEvenementUnique(String nomEvenement) throws SQLException {
        List<Evenement> evenements = SE.getAll(); // Assuming getEvenements() returns a List<Evenement>
        for (Evenement event : evenements) {
            if (event.getNom_evenement().equals(nomEvenement)) {
                return false; // Event name is not unique
            }
        }
        return true; // Event name is unique
    }


    @FXML
    void OnClickedAjouter(ActionEvent event) {
        try {
            if (champsSontValides()) {
                String nomEvenement = NomEvenement.getText(); // Get the name of the event
                // Check if the event name is unique
                if (isNomEvenementUnique(nomEvenement)) {
                    String imagePath = Image.getText(); // Ensure imagePath is correctly retrieved
                    Evenement newEvent = new Evenement(
                            imagePath,
                            typeEvenement.getText(),
                            nomEvenement,
                            LieuEvenement.getText(),
                            DateDebut.getValue().atStartOfDay(),
                            DateFin.getValue().atStartOfDay(),
                            Integer.parseInt(Budget.getText())
                    );
                    SE.add(newEvent);

                    // Show success message
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Événement ajouté");
                    alert.setHeaderText(null);
                    alert.setContentText("L'événement a été ajouté avec succès!");
                    alert.showAndWait();

                    // Clear fields after successful addition
                    clearFields();

                    // Refresh event list
                    refreshEvents();
                } else {
                    // Show error message if event name is not unique
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setHeaderText(null);
                    alert.setContentText("Le nom de l'événement est deja exist!");
                    alert.showAndWait();
                }
            } else {
                // Show error message if fields are not valid
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs!");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            // Show error message if budget field is not a valid number
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Le budget doit être un nombre valide!");
            alert.showAndWait();
        } catch (SQLException e) {
            // Handle database errors
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de l'ajout de l'événement. Veuillez réessayer plus tard.");
            alert.showAndWait();
            e.printStackTrace(); // Log the exception for debugging
        }
    }

// Method to check if the event name is unique
// Method to check if the event name is unique

    private boolean champsSontValides() {
        return !typeEvenement.getText().isEmpty() &&
                !NomEvenement.getText().isEmpty() &&
                !LieuEvenement.getText().isEmpty() &&
                DateDebut.getValue() != null &&
                DateFin.getValue() != null &&
                !Budget.getText().isEmpty(); // Utilisez !Budget.getText().isEmpty() pour vérifier si le champ Budget n'est pas vide
    }

    private void clearFields() {
        typeEvenement.clear();
        NomEvenement.clear();
        LieuEvenement.clear();
        DateDebut.setValue(null);
        DateFin.setValue(null);
        Budget.clear();

    }

    @FXML
    void OnClickedImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        // Filtrer uniquement les fichiers d'image
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
        );
        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Récupérer le chemin de l'image sélectionnée
            imagePath = selectedFile.getName(); // Obtenir seulement le nom du fichier
            // Afficher le chemin de l'image sélectionnée dans le champ texte Image
            Image.setText(imagePath);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Evenement> events = SE.getAll();
            for (Evenement evenement : events) {
                HBox eventBox = new HBox(); // Create an HBox for each event
                Label eventLabel = new Label(
                        "Nom: " + evenement.getNom_evenement() +
                                ", Type: " + evenement.getType_evenement() +
                                ", Date Debut: " + evenement.getDate_debut().toString() +
                                ", Date fin: " + evenement.getDate_fin().toString() +
                                ", Lieu: " + evenement.getLieu_evenement() +
                                ", budget: " + evenement.getBudget()
                );
                ImageView imageView = new ImageView();
                try {
                    File imageFile = new File("C:/Users/mayss/Desktop/web/PIDEV/public/uploads/" + evenement.getImage_evenement());
                    Image image = new Image(imageFile.toURI().toString());
                    imageView.setImage(image);
                    imageView.setFitWidth(100); // Set the desired width of the image
                    imageView.setPreserveRatio(true); // Preserve the aspect ratio of the image
                } catch (Exception e) {
                    // Handle image loading errors
                    System.err.println("Erreur de chargement de l’image : " + e.getMessage());
                    // Show error message in alert dialog
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Erreur de chargement de l’image :" + e.getMessage());
                    alert.showAndWait();
                }

                eventLabel.setOnMouseClicked(event -> {
                    selectedEvenement = evenement;
                    ImageBtn.setText(selectedEvenement.getImage_evenement());

                    typeEvenement.setText(selectedEvenement.getType_evenement());
                    NomEvenement.setText(selectedEvenement.getNom_evenement());
                    DateDebut.setValue(selectedEvenement.getDate_debut().toLocalDate());
                    DateFin.setValue(selectedEvenement.getDate_fin().toLocalDate());
                    LieuEvenement.setText(selectedEvenement.getLieu_evenement());
                    Budget.setText(String.valueOf(selectedEvenement.getBudget()));

                });

                eventBox.getChildren().addAll(imageView, eventLabel); // Add ImageView and Label to the HBox
                EventVBox.getChildren().add(eventBox); // Add the HBox to the VBox
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setupSearchBarListener();
        triBtn.setOnAction(event -> {
            try {
                OnClickedTri(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        if(i % 2 == 0){
            tri = "ASC";
        }else{
            tri = "DESC";

        }
        i++;
        try {
            refreshEvents();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    private void filterEvenement(String searchText) throws SQLException {

        List<Evenement> evenements = SE.getAll();
        EventVBox.getChildren().clear();

        for (Evenement evenement : evenements) {
            if (evenement.getNom_evenement().toLowerCase().contains(searchText.toLowerCase())) {
                HBox eventBox = new HBox(); // Create an HBox for each event
                Label eventLabel = new Label("Nom: " + evenement.getNom_evenement() +
                        ", Type: " + evenement.getType_evenement() +

                        ", Date Debut: " + evenement.getDate_debut().toString() +

                        ", Date fin: " + evenement.getDate_fin().toString() +

                        ", Lieu: " + evenement.getLieu_evenement() +
                        ", budget: " + evenement.getBudget());
                ImageView imageView = new ImageView();
                try {
                    File imageFile = new File("C:/Users/mayss/Desktop/web/PIDEV/public/uploads/" + evenement.getImage_evenement());
                    Image image = new Image(imageFile.toURI().toString());
                    imageView.setImage(image);
                    imageView.setFitWidth(100); // Set the desired width of the image
                    imageView.setPreserveRatio(true); // Preserve the aspect ratio of the image
                } catch (Exception e) {
                    // Handle image loading errors
                    System.err.println("Erreur de chargement de l’image : " + e.getMessage());
                    // Show error message in alert dialog
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Erreur de chargement de l’image : " + e.getMessage());
                    alert.showAndWait();
                }

                eventLabel.setOnMouseClicked(event -> {
                    selectedEvenement = evenement;
                    ImageBtn.setText(selectedEvenement.getImage_evenement());

                    typeEvenement.setText(selectedEvenement.getType_evenement());
                    NomEvenement.setText(selectedEvenement.getNom_evenement());
                    DateDebut.setValue(selectedEvenement.getDate_debut().toLocalDate());
                    DateFin.setValue(selectedEvenement.getDate_fin().toLocalDate());
                    LieuEvenement.setText(selectedEvenement.getLieu_evenement());
                    Budget.setText(String.valueOf(selectedEvenement.getBudget()));

                });

                eventBox.getChildren().addAll(imageView, eventLabel); // Add ImageView and Label to the HBox
                EventVBox.getChildren().add(eventBox); // Add the HBox to the VBox
            }
        }
    }



    @FXML
    void OnClickedModifier(ActionEvent event) throws SQLException {
        if (selectedEvenement != null) {String nouveauNomEvenement = NomEvenement.getText();
            if (isNomEvenementUnique(nouveauNomEvenement) && !nouveauNomEvenement.equals(selectedEvenement.getNom_evenement())) {
                selectedEvenement.setImage_evenement(imagePath); // Utilisez imagePath pour définir le chemin de l'image

                selectedEvenement.setType_evenement(typeEvenement.getText());
                selectedEvenement.setNom_evenement(NomEvenement.getText());
                selectedEvenement.setDate_debut(DateDebut.getValue().atStartOfDay());
                selectedEvenement.setDate_fin(DateFin.getValue().atStartOfDay());

                selectedEvenement.setLieu_evenement(LieuEvenement.getText());
                selectedEvenement.setBudget(Double.parseDouble(Budget.getText()));


                SE.update(selectedEvenement);
                refreshEvents();


            }else {
                // Show error message if the new event name is not unique or is the same as the current name
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                if (!isNomEvenementUnique(nouveauNomEvenement)) {
                    alert.setContentText("Le nom de l'événement est deja existe! Veuillez entrer un nouveau nom d'événement différent!");
                }
                alert.showAndWait();
            }
        }
    }

    @FXML
    void OnClickedSupprimer(ActionEvent event) {
        try {
            if (selectedEvenement != null) {
                SE.delete(selectedEvenement);
                refreshEvents();
                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Événement supprimé");
                alert.setHeaderText(null);
                alert.setContentText("L'événement a été supprimé avec succès!");
                alert.showAndWait();
            } else {
                // Show error message if no event is selected
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Aucun événement sélectionné pour la suppression.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            // Handle database errors
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de la suppression de l'événement. Veuillez réessayer plus tard.");
            alert.showAndWait();
            e.printStackTrace(); // Log the exception for debugging
        }
    }


    private void refreshEvents() throws SQLException {
        List<Evenement> events = SE.afficherbyNOM(tri);

        // List<Evenement> events = SE.getAll();
        EventVBox.getChildren().clear();
        for (Evenement evenement : events) {

            Label eventLabel = new Label(
                    "Nom: " + evenement.getNom_evenement() +
                            ", Type: " + evenement.getType_evenement() +
                            ", Date Debut: "  + evenement.getDate_debut().toString() +
                            ", Date fin: "  + evenement.getDate_fin().toString() +
                            ", Lieu: " + evenement.getLieu_evenement() +
                            ", budget: " + evenement.getBudget()
                    // ",Image: " + evenement.getImage()
            );
            ImageView imageView = new ImageView();
            try {
                File imageFile = new File(imageDirectory + evenement.getImage_evenement());
                Image image = new Image(imageFile.toURI().toString());
                // Inside the try block where you load the image
                imageView.setImage(image);
                imageView.setFitWidth(100); // Set the desired width of the image
                imageView.setPreserveRatio(true); // Preserve the aspect ratio of the image


            } catch (Exception e) {
                // Handle image loading errors
                System.err.println("Erreur de chargement de l’image : " + e.getMessage());
                // Show error message in alert dialog
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Erreur de chargement de l’image :" + e.getMessage());
                alert.showAndWait();
            }

            eventLabel.setOnMouseClicked(event -> {
                selectedEvenement = evenement;
                ImageBtn.setText(selectedEvenement.getImage_evenement());

                typeEvenement.setText(selectedEvenement.getType_evenement());
                NomEvenement.setText(selectedEvenement.getNom_evenement());
                DateDebut.setValue(selectedEvenement.getDate_debut().toLocalDate());
                DateFin.setValue(selectedEvenement.getDate_fin().toLocalDate());
                LieuEvenement.setText(selectedEvenement.getLieu_evenement());
                Budget.setText(String.valueOf(selectedEvenement.getBudget()));

            });
            EventVBox.getChildren().addAll(imageView, eventLabel);

        }
    }

    @FXML
    void OnClickedBack(ActionEvent event) {
        SE.changeScreen(event,"/com/example/pidevjava/Sign_in.fxml", " Accueille ");

    }

    @FXML
    void OnClickedTri(ActionEvent event) throws SQLException {
        if(i % 2 == 0){
            tri = "ASC";
        }else{
            tri = "DESC";

        }
        i++;
        refreshEvents();
    }

}