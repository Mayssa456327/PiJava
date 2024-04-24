package controllers;
import entities.User;
import services.ServiceUser;
import java.net.URL;
import java.sql.SQLException;
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

public class ModifierUser implements Initializable {
    @FXML
    private TextField NomM;
    @FXML
    private TextField idUser;
    @FXML
    private TextField PrenomM;
    @FXML
    private Button modifier_btn;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serviceUser = new ServiceUser();
    }

    @FXML
    private void textfieldDesign1(MouseEvent event) {
    }

    @FXML
    private void textfieldDesign1(KeyEvent event) {
    }

    @FXML
    private void ModifierU(ActionEvent event) {
        // Gather data from text fields
        String nom = NomM.getText();
        String prenom = PrenomM.getText();
        String mail = email_signupM.getText();
        String role = roleM.getText();
        String numeroTelephone = NumeroM.getText();
        String password = PasswordM.getText();
        String ville = VilleM.getText();
        String sexe = SexeM.getText();

        // Get the user ID from the hidden field
        int userId = Integer.parseInt(idUser.getText());

        try {
            // Check if the user ID exists in the database
            if(serviceUser.idExists(userId)) {
                // Create a new User object with the updated data
                User updatedUser = new User(userId, nom, prenom, mail, role, numeroTelephone, password, ville, sexe);

                // Call the modifier method from ServiceUser to update the user in the database
                serviceUser.modifier(updatedUser);

                // Display a success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Modification Utilisateur");
                alert.setHeaderText(null);
                alert.setContentText("Utilisateur modifié avec succès !");
                alert.showAndWait();
            } else {
                // Display an error message if the user ID doesn't exist
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ID utilisateur non trouvé");
                alert.setHeaderText(null);
                alert.setContentText("L'ID utilisateur spécifié n'existe pas dans la base de données !");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur lors de la modification");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors de la modification de l'utilisateur. Veuillez réessayer !");
            alert.showAndWait();
        }
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
