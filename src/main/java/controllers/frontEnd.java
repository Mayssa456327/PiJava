package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class frontEnd {
    public void exit(){
        System.exit(0);
    }
    @FXML
    void evenement( ActionEvent event)  {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/evenement.fxml"));
            Parent root = loader.load();
            ((Node)(event.getSource())).getScene().getWindow().hide();

            // Create a new stage for the loaded FXML scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("evenement");
            stage.initStyle(StageStyle.TRANSPARENT); // Optional, depending on your design
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading evenement: " + e.getMessage());
        }

    }
    @FXML
    void hopitals( ActionEvent event)  {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hopitals.fxml"));
            Parent root = loader.load();
            ((Node)(event.getSource())).getScene().getWindow().hide();

            // Create a new stage for the loaded FXML scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("hopitals");
            stage.initStyle(StageStyle.TRANSPARENT); // Optional, depending on your design
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading hopitals: " + e.getMessage());
        }

    }
    @FXML
    void blogs( ActionEvent event)  {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/blogs.fxml"));
            Parent root = loader.load();
            ((Node)(event.getSource())).getScene().getWindow().hide();

            // Create a new stage for the loaded FXML scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("blogs");
            stage.initStyle(StageStyle.TRANSPARENT); // Optional, depending on your design
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading blogs: " + e.getMessage());
        }

    }
    @FXML
    void certificatMedicale( ActionEvent event)  {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/certificat.fxml"));
            Parent root = loader.load();
            ((Node)(event.getSource())).getScene().getWindow().hide();

            // Create a new stage for the loaded FXML scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("certificat");
            stage.initStyle(StageStyle.TRANSPARENT); // Optional, depending on your design
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading certificat: " + e.getMessage());
        }

    }
    @FXML
    void labos( ActionEvent event)  {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/labos.fxml"));
            Parent root = loader.load();
            ((Node)(event.getSource())).getScene().getWindow().hide();

            // Create a new stage for the loaded FXML scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("labos");
            stage.initStyle(StageStyle.TRANSPARENT); // Optional, depending on your design
            stage.show();
        } catch (IOException e) {
            System.out.println("Error labos: " + e.getMessage());
        }

    }
    @FXML
    void sponsors( ActionEvent event)  {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sponsors.fxml"));
            Parent root = loader.load();
            ((Node)(event.getSource())).getScene().getWindow().hide();

            // Create a new stage for the loaded FXML scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("sponsors");
            stage.initStyle(StageStyle.TRANSPARENT); // Optional, depending on your design
            stage.show();
        } catch (IOException e) {
            System.out.println("Error sponsors: " + e.getMessage());
        }

    }
}
