package controllers;
import entities.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;
import services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AjouterUser implements Initializable {
    @FXML
    private TextField NomM;
    @FXML
    private TextField idUser;
    @FXML
    private TextField PrenomM;
    @FXML
    private Button Ajouter_btn;
    @FXML
    private TextField NumeroM;
    @FXML
    private TextField roleM;
    @FXML
    private TextField email_signupM;
    @FXML
    private TextField VilleM;
    @FXML
    private TextField SexeM;
    @FXML
    private TextField PasswordM;

    private ServiceUser serviceUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serviceUser = new ServiceUser();
    }

    @FXML
    private void textfieldDesign1(MouseEvent event) {
    }
    @FXML
    public void return_btn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/admin.fxml")));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

        // Close the sign-in window
        ((Node)(event.getSource())).getScene().getWindow().hide();

    }
    @FXML
    private void textfieldDesign1(KeyEvent event) {
    }
    public void setText(User user)
    {
        String id =String.valueOf(user.getId());
        idUser.setText(id);
        NomM.setText(user.getNom());
        PrenomM.setText(user.getPrenom());
        NumeroM.setText(user.getNumeroTelephone());
        roleM.setText(user.getRole());
        email_signupM.setText(user.getMail());
        VilleM.setText(user.getVille());
        SexeM.setText(user.getSexe());
        PasswordM.setText(user.getPassword());
    }
    public void setTextFields(User R){
        idUser.setText(String.valueOf(R.getId()));
        NomM.setText(String.valueOf(R.getNom()));
        PrenomM.setText(R.getPrenom());
        roleM.setText(String.valueOf(R.getRole()));
        NumeroM.setText(R.getNumeroTelephone());
        email_signupM.setText(R.getMail());
        VilleM.setText(R.getVille());
        SexeM.setText(R.getSexe());
        PasswordM.setText(R.getPassword());
    }
    public void ajouterUser(ActionEvent event) {
        // Gather data from text fields
        String nom = NomM.getText();
        String prenom = PrenomM.getText();
        String mail = email_signupM.getText();
        String role = roleM.getText();
        String numeroTelephone = NumeroM.getText();
        String password = PasswordM.getText();
        String ville = VilleM.getText();
        String sexe = SexeM.getText();

        // Create a new User object with the gathered data
        User newUser = new User(0, nom, prenom, mail, role, numeroTelephone, password, ville, sexe);

        // Call the ajouter method from ServiceUser to add the user to the database
        serviceUser.ajouter(newUser);

        // Display a success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ajout Utilisateur");
        alert.setHeaderText(null);
        alert.setContentText("Utilisateur ajouté avec succès !");
        alert.showAndWait();

        // Clear the text fields after adding the user
        clearFields();
    }


    private void clearFields() {
        NomM.clear();
        PrenomM.clear();
        email_signupM.clear();
        roleM.clear();
        NumeroM.clear();
        PasswordM.clear();
        VilleM.clear();
        SexeM.clear();
    }
}

