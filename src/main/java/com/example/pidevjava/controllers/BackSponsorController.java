package com.example.pidevjava.controllers;

import com.example.pidevjava.models.Sponsor;
import com.example.pidevjava.services.ServiceEvenement;
import com.example.pidevjava.services.ServiceSponsor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class BackSponsorController implements Initializable {

    @FXML
    private VBox SponsorVBox;

    @FXML
    private TextField adresse;

    @FXML
    private TextField budget;

    @FXML
    private TextField email_sponsor;

    @FXML
    private ChoiceBox<Integer> evenement_id;

    @FXML
    private Button modiferBtn;

    @FXML
    private TextField nom_sponsor;

    @FXML
    private Button suppBtn;

    private final ServiceSponsor SS = new ServiceSponsor();
    private final ServiceEvenement SE = new ServiceEvenement(); // Inject ServiceEvenement

    private ServiceSponsor serviceSponsor;
    private Sponsor selectedSponsor;

    @FXML
    void OnClickedModifierSponsor(ActionEvent event) {
        if (selectedSponsor != null) {
            try {
                selectedSponsor.setBudget(Double.parseDouble(budget.getText()));
                selectedSponsor.setEvenement_id(evenement_id.getValue());
                selectedSponsor.setNom_sponsor(nom_sponsor.getText());
                selectedSponsor.setEmail_sponsor(email_sponsor.getText());
                selectedSponsor.setAdresse(adresse.getText());
                serviceSponsor.update(selectedSponsor);
                loadReservations();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while updating sponsor");
            }
        }
    }

    @FXML
    void OnClickedSupprimerSponsor(ActionEvent event) {
        if (selectedSponsor != null) {
            try {
                serviceSponsor.delete(selectedSponsor);
                loadReservations();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting sponsor");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceSponsor = new ServiceSponsor();
        loadEventIds(); // Call method to load event IDs
        loadReservations();
    }

    private void loadEventIds() {
        List<Integer> eventIds;
        try {
            eventIds = SE.getAllEventIds();
            evenement_id.getItems().addAll(eventIds);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while loading event IDs");
        }
    }

    private void loadReservations() {
        SponsorVBox.getChildren().clear();
        try {
            List<Sponsor> sponsors = SS.getAll();
            for (Sponsor sponsor : sponsors) {
                Label sponsorLabel = new Label();
                sponsorLabel.setText(" Budget : " + sponsor.getBudget() +
                        ", Evenement ID: " + sponsor.getEvenement_id() +
                        ", Nom: " + sponsor.getNom_sponsor() +
                        ", Email: " + sponsor.getEmail_sponsor() +
                        ", Adresse: " + sponsor.getAdresse());

                sponsorLabel.setOnMouseClicked(event -> {
                    selectedSponsor = sponsor;
                    budget.setText(String.valueOf(sponsor.getBudget()));
                    evenement_id.setValue(sponsor.getEvenement_id());
                    nom_sponsor.setText(sponsor.getNom_sponsor());
                    email_sponsor.setText(sponsor.getEmail_sponsor());
                    adresse.setText(sponsor.getAdresse());
                });

                SponsorVBox.getChildren().add(sponsorLabel);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while loading reservations");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
