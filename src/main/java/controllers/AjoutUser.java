package controllers;

import entities.User;
import services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AjoutUser {
    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfPrenom;

    // Ajoutez les nouveaux champs dans le contrôleur
    @FXML
    private TextField tfMail;

    @FXML
    private TextField tfRole;

    @FXML
    private TextField tfNumeroTelephone;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfVille;

    @FXML
    private TextField tfSexe;

    @FXML
    private TextField tfProfileImage;

    @FXML
    void addUser(ActionEvent event) throws IOException {
        ServiceUser serviceUser = new ServiceUser();

        // Créez un nouvel utilisateur avec les nouveaux attributs
        User user = new User(
                tfNom.getText(),
                tfPrenom.getText(),
                tfMail.getText(),
                tfRole.getText(),
                tfNumeroTelephone.getText(),
                tfPassword.getText(),
                tfVille.getText(),
                tfSexe.getText(),
                tfProfileImage.getText()
        );

        // Ajoutez l'utilisateur
        serviceUser.ajouter(user);

        // Affichez une alerte pour indiquer que l'ajout a réussi
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Utilisateur ajouté");
        alert.setContentText("Utilisateur ajouté avec succès !");
        alert.show();

        /*
        // Redirigez vers l'affichage des utilisateurs
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageUser.fxml"));
        Parent root = loader.load();
        tfNom.getScene().setRoot(root);

        // Passez les données de l'utilisateur à la vue d'affichage des utilisateurs
        AffichageUser apc = loader.getController();
        apc.setLbNom(tfNom.getText());
        apc.setLbPrenom(tfPrenom.getText());
         */
    }
}

