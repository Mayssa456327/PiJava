package com.example.pidevjava.controllers;

import com.example.pidevjava.models.Evenement;
import com.example.pidevjava.models.Sponsor;
import com.example.pidevjava.services.ServiceEvenement;
import com.example.pidevjava.services.ServiceSponsor;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

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
    private ChoiceBox<String> evenement_id;

    @FXML
    private TextField nom_sponsor;

    private final ServiceSponsor SS = new ServiceSponsor();
    private final ServiceEvenement SE = new ServiceEvenement();
    private Evenement selectedEvent;

    public void setSelectedEvent(Evenement event) {
        this.selectedEvent = event;
    }

    public void setSelectedEventName(String eventName) {
        evenement_id.setValue(eventName);
    }

    @FXML
    void OnClickedAjouterSponsor(ActionEvent event) {
        try {
            if (champsSontValides()) {
                if (isNumeric(budget.getText())) {
                    double budgetValue = Double.parseDouble(budget.getText());
                    int evenement_ids = SE.getEventIdByName(evenement_id.getValue());
                    Sponsor newSponsor = new Sponsor(
                            budgetValue,
                            evenement_ids,
                            nom_sponsor.getText(),
                            email_sponsor.getText(),
                            adresse.getText()
                    );
                    sendEmail(email_sponsor.getText() , "Sponsorisation", "Votre Sponsorisation a éte ajouter");

                    SS.add(newSponsor);

                    // Afficher un message de réussite
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sponsor ajouté");
                    alert.setHeaderText(null);
                    alert.setContentText("Le sponsor a été ajouté avec succès !");
                    alert.showAndWait();

                    // Effacer les champs après l'ajout réussi
                    clearFields();
                } else {
                    // Afficher un message d'erreur si le champ du budget n'est pas un nombre valide
                    afficherAlerteErreur("Le budget doit être un nombre valide !");
                }
            } else {
                // Afficher un message d'erreur si les champs ne sont pas valides
                afficherAlerteErreur("Veuillez remplir tous les champs !");
            }
        } catch (NumberFormatException e) {
            // Afficher un message d'erreur si le champ du budget n'est pas un nombre valide
            afficherAlerteErreur("Le budget doit être un nombre valide !");
        } catch (SQLException e) {
            // Gérer les erreurs de base de données
            afficherAlerteErreur("Une erreur est survenue lors de l'ajout du sponsor. Veuillez réessayer plus tard.");
            e.printStackTrace(); // Journaliser l'exception pour le débogage
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void OnClickedBack(ActionEvent event) {
        SE.changeScreen(event, "/com/example/pidevjava/FrontEvenement.fxml", "afficher front Evenement");
    }

  /*  @FXML
    void initialize() {
        try {
            List<String> eventNames = SE.getAllEventNames(); // Obtient tous les noms des événements
            evenement_id.getItems().addAll(eventNames); // Ajoute les noms des événements au ChoiceBox

            // Ajoute un écouteur de changement pour mettre à jour l'ID de l'événement sélectionné
            evenement_id.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    //int eventId = SE.getEventIdByName(String.valueOf(newValue)); // Obtient l'ID de l'événement sélectionné par son nom
                    evenement_id.setItems(FXCollections.observableList(eventNames)); // Définit l'ID de l'événement sélectionné
                }
            });
        } catch (SQLException e) {
            afficherAlerteErreur("Une erreur est survenue lors de la récupération des noms d'événements.");
            e.printStackTrace(); // Journaliser l'exception pour le débogage
        }
        evenement_id.getSelectionModel().selectedItemProperty().addListener(((observable ,oldValue, newValue)->{if(newValue != null){ evenement_id.setValue(newValue);
            System.out.println("Selected Event: " + newValue);
        }}));
    }*/

    @FXML
    void initialize() {
        try {
            List<String> eventNames = SE.getAllEventNames(); // Obtient tous les noms des événements
            evenement_id.getItems().addAll(eventNames); // Ajoute les noms des événements au ChoiceBox

            // Ajoute un écouteur de changement pour afficher l'événement sélectionné
            evenement_id.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    System.out.println("Selected Event: " + newValue); // Affiche l'événement sélectionné
                }
            });
        } catch (SQLException e) {
            afficherAlerteErreur("Une erreur est survenue lors de la récupération des noms d'événements.");
            e.printStackTrace(); // Journaliser l'exception pour le débogage
        }

        budget.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                budget.setText(oldValue);
            }
        });
    }


    private boolean champsSontValides() {
        // Vérifier que l'ID d'événement est sélectionné et que les champs ne sont pas vides
        return evenement_id.getValue() != null &&
                !nom_sponsor.getText().isEmpty() &&
                !email_sponsor.getText().isEmpty() &&
                !adresse.getText().isEmpty() &&
                isEmailValid(email_sponsor.getText());
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str); // Essaye de convertir la chaîne en double
            // Vérifie si la chaîne contient uniquement des chiffres et éventuellement un seul point décimal
            return str.matches("^\\d*\\.?\\d*$");
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void clearFields() {
        evenement_id.setValue(null);
        nom_sponsor.clear();
        email_sponsor.clear();
        adresse.clear();
        budget.clear();
    }

    private void afficherAlerteErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isEmailValid(String email) {
        // Vérifie si l'e-mail contient un "@" et un "."
        if (!email.contains("@")) {
            afficherAlerteErreur("L'adresse e-mail doit contenir '@gmail.com' !");
            return false;
        } else {
            return email.contains(".");
        }
    }
    private void sendEmail(String recipient, String subject, String text) throws MessagingException, IOException {
        String from = "mayssabenhammouda99@gmail.com";
        final String username = "mayssabenhammouda99@gmail.com";
        final String password = "mayssabenhammouda2001";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);

        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(text);


        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(textPart);


        message.setContent(multipart);
        Transport.send(message);
    }
}
