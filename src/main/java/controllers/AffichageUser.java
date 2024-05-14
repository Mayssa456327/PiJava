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
import javafx.stage.StageStyle;
import utils.SessionManager;


import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.util.Objects;

public class AffichageUser {

    @FXML
    void certificatMedicale(ActionEvent event) {

    }

    @FXML
    void evenement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontEvenement.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void page1(ActionEvent event) throws IOException {
        Parent root = (Parent) FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/Certs.fxml")));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
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
    @FXML
    void gotoproduits(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view-product-user.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
