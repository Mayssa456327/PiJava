package org.example.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.models.PDFService;
import org.example.models.EmailService;
import org.example.models.Reservation;
import org.example.models.Hopital;
import org.example.models.SMSsender;
import org.example.services.ReservationService;
import org.example.services.HopitalService;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {
    @FXML
    private TextField nomPatientTextField;
    @FXML
    private TextField dateDebutTextField;
    @FXML
    private TextField dateFinTextField;
    @FXML
    private ComboBox<String> hopitalComboBox;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField telephoneTextField;
    @FXML
    private TableView<Reservation> reservationsTable;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableColumn<Reservation, String> nomPatientColumn;

    @FXML
    private TableColumn<Reservation, String> dateDebutColumn;
    @FXML
    private ComboBox<String> sortComboBox;


    private ObservableList<Reservation> allReservations;

    private final ReservationService reservationService = new ReservationService();
    private final HopitalService hopitalService = new HopitalService();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Charger les réservations lors de l'initialisation
        try {
            if (reservationsTable != null) {
                allReservations = FXCollections.observableArrayList(reservationService.getAll());
                reservationsTable.getItems().addAll(allReservations);
            } else {
                System.err.println("reservationsTable is null. Make sure it is properly injected from FXML.");
            }
        } catch (SQLException e) {
            showErrorAlert("Failed to retrieve reservations: " + e.getMessage());
        }

        // Charger les noms d'hôpitaux dans la ComboBox
        try {
            List<String> hospitalNames = reservationService.getAllHospitalNames();
            hopitalComboBox.getItems().addAll(hospitalNames);
        } catch (SQLException e) {
            showErrorAlert("Failed to load hospitals: " + e.getMessage());
        }

        // Écouter les changements dans le TextField de recherche
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterReservations(newValue);
        });
    }

    @FXML
    public void addReservation() {
        String nomPatient = nomPatientTextField.getText();
        String dateDebut = dateDebutTextField.getText();
        String dateFin = dateFinTextField.getText();
        String hopitalNom = hopitalComboBox.getValue();
        String email = emailTextField.getText();
        String telephone = telephoneTextField.getText();

        // Appeler la méthode SMSSender de SmsSender avec le numéro de téléphone récupéré
        //SMSsender.sendSMS(telephone, "Votre reservation a été bien enrigistrer");
        // Vérifier si les champs obligatoires sont remplis
        if (nomPatient.isEmpty() || dateDebut.isEmpty() || dateFin.isEmpty() || hopitalNom == null || email.isEmpty() || telephone.isEmpty()) {
            showErrorAlert("Il faut remplir tout les champs.");
            return;
        }
        try {
            LocalDate.parse(dateDebut);
            LocalDate.parse(dateFin);
        } catch (DateTimeParseException e) {
            showErrorAlert("Entrer la date sous la format  yyyy-MM-dd.");
            return;
        }
        if (!isValidEmail(email)) {
            showErrorAlert("Entrer une adresse email valide.");
            return;
        }
        if (!isValidPhoneNumber(telephone)) {
            showErrorAlert("Entrer un numero de telephone valide.");
            return;
        }

        // Récupérer l'objet Hopital correspondant au nom sélectionné
        Hopital hopital;
        try {
            hopital = hopitalService.getHospitalByName(hopitalNom);
            if (hopital == null) {
                showErrorAlert("Hospital not found for name: " + hopitalNom);
                return;
            }
        } catch (SQLException e) {
            showErrorAlert("Failed to retrieve hospital: " + e.getMessage());
            return;
        }

        // Créer la réservation avec l'objet Hopital récupéré
        Reservation reservation = new Reservation(hopital.getId(), nomPatient, dateDebut, dateFin, hopital, email, telephone);

        try {
            reservationService.add(reservation);
            showSuccessAlert("Reservation ajouter avec succes");
            // Générer le fichier PDF
            String cheminFichierPDF = "C:/Users/mouaf/Downloads/reservation" + reservation.getId() + ".pdf"; // Nom du fichier PDF basé sur l'ID de la réservation
            PDFService.genererPDF(nomPatient, dateDebut, dateFin,email,hopitalNom, cheminFichierPDF);
            showSuccessAlert("Fichier PDF généré avec succès.");
            // Ajoutez ceci à la fin de votre méthode d'ajout de réservation après avoir généré le PDF
            String subject = "Votre réservation";
            String body = "Bonjour, veuillez trouver ci-joint votre réservation.";
            EmailService.sendEmailWithAttachment(email, subject, body, cheminFichierPDF);

        } catch (SQLException e) {
            showErrorAlert("Impossible d'ajouter la reservation: " + e.getMessage());
        }

        refreshInterface();
    }
    @FXML
    public void deleteReservation() {
        Reservation selectedResevation = reservationsTable.getSelectionModel().getSelectedItem();
        if (selectedResevation != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText("Supprimer la reservation");
            confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer la reservation sélectionné?");
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        hopitalService.delete(selectedResevation.getId());
                        reservationsTable.getItems().remove(selectedResevation);
                        showSuccessAlert("Reservation supprimé avec succès");
                    } catch (SQLException e) {
                        showErrorAlert("Erreur lors de la suppression de la reservation: " + e.getMessage());
                    }
                }
            });
        } else {
            showErrorAlert("Veuillez sélectionner une Reservation à supprimer.");
        }
        refreshInterface();
    }
    @FXML
    public void displaySelectedReservation() {
        // Récupérer l'hôpital sélectionné dans le TableView
        Reservation selectedReservation = reservationsTable.getSelectionModel().getSelectedItem();

        // Afficher les informations de l'hôpital sélectionné dans les champs de texte
        if (selectedReservation != null) {
            nomPatientTextField.setText(selectedReservation.getNom_patient());
            dateDebutTextField.setText(selectedReservation.getDate_debut());
            dateFinTextField.setText(selectedReservation.getDate_fin());
            telephoneTextField.setText(selectedReservation.getTelephone());
            emailTextField.setText(selectedReservation.getEmail());
        }
    }

    @FXML
    public void updateReservation(javafx.event.ActionEvent actionEvent) {
        // Récupérer l'hôpital sélectionné dans le TableView
        Reservation selectedReservation = reservationsTable.getSelectionModel().getSelectedItem();

        // Vérifier si un hôpital est sélectionné
        if (selectedReservation != null) {
            try {
                // Mettre à jour les informations de l'hôpital avec les valeurs des champs de texte
                nomPatientTextField.setText(selectedReservation.getNom_patient());
                dateDebutTextField.setText(selectedReservation.getDate_debut());
                dateFinTextField.setText(selectedReservation.getDate_fin());
                telephoneTextField.setText(selectedReservation.getTelephone());
                emailTextField.setText(selectedReservation.getEmail());
                // Appeler la méthode de mise à jour dans le service
                reservationService.update(selectedReservation);

                // Actualiser la TableView pour refléter les modifications
                reservationsTable.refresh();

                showSuccessAlert("Reservation  updated successfully");
            } catch (SQLException e) {
                showErrorAlert("Failed to update Reservation: " + e.getMessage());
            }
        } else {
            showErrorAlert("Please select an Reservation to update");
        }
        refreshInterface();
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

    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^\\d{8}$";
        return phoneNumber.matches(regex);
    }
    public void refreshInterface() {
        try {
            List<Reservation> reservations = reservationService.getAll();
            reservationsTable.getItems().clear();
            reservationsTable.getItems().addAll(reservations);
        } catch (SQLException e) {
            showErrorAlert("Failed to refresh interface: " + e.getMessage());
        }
    }

    private void filterReservations(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            // Si la barre de recherche est vide, afficher toutes les réservations
            reservationsTable.setItems(allReservations);
        } else {
            // Sinon, filtrer les réservations en fonction du texte de recherche
            ObservableList<Reservation> filteredList = FXCollections.observableArrayList();
            for (Reservation reservation : allReservations) {
                if (reservation.getNom_patient().toLowerCase().contains(searchText.toLowerCase())
                        || reservation.getTelephone().toLowerCase().contains(searchText.toLowerCase())
                        || reservation.getEmail().toLowerCase().contains(searchText.toLowerCase())
                        || reservation.getDate_debut().toLowerCase().contains(searchText.toLowerCase())
                        || reservation.getDate_fin().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredList.add(reservation);
                }
            }
            reservationsTable.setItems(filteredList);
        }
    }

    @FXML
    public void handleSortSelection(javafx.event.ActionEvent actionEvent) {
        String selectedSortCriteria = sortComboBox.getValue();
        if (selectedSortCriteria != null) {
            if (selectedSortCriteria.equals("Nom Patient")) {
                sortByNomPatient();
            } else if (selectedSortCriteria.equals("Date de Début")) {
                sortByDateDebut();
            }
        }
    }
    public void sortByNomPatient() {
        reservationsTable.getSortOrder().clear();
        reservationsTable.getSortOrder().add(nomPatientColumn);
    }
    private void sortByDateDebut() {
        reservationsTable.getSortOrder().clear();
        reservationsTable.getSortOrder().add(dateDebutColumn);
    }


}
