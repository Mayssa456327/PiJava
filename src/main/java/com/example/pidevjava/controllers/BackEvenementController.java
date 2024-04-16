package com.example.pidevjava.controllers;

import com.example.pidevjava.models.Evenement;
import com.example.pidevjava.services.ServiceEvenement;
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
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class BackEvenementController implements Initializable {

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
    private TextField typeEvenement;
    private final ServiceEvenement SE = new ServiceEvenement();
    private String imagePath;

    private Evenement selectedEvenement;

    private final String imageDirectory = "C:/Users/mayss/Desktop/web/PIDEV/public/uploads/";

    private void setupSearchBarListener() {
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterEvenement(newValue);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    void OnClickedAjouter(ActionEvent event) {
        try {
            if (champsSontValides()) {
                String imagePath = Image.getText(); // Ensure imagePath is correctly retrieved
                Evenement newEvent = new Evenement(
                        imagePath,
                        typeEvenement.getText(),
                        NomEvenement.getText(),
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


  /*  private void filterEvenement(String searchText) throws SQLException {

        List<Evenement> evenements = SE.getAll();
        EventVBox.getChildren().clear();

        for (Evenement evenement : evenements) {
            // Vérifiez si le nom de la réservation contient le texte de recherche
            if (evenement.getNom_evenement().toLowerCase().contains(searchText.toLowerCase())) {
                Label eventLabel = new Label("Nom: " + evenement.getNom_evenement() +
                        ", Type: " + evenement.getType_evenement() +
                        ", Date Debut: " + evenement.getDate_debut().toString() +
                        ", Date fin: " + evenement.getDate_fin().toString() +
                        ", Lieu: " + evenement.getLieu_evenement() +
                        ", budget: " + evenement.getBudget());
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
                    System.err.println("Error loading image: " + e.getMessage());
                    // Show error message in alert dialog
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Error loading image: " + e.getMessage());
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
    }*/
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
                  System.err.println("Error loading image: " + e.getMessage());
                  // Show error message in alert dialog
                  Alert alert = new Alert(Alert.AlertType.ERROR);
                  alert.setTitle("Error");
                  alert.setHeaderText(null);
                  alert.setContentText("Error loading image: " + e.getMessage());
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
  }


    private void filterEvenement(String searchText) throws SQLException {

        List<Evenement> evenements = SE.getAll();
        EventVBox.getChildren().clear();

        for (Evenement evenement : evenements) {
            // Vérifiez si le nom de la réservation contient le texte de recherche
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
                    System.err.println("Error loading image: " + e.getMessage());
                    // Show error message in alert dialog
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Error loading image: " + e.getMessage());
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
        if (selectedEvenement != null) {
            selectedEvenement.setImage_evenement(imagePath); // Utilisez imagePath pour définir le chemin de l'image

            selectedEvenement.setType_evenement(typeEvenement.getText());
            selectedEvenement.setNom_evenement(NomEvenement.getText());
            selectedEvenement.setDate_debut(DateDebut.getValue().atStartOfDay());
            selectedEvenement.setDate_fin(DateFin.getValue().atStartOfDay());

            selectedEvenement.setLieu_evenement(LieuEvenement.getText());
            selectedEvenement.setBudget(Double.parseDouble(Budget.getText()));


            SE.update(selectedEvenement);
            refreshEvents();


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

   /* @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Evenement> events = SE.getAll();
            for (Evenement evenement : events) {
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
                    File imageFile = new File(imageDirectory + evenement.getImage_evenement());
                    Image image = new Image(imageFile.toURI().toString());
                    imageView.setImage(image);
                    imageView.setFitWidth(100); // Set the desired width of the image
                    imageView.setPreserveRatio(true); // Preserve the aspect ratio of the image
                } catch (Exception e) {
                    // Handle image loading errors
                    System.err.println("Error loading image: " + e.getMessage());
                    // Show error message in alert dialog
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Error loading image: " + e.getMessage());
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setupSearchBarListener();
    }*/


    private void refreshEvents() throws SQLException {
        List<Evenement> events = SE.getAll();
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
                System.err.println("Error loading image: " + e.getMessage());
                // Show error message in alert dialog
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error loading image: " + e.getMessage());
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
}