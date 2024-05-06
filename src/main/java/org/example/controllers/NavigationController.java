package org.example.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class NavigationController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void backHopital(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("backAdmin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.show();
    }
    public void backReservation(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("backReservation.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.show();
    }

}
