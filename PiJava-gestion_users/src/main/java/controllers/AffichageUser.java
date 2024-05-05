package controllers;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utils.SessionManager;


import javax.swing.text.html.ImageView;
import java.io.IOException;

public class AffichageUser {

    @FXML
    void certificatMedicale(ActionEvent event) {

    }

    @FXML
    void evenement(ActionEvent event) {

    }

    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
    }


    @FXML
    void hopitals(ActionEvent event) {

    }

    @FXML
    void labos(ActionEvent event) {

    }

    @FXML
    void sponsors(ActionEvent event) {

    }
    @FXML
    private Label lbNom;

    @FXML
    private Label lbPrenom;

    public void setLbNom(String nom) {
        lbNom.setText(nom);
    }

    public void setLbPrenom(String prenom) {
        lbPrenom.setText(prenom);
    }
   // public void setLbAge(int age) {
       // lbPrenom.setText(String.valueOf(age));
    //}

    @FXML
    public void blogs(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherBlog.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
