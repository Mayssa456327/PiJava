package com.example.pidevjava.controllers;

import com.example.pidevjava.models.Evenement;
import com.example.pidevjava.models.Sponsor;
import com.example.pidevjava.services.ServiceEvenement;
import com.example.pidevjava.services.ServiceSponsor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

public class FrontSponsorController {

    @FXML
    private Button AjouterBtn;

    @FXML
    private TextField adresse;

    @FXML
    private TextField budget;

    @FXML
    private TextField email_sponsor;

    @FXML
    private ChoiceBox<Integer> evenement_id;

    @FXML
    private TextField nom_sponsor;
    private final ServiceSponsor SS = new ServiceSponsor();

    private final ServiceEvenement SE = new ServiceEvenement(); // Inject ServiceEvenement

    @FXML
    void OnClickedAjouterSponsor(ActionEvent event) {

        try {
            if (champsSontValides()) {
                Sponsor newSponsor = new Sponsor(
                        (double) Integer.parseInt(budget.getText()),
                        evenement_id.getValue(),
                        nom_sponsor.getText(),
                        adresse.getText(),
                        email_sponsor.getText()


                );
                SS.add(newSponsor);

                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Événement ajouté");
                alert.setHeaderText(null);
                alert.setContentText("L'événement a été ajouté avec succès!");
                alert.showAndWait();

                // Clear fields after successful addition
                clearFields();

                // Refresh event list
               // refreshEvents();
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

    @FXML
    void initialize() {
        // Populate the evenement_id ChoiceBox with event IDs
        try {
            List<Integer> eventIds = SE.getAllEventIds();
            evenement_id.getItems().addAll(eventIds);
        } catch (SQLException e) {
            // Handle database errors
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de la récupération des ID d'événement.");
            alert.showAndWait();
            e.printStackTrace(); // Log the exception for debugging
        }
    }

    private boolean champsSontValides() {
        return evenement_id.getValue() != null &&
                !nom_sponsor.getText().isEmpty() &&
                !adresse.getText().isEmpty() &&
                !email_sponsor.getText().isEmpty() &&
                !budget.getText().isEmpty(); // Utilisez !Budget.getText().isEmpty() pour vérifier si le champ Budget n'est pas vide
    }

    private void clearFields() {
        evenement_id.setValue(null);
        nom_sponsor.clear();
        adresse.clear();
        email_sponsor.clear();

        budget.clear();

    }

}
