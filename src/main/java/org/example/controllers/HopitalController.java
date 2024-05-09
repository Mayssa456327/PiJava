package org.example.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.models.Hopital;
import org.example.models.Reservation;
import org.example.services.HopitalService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class HopitalController implements Initializable {
    @FXML
    private TextField nomTextField;
    @FXML
    private TextField adresseTextField;
    @FXML
    private TextField telephoneTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField nbrChambreTextField;
    @FXML
    private TextField idTextField;
    @FXML
    private ComboBox<String> sortComboBox;

    private ObservableList<Hopital> allHopitals;

    @FXML
    private TableColumn<Hopital, String> nomColumn;

    @FXML
    private TableColumn<Hopital, String> adresseColumn;

    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<Hopital> hopitalsTable;

    private  final HopitalService hopitalService = new HopitalService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Charger les réservations lors de l'initialisation
        try {
            if (hopitalsTable != null) {
                allHopitals = FXCollections.observableArrayList(hopitalService.getAll());
                hopitalsTable.getItems().addAll(allHopitals);
            } else {
                System.err.println("reservationsTable is null. Make sure it is properly injected from FXML.");
            }
        } catch (SQLException e) {
            showErrorAlert("Failed to retrieve reservations: " + e.getMessage());
        }
        // Écouter les changements dans le TextField de recherche
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterReservations(newValue);
        });
    }
    @FXML
    private void addHopital(javafx.event.ActionEvent actionEvent) {
        String nom = nomTextField.getText();
        String adresse = adresseTextField.getText();
        String telephone = telephoneTextField.getText();
        String email = emailTextField.getText();
        String nbrChambreText = nbrChambreTextField.getText();

        // Vérifier si tous les champs sont remplis
        if (nom.isEmpty() || adresse.isEmpty() || telephone.isEmpty() || email.isEmpty() || nbrChambreText.isEmpty()) {
            showErrorAlert("Veuillez remplir tous les champs.");
            return;
        }

        // Vérifier le format de l'email
        if (!isValidEmail(email)) {
            showErrorAlert("Veuillez saisir une adresse email valide.");
            return;
        }

        // Vérifier le format du numéro de téléphone
        if (!isValidPhoneNumber(telephone)) {
            showErrorAlert("Veuillez saisir un numéro de téléphone valide.");
            return;
        }

        // Vérifier le format du nombre de chambres
        if (!isValidNumberOfRooms(nbrChambreText)) {
            showErrorAlert("Veuillez saisir un nombre valide de chambres.");
            return;
        }

        // Ajouter l'hôpital si toutes les validations sont passées
        try {
            int nbrChambre = Integer.parseInt(nbrChambreText);
            Hopital hopital = new Hopital(nom, adresse, telephone, email, nbrChambre);
            hopitalService.add(hopital);
            showSuccessAlert("Hopital ajouté avec succès");
        } catch (SQLException e) {
            showErrorAlert("Échec de l'ajout de l'hôpital : " + e.getMessage());
        }
    }




    @FXML
    public void updateHopital(javafx.event.ActionEvent actionEvent) {
        // Récupérer l'hôpital sélectionné dans le TableView
        Hopital selectedHopital = hopitalsTable.getSelectionModel().getSelectedItem();

        // Vérifier si un hôpital est sélectionné
        if (selectedHopital != null) {
            try {
                // Mettre à jour les informations de l'hôpital avec les valeurs des champs de texte
                selectedHopital.setNom(nomTextField.getText());
                selectedHopital.setAdresse(adresseTextField.getText());
                selectedHopital.setTelephone(telephoneTextField.getText());
                selectedHopital.setEmail(emailTextField.getText());
                selectedHopital.setNombre_chambre(Integer.parseInt(nbrChambreTextField.getText()));
                // Appeler la méthode de mise à jour dans le service
                hopitalService.update(selectedHopital);

                // Actualiser la TableView pour refléter les modifications
                hopitalsTable.refresh();

                showSuccessAlert("Hopital updated successfully");
            } catch (SQLException e) {
                showErrorAlert("Failed to update Hopital: " + e.getMessage());
            }
        } else {
            showErrorAlert("Please select an Hopital to update");
        }
    }

    @FXML
    public void displaySelectedHopital() {
        // Récupérer l'hôpital sélectionné dans le TableView
        Hopital selectedHopital = hopitalsTable.getSelectionModel().getSelectedItem();

        // Afficher les informations de l'hôpital sélectionné dans les champs de texte
        if (selectedHopital != null) {
            nomTextField.setText(selectedHopital.getNom());
            adresseTextField.setText(selectedHopital.getAdresse());
            telephoneTextField.setText(selectedHopital.getTelephone());
            emailTextField.setText(selectedHopital.getEmail());
            nbrChambreTextField.setText(String.valueOf(selectedHopital.getNombre_chambre()));
        }
    }


    @FXML
    public void getById() {
        int hopitalId = Integer.parseInt(idTextField.getText()); // Assuming you have a TextField for entering the ID
        try {
            Hopital hopital = hopitalService.getById(hopitalId);
            // Now you can use the retrieved Hopital object as needed, such as displaying its details in the UI
        } catch (SQLException e) {
            showErrorAlert("Failed to retrieve hopital: " + e.getMessage());
        }
    }

    @FXML
    public void deleteHopital() {
        // Vérifier d'abord si une ligne est sélectionnée dans la TableView
        Hopital selectedHopital = hopitalsTable.getSelectionModel().getSelectedItem();
        if (selectedHopital != null) {
            // Demander confirmation avant de supprimer l'hôpital
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText("Supprimer l'hôpital");
            confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer l'hôpital sélectionné?");

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        hopitalService.delete(selectedHopital.getId());
                        hopitalsTable.getItems().remove(selectedHopital);
                        showSuccessAlert("Hopital supprimé avec succès");
                    } catch (SQLException e) {
                        showErrorAlert("Erreur lors de la suppression de l'hôpital: " + e.getMessage());
                    }
                }
            });
        } else {
            showErrorAlert("Veuillez sélectionner un hôpital à supprimer.");
        }
    }




    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private boolean isValidEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(regex);
    }

    // Méthode pour valider le format du numéro de téléphone
    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^\\d{8}$";
        return phoneNumber.matches(regex);
    }

    private boolean isValidNumberOfRooms(String numberOfRooms) {
        try {
            int number = Integer.parseInt(numberOfRooms);
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @FXML
    public Hopital getHopitalFromUI() {
        // Récupérez les informations de l'hôpital à partir de votre interface utilisateur
        String nom = nomTextField.getText();
        String adresse = adresseTextField.getText();
        String telephone = telephoneTextField.getText();
        String email = emailTextField.getText();
        int nombreChambre = Integer.parseInt(nbrChambreTextField.getText());
        // Créez un nouvel objet Hopital avec les informations récupérées
        Hopital hopital = new Hopital(nom, adresse, telephone, email, nombreChambre);

        return hopital;
    }

    private void filterReservations(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            // Si la barre de recherche est vide, afficher toutes les réservations
            hopitalsTable.setItems(allHopitals);
        } else {
            // Sinon, filtrer les réservations en fonction du texte de recherche
            ObservableList<Hopital> filteredList = FXCollections.observableArrayList();
            for (Hopital hopital : allHopitals) {
                if (hopital.getNom().toLowerCase().contains(searchText.toLowerCase())
                        || hopital.getAdresse().toLowerCase().contains(searchText.toLowerCase())
                        || hopital.getEmail().toLowerCase().contains(searchText.toLowerCase())
                        || hopital.getTelephone().toLowerCase().contains(searchText.toLowerCase())
                        || Integer.toString(hopital.getNombre_chambre()).contains(searchText.toLowerCase())) {
                    filteredList.add(hopital);
                }
            }
            hopitalsTable.setItems(filteredList);
        }
    }

    @FXML
    public void handleSortSelection(javafx.event.ActionEvent actionEvent) {
        String selectedSortCriteria = sortComboBox.getValue();
        if (selectedSortCriteria != null) {
            if (selectedSortCriteria.equals("nom")) {
                sortByNom();
            } else if (selectedSortCriteria.equals("Adresse")) {
                sortByAdresse();
            }
        }
    }
    public void sortByNom() {
        hopitalsTable.getSortOrder().clear();
        hopitalsTable.getSortOrder().add(nomColumn);
    }
    private void sortByAdresse() {
        hopitalsTable.getSortOrder().clear();
        hopitalsTable.getSortOrder().add(adresseColumn);
    }
    @FXML
    public void backHopital(javafx.event.ActionEvent actionEvent) throws IOException {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backHopital.fxml"));
            Parent root = loader.load();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

            // Create a new stage for the loaded FXML scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hopital");
            stage.initStyle(StageStyle.TRANSPARENT); // Optional, depending on your design
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading hopitals: " + e.getMessage());
        }
    }

    @FXML
    public void backReservation(javafx.event.ActionEvent actionEvent) throws IOException {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backReservation.fxml"));
            Parent root = loader.load();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

            // Create a new stage for the loaded FXML scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Reservation");
            stage.initStyle(StageStyle.TRANSPARENT); // Optional, depending on your design
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading hopitals: " + e.getMessage());
        }
    }



}

