
package com.example.pidevjava.controllers;

import com.example.pidevjava.models.Evenement;
import com.example.pidevjava.models.Sponsor;
import com.example.pidevjava.services.ServiceEvenement;
import com.example.pidevjava.services.ServiceSponsor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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
    private ChoiceBox<String>evenement_id;

    @FXML
    private Button modiferBtn;

    @FXML
    private TextField nom_sponsor;

    @FXML
    private Button suppBtn;

    @FXML
    private PieChart pieChart;

    private final ServiceSponsor SS = new ServiceSponsor();
    private final ServiceEvenement SE = new ServiceEvenement();

    private ServiceSponsor serviceSponsor;
    private ServiceEvenement serviceEvenement;
    private Sponsor selectedSponsor;
    private Evenement selectedEvenement;

    @FXML
    private Button statBtn;

    @FXML
    void OnClickedBack(ActionEvent event) {
        SE.changeScreen(event, "/com/example/pidevjava/Sign_in.fxml", " Accueil ");
    }


    @FXML
    void OnClickedModifierSponsor(ActionEvent event) throws SQLException {
        if (selectedSponsor != null) {
            String budgetText = budget.getText();
            Integer eventId = SE.getEventIdByName(evenement_id.getValue());
            String nomSponsorText = nom_sponsor.getText();
            String emailSponsorText = email_sponsor.getText();
            String adresseText = adresse.getText();

            if (budgetText.isEmpty() || eventId == null || nomSponsorText.isEmpty() ||
                    emailSponsorText.isEmpty() || adresseText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis.");
                return;
            }

            if (!emailSponsorText.toLowerCase().endsWith("@gmail.com")) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "L’e-mail doit se terminer par @gmail.com");
                return;
            }

            try {
                selectedSponsor.setBudget(Double.parseDouble(budgetText));
                selectedSponsor.setEvenement_id(eventId);
                selectedSponsor.setNom_sponsor(nomSponsorText);
                selectedSponsor.setEmail_sponsor(emailSponsorText);
                selectedSponsor.setAdresse(adresseText);
                serviceSponsor.update(selectedSponsor);
                loadReservations();
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Budget invalide.");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la mise à jour du sponsor.");
            }
        }
    }


    @FXML
    void OnClcickedStatSponsor(ActionEvent event) {
        generateStatistics();
    }

    @FXML
    void OnClickedSupprimerSponsor(ActionEvent event) {
        if (selectedSponsor != null) {
            try {
                serviceSponsor.delete(selectedSponsor);
                loadReservations();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression du sponsor.");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceSponsor = new ServiceSponsor();
        loadEventNames();
        loadReservations();
        generateStatistics();

        budget.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                budget.setText(oldValue);
            }
        });
    }

    private void loadEventNames() {
        try {
            List<String> eventNames = SE.getAllEventNames();
            evenement_id.setItems(FXCollections.observableArrayList(eventNames));
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des noms d'événement.");
        }
    }

    private void generateStatistics() {
        pieChart.getData().clear();
        try {
            List<Sponsor> sponsors = SS.getAll();
            sponsors.sort(Comparator.comparing(Sponsor::getNom_sponsor));
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            double totalBudget = sponsors.stream().mapToDouble(Sponsor::getBudget).sum();
            for (Sponsor sponsor : sponsors) {
                double percentage = (sponsor.getBudget() / totalBudget) * 100;
                PieChart.Data slice = new PieChart.Data(sponsor.getNom_sponsor(), sponsor.getBudget());
                slice.nodeProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        newValue.setOnMouseClicked(event -> showAlert(Alert.AlertType.INFORMATION, "Pourcentage",
                                String.format("%.2f%%", percentage)));
                    }
                });
                pieChartData.add(slice);
            }
            pieChart.setData(pieChartData);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert1(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la génération des statistiques.");
        }
    }
    private void addPercentageLabel(PieChart.Data slice, String percentage) {
        Tooltip tooltip = new Tooltip(percentage);
        Tooltip.install(slice.getNode(), tooltip);
    }


    private void showAlert1(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void loadReservations() {
        SponsorVBox.getChildren().clear();
        try {
            List<Sponsor> sponsors = SS.getAll();
            for (Sponsor sponsor : sponsors) {
                VBox sponsorContainer = new VBox();
                sponsorContainer.setStyle("-fx-background-color: #f2f2f2; -fx-padding: 10px; -fx-spacing: 5px;");

                GridPane gridPane = new GridPane();
                gridPane.setStyle("-fx-padding: 5px;");
                gridPane.setVgap(5);
                gridPane.setHgap(10);

                Label nomLabel = new Label("Nom:");
                Label nomValue = new Label(sponsor.getNom_sponsor());
                Label evenementLabel = new Label("Nom de l'événement:");
                Label evenementValue = new Label(SE.getNomEvenementById(sponsor.getEvenement_id()));
                Label emailLabel = new Label("Email:");
                Label emailValue = new Label(sponsor.getEmail_sponsor());
                Label adresseLabel = new Label("Adresse:");
                Label adresseValue = new Label(sponsor.getAdresse());
                Label budgetLabel = new Label("Budget:");
                Label budgetValue = new Label(String.valueOf(sponsor.getBudget()));

                gridPane.addRow(0, nomLabel, nomValue);
                gridPane.addRow(1, evenementLabel, evenementValue);
                gridPane.addRow(2, emailLabel, emailValue);
                gridPane.addRow(3, adresseLabel, adresseValue);
                gridPane.addRow(4, budgetLabel, budgetValue);

                Button selectButton = new Button("Sélectionner");
                selectButton.setOnAction(event -> {
                    selectedSponsor = sponsor;
                    budget.setText(String.valueOf(sponsor.getBudget()));
                    evenement_id.setValue(String.valueOf(sponsor.getEvenement_id()));
                    nom_sponsor.setText(sponsor.getNom_sponsor());
                    email_sponsor.setText(sponsor.getEmail_sponsor());
                    adresse.setText(sponsor.getAdresse());
                });
                selectButton.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white;");
                sponsorContainer.getChildren().addAll(gridPane, selectButton);
                SponsorVBox.getChildren().add(sponsorContainer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des sponsors.");
        }

    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
