package com.example.pidevjava.controllers;

import com.example.pidevjava.models.Sponsor;
import com.example.pidevjava.services.ServiceEvenement;
import com.example.pidevjava.services.ServiceSponsor;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


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
    private ChoiceBox<Integer> evenement_id;

    @FXML
    private Button modiferBtn;

    @FXML
    private TextField nom_sponsor;

    @FXML
    private Button suppBtn;

    @FXML
    private PieChart pieChart;

    private final ServiceSponsor SS = new ServiceSponsor();
    private final ServiceEvenement SE = new ServiceEvenement(); // Inject ServiceEvenement

    private ServiceSponsor serviceSponsor;
    private Sponsor selectedSponsor;
    private Voice voice; // Voice for text-to-speech

    @FXML
    private Button statBtn;
    @FXML
    void OnClickedBack(ActionEvent event) {
        SE.changeScreen(event,"/com/example/pidevjava/Sign_in.fxml", " Accueille ");

    }

    @FXML
    void OnClickedModifierSponsor(ActionEvent event) {
        if (selectedSponsor != null) {
            // Validate fields
            String budgetText = budget.getText();
            Integer evenementId = evenement_id.getValue();
            String nomSponsorText = nom_sponsor.getText();
            String emailSponsorText = email_sponsor.getText();
            String adresseText = adresse.getText();

            if (budgetText.isEmpty() || evenementId == null || nomSponsorText.isEmpty() ||
                    emailSponsorText.isEmpty() || adresseText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Le budget doit être chiffré.");
                return;
            }

            // Check if email ends with "@gmail.com"
            if (!emailSponsorText.toLowerCase().endsWith("@gmail.com")) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "L’e-mail doit se terminer par @gmail.com");
                return;
            }

            try {
                selectedSponsor.setBudget(Double.parseDouble(budgetText));
                selectedSponsor.setEvenement_id(evenementId);
                selectedSponsor.setNom_sponsor(nomSponsorText);
                selectedSponsor.setEmail_sponsor(emailSponsorText);
                selectedSponsor.setAdresse(adresseText);
                serviceSponsor.update(selectedSponsor);
                loadReservations();
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Entrée non valide pour le budget.");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s’est produite lors de la mise à jour du sponsor");
            }
        }
    }

    @FXML
    void OnClcickedStatSponsor(ActionEvent event) {
        generateStatistics();
    }

    private void generateStatistics() {
        pieChart.getData().clear(); // Clear previous data

        try {
            List<Sponsor> sponsors = SS.getAll();

            // Tri des sponsors par nom croissant
            sponsors.sort(Comparator.comparing(Sponsor::getNom_sponsor));

            // Création de la liste pour le PieChart
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Sponsor sponsor : sponsors) {
                pieChartData.add(new PieChart.Data(sponsor.getNom_sponsor(), sponsor.getBudget()));
            }


            pieChart.setData(pieChartData);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s’est produite lors de la génération des statistiques");
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
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s’est produite lors de la suppression du sponsor");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceSponsor = new ServiceSponsor();
        loadEventIds(); // Call method to load event IDs
        loadReservations();
        initializeVoice();
        generateStatistics();

    }
    private void initializeVoice() {
        // Set the system property for FreeTTS voices directory
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        // Get the default voice
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice[] voices = voiceManager.getVoices();

        // Choose a voice (e.g., "kevin" or "kevin16")
        voice = voiceManager.getVoice("kevin");
        if (voice != null) {
            voice.allocate();
        } else {
            System.err.println("Cannot find voice: kevin16");
        }
    }
    private void speak(String phrase) {
        if (voice != null) {
            voice.speak(phrase);
        }
    }

    private void loadEventIds() {
        List<Integer> eventIds;
        try {
            eventIds = SE.getAllEventIds();
            evenement_id.getItems().addAll(eventIds);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s’est produite lors du chargement des ID d’événement");
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
                    speak(sponsorLabel.getText()); // Speak the sponsor details

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
    private void clearFields() {
        evenement_id.setValue(null);
        budget.clear();
        adresse.clear();
        email_sponsor.clear();
        nom_sponsor.clear();

    }

    @Override
    public void finalize() {
        if (voice != null) {
            voice.deallocate();
        }
    }
}
