package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class NavigationController {
    private Stage stage;
    private Scene scene;
    private Parent root;
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
