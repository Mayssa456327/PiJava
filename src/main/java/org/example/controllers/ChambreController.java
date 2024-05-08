package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.models.Chambre;
import org.example.models.Reservation;
import org.example.services.ChambreService;
import org.example.services.HopitalService;
import org.example.services.ReservationService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ChambreController implements Initializable {

    @FXML
    private CheckBox ouiCheckBox;

    @FXML
    private CheckBox nonCheckBox;

    @FXML
    private ComboBox<String> hopitalComboBox;

    @FXML
    private TextField reservationIdTextField;
    @FXML
    private TableView<Chambre> chambresTable;
    private ObservableList<Chambre> allChambres;



    private final ChambreService chambreService = new ChambreService();
    private final HopitalService hopitalService = new HopitalService();
    private final ReservationService reservationService = new ReservationService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Charger les réservations lors de l'initialisation
        try {
            if (chambresTable != null) {
                allChambres = FXCollections.observableArrayList(chambreService.getAll());
                chambresTable.getItems().addAll(allChambres);
            } else {
                System.err.println("reservationsTable is null. Make sure it is properly injected from FXML.");
            }
        } catch (SQLException e) {
            showErrorAlert("Failed to retrieve reservations: " + e.getMessage());
        }
        // Charger les noms d'hôpitaux dans la ComboBox
        try {
            List<String> hospitalNames = reservationService.getAllHospitalNames();
            hopitalComboBox.setItems(FXCollections.observableArrayList(hospitalNames));
        } catch (SQLException e) {
            showErrorAlert("Failed to load hospitals: " + e.getMessage());
        };
    }

    @FXML
    void ajouterChambre(ActionEvent actionEvent) {
        try {
            boolean disponibiliter = ouiCheckBox.isSelected();
            System.out.println(disponibiliter);


            // Récupérer l'ID de la réservation
            int reservationId = Integer.parseInt(reservationIdTextField.getText());

            // Récupérer le nom de l'hôpital sélectionné dans la ComboBox
            String selectedHospital = hopitalComboBox.getValue();
            System.out.println(selectedHospital);

            // Récupérer l'ID de l'hôpital à partir du service
            int hopitalId = reservationService.getHospitalIdByName(selectedHospital);

            // Vérifie si l'hôpital existe avant d'ajouter la chambre
            if (hopitalId != -1) {
                Chambre chambre = new Chambre();
                chambre.setDisponibiliter(disponibiliter);
                chambre.setHopital_id(hopitalId);
                chambre.setReservation_id(reservationId);

                chambreService.add(chambre);

                // Affiche une confirmation
                showAlert(Alert.AlertType.INFORMATION, "Chambre ajoutée avec succès.");
            } else {
                // Affiche une erreur si l'hôpital n'existe pas
                showAlert(Alert.AlertType.ERROR, "L'hôpital sélectionné n'existe pas.");
            }
        } catch (SQLException | NumberFormatException e) {
            // Affiche une erreur en cas de problème lors de l'ajout de la chambre
            showAlert(Alert.AlertType.ERROR, "Erreur lors de l'ajout de la chambre : " + e.getMessage());
        }
    }

    // Méthode utilitaire pour afficher une boîte de dialogue d'alerte
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        showAlert(Alert.AlertType.ERROR, message);
    }


}
