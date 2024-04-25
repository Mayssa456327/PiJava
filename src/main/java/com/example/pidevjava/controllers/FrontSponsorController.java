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
    private Button backbtn;


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
    void OnClickedBack(ActionEvent event) {
        SE.changeScreen(event,"/com/example/pidevjava/FrontEvenement.fxml", "afficher front Evenement");

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
        // Vérifier que l'ID d'événement est sélectionné et que les champs ne sont pas vides
        if (evenement_id.getValue() == null ||
                nom_sponsor.getText().isEmpty() ||
                adresse.getText().isEmpty() ||
                email_sponsor.getText().isEmpty() ||
                budget.getText().isEmpty()) {
            return false;
        }

        // Vérifier le format de l'e-mail
        String email = email_sponsor.getText();
        if (!isValidEmailFormat(email)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir une adresse e-mail valide avec le domaine @gmail.com!");
            alert.showAndWait();
            return false;
        }

        return true;
    }

    private boolean isValidEmailFormat(String email) {
        // Vérifier si l'e-mail correspond au format attendu avec le domaine @zdfcaz.com
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:gmail\\.com)$";
        return email.matches(emailPattern);
    }
    private void clearFields() {
        evenement_id.setValue(null);
        nom_sponsor.clear();
        adresse.clear();
        email_sponsor.clear();

        budget.clear();

    }

}
