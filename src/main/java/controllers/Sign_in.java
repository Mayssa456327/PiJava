package controllers;
import entities.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import services.ServiceUser;
import utils.MyDB;
import utils.SessionManager;

public class Sign_in implements Initializable {

    public TextField nom;
    public TextField prenom;
    public ComboBox role;
    public ComboBox sexe;
    ServiceUser sv = new ServiceUser();
    @FXML
    public Label DocDirect;
    @FXML
    public Label DocDirect2;
    @FXML
    private TextField email_signin;
    @FXML
    private PasswordField password_signin;
    @FXML
    private Button login_btn;
    @FXML
    private Label nomErrorLabel;

    @FXML
    private Label prenomErrorLabel;

    @FXML
    private Label emailErrorLabel;
    @FXML
    private Hyperlink create_acc;
    @FXML
    private Button signup_btn;
    @FXML
    private Hyperlink login_acc;
    @FXML
    private AnchorPane signup_form;
    @FXML
    private AnchorPane login_form;
    @FXML
    private PasswordField confirm_password;
    @FXML
    private TextField numero;
    @FXML
    private TextField login_showPassword;
    @FXML
    private PasswordField password_signup;
    @FXML
    private TextField adresse;
    @FXML
    private TextField email_signup;
    @FXML
    private Hyperlink mdp_oub;
    @FXML
    private Button login_selectShowPassword;
    @FXML
    private Button img;

    @FXML
    private ImageView image_view;
    @FXML
    private Label file_path;
    /**
     * Initializes the controller class.
     */

    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    public void exit(){
        System.exit(0);
    }
    public void textfieldDesign(){
        if (email_signin.isFocused()){
            email_signin.setStyle("-fx-background-color:#fff;"
                    + "-fx-border-width:2px");
            password_signin.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");


        }else if (password_signin.isFocused()){
            email_signin.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            password_signin.setStyle("-fx-background-color:#fff;"
                    + "-fx-border-width:2px");
        }
    }
    public void textfieldDesign1(){
        if(email_signup.isFocused()){
            email_signup.setStyle("-fx-background-color:#fff;"
                    + "-fx-border-width:2px");
            password_signup.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            nom.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            prenom.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            sexe.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            role.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            img.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            adresse.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            numero.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            confirm_password.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
        } else if(password_signup.isFocused()){
            email_signup.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            password_signup.setStyle("-fx-background-color:#fff;"
                    + "-fx-border-width:2px");
            nom.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            prenom.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            sexe.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            role.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            img.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            adresse.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            numero.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            confirm_password.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
        }  else if(nom.isFocused()){
            email_signup.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            password_signup.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            nom.setStyle("-fx-background-color:#fff;"
                    + "-fx-border-width:2px");
            prenom.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            adresse.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            sexe.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            role.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            img.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            numero.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            confirm_password.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
        } else if(prenom.isFocused()){
            email_signup.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            password_signup.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            nom.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            nom.setStyle("-fx-background-color:#fff;"
                    + "-fx-border-width:2px");
            adresse.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            sexe.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            role.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            img.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            numero.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            confirm_password.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
        } else if(adresse.isFocused()){
            email_signup.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            password_signup.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            nom.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            prenom.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            adresse.setStyle("-fx-background-color:#fff;"
                    + "-fx-border-width:2px");
            numero.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            sexe.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            role.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            img.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            confirm_password.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
        } else if(numero.isFocused()){
            email_signup.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            password_signup.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            nom.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            prenom.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            adresse.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            numero.setStyle("-fx-background-color:#fff;"
                    + "-fx-border-width:2px");
            confirm_password.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            sexe.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            role.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            img.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
        }  else if(confirm_password.isFocused()){
            email_signup.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            password_signup.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            nom.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            prenom.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            sexe.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            role.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            img.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            adresse.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            numero.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            confirm_password.setStyle("-fx-background-color:#fff;"
                    + "-fx-border-width:2px");
        }else if(sexe.isFocused()){
            email_signup.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            password_signup.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            nom.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            prenom.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            confirm_password.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            role.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            img.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            adresse.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            numero.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            sexe.setStyle("-fx-background-color:#fff;"
                    + "-fx-border-width:2px");
        }else if(img.isFocused()){
            email_signup.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            password_signup.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            nom.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            prenom.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            confirm_password.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            role.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            sexe.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            adresse.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            numero.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            img.setStyle("-fx-background-color:#fff;"
                    + "-fx-border-width:2px");
        }else if(role.isFocused()){
            email_signup.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            password_signup.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            nom.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            prenom.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            confirm_password.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            sexe.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            img.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            adresse.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            numero.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            role.setStyle("-fx-background-color:#fff;"
                    + "-fx-border-width:2px");
        }

    }
    public void dropShadowEffect(){
        DropShadow original = new DropShadow(20,Color.valueOf("#517ab5"));
        original.setRadius(30);
        DocDirect.setEffect(original);

        DocDirect2.setEffect(original);
        DocDirect.setOnMouseEntered((MouseEvent event)->{
            DropShadow shadow = new DropShadow(60,Color.valueOf("#517ab5"));
            DocDirect.setCursor(Cursor.HAND);
            DocDirect.setStyle("-fx-text-fill:#5128c9");
            DocDirect.setEffect(shadow);

        });
        DocDirect.setOnMouseExited((MouseEvent event)->{
            DropShadow shadow = new DropShadow(20,Color.valueOf("#517ab5"));
            shadow.setRadius(30);
            DocDirect.setStyle("-fx-text-fill:#000");
            DocDirect.setEffect(shadow);
        });
        DocDirect2.setOnMouseEntered((MouseEvent event)->{
            DropShadow shadow = new DropShadow(60,Color.valueOf("#517ab5"));
            DocDirect2.setCursor(Cursor.HAND);
            DocDirect2.setStyle("-fx-text-fill:#4c22c7");
            DocDirect2.setEffect(shadow);

        });
        DocDirect2.setOnMouseExited((MouseEvent event)->{
            DropShadow shadow = new DropShadow(20,Color.valueOf("#517ab5"));
            shadow.setRadius(30);
            DocDirect2.setStyle("-fx-text-fill:#000");
            DocDirect2.setEffect(shadow);
        });
    }
    public void changeForm(ActionEvent event){
        if(event.getSource() == create_acc){
            signup_form.setVisible(true);
            login_form.setVisible(false);
        }else if(event.getSource()==login_acc){
            login_form.setVisible(true);
            signup_form.setVisible(false);
        }
    }

    public boolean ValidationEmail(){
        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9._]+([.][a-zA-Z0-9]+)+");
        Matcher match = pattern.matcher(email_signup.getText());

        if(match.find() && match.group().equals(email_signup.getText()))
        {
            return true;
        }else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Email");
            alert.showAndWait();

            return false;
        }
    }

    public void login(ActionEvent event) throws IOException {
        if (email_signin.getText().equals("slim.ahmed@esprit.tn") && password_signin.getText().equals("adminadmin")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Doc Direct :: Success Message");
            alert.setHeaderText(null);
            alert.setContentText("Bienvenu Admin");
            alert.showAndWait();

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/admin.fxml")));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();

            // Close the sign-in window
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } else {
            String query2 = "select * from user where mail=?  and password=?";
            cnx = MyDB.getInstance().getConnection();
            try {
                PreparedStatement smt = cnx.prepareStatement(query2);
                smt.setString(1, email_signin.getText());
                smt.setString(2, password_signin.getText());
                ResultSet rs = smt.executeQuery();
                User p;
                if (rs.next()) {
                    p = new User(
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("mail"),
                            rs.getString("role"),
                            rs.getString("numeroTelephone"),
                            rs.getString("password"),
                            rs.getString("ville"),
                            rs.getString("sexe"),
                            rs.getString("profile_image"),
                            rs.getBoolean("is_verified"),
                            rs.getBoolean("status"),
                            rs.getString("reset_token")
                    );
                    p.setId(rs.getInt("id"));
                    User.setCurrent_User(p);
                    SessionManager.setUser(
                            rs.getInt("id"),
                            new User(
                                    rs.getString("nom"),
                                    rs.getString("prenom"),
                                    rs.getString("mail"),
                                    rs.getString("role"),
                                    rs.getString("numeroTelephone"),
                                    rs.getString("password"),
                                    rs.getString("ville"),
                                    rs.getString("sexe"),
                                    rs.getString("profile_image"),
                                    rs.getBoolean("is_verified"),
                                    rs.getBoolean("status"),
                                    rs.getString("reset_token")
                            )
                    );

                    System.out.println(User.Current_User.getMail());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("DocDirect :: Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Vous etes connecté");
                    alert.showAndWait();

                    // Close the sign-in window
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();

                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AffichageUser.fxml")));
                    Scene scene = new Scene(root);
                    Stage newStage = new Stage();
                    newStage.initStyle(StageStyle.TRANSPARENT);
                    newStage.setScene(scene);
                    newStage.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("DocDirect :: Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Email/Password !!");
                    alert.showAndWait();
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    private boolean validateFields() {
        boolean isValid = true;

        if (nom.getText().isEmpty()) {
            nomErrorLabel.setText("Le nom est requis.");
            nomErrorLabel.setVisible(true);
            isValid = false;
        } else if (!nom.getText().matches("[a-zA-Z]+")) {
            nomErrorLabel.setText("Le nom doit contenir uniquement des lettres.");
            nomErrorLabel.setVisible(true);
            isValid = false;
        } else {
            nomErrorLabel.setVisible(false);
        }

        if (prenom.getText().isEmpty()) {
            prenomErrorLabel.setText("Le prénom est requis.");
            prenomErrorLabel.setVisible(true);
            isValid = false;
        } else if (!prenom.getText().matches("[a-zA-Z]+")) {
            prenomErrorLabel.setText("Le prénom doit contenir uniquement des lettres.");
            prenomErrorLabel.setVisible(true);
            isValid = false;
        } else {
            prenomErrorLabel.setVisible(false);
        }

        // Valider l'email
        if (email_signup.getText().isEmpty()) {
            emailErrorLabel.setText("L'email est requis.");
            emailErrorLabel.setVisible(true);
            isValid = false;
        } else if (!email_signup.getText().contains("@")) {
            emailErrorLabel.setText("L'email doit contenir un '@'.");
            emailErrorLabel.setVisible(true);
            isValid = false;
        } else {
            emailErrorLabel.setVisible(false);
        }

        // Valider les autres champs de la même manière

        return isValid;
    }
    @FXML
    public void signUp() {
        if (!validateFields()) {
            return;
        }
        cnx = MyDB.getInstance().getConnection();
        String query = "INSERT INTO user (nom, prenom, mail, role, numeroTelephone, password, ville, sexe, profile_image, is_verified) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            User user = new User();
            user.setNom(nom.getText());
            user.setPrenom(prenom.getText());
            user.setNumeroTelephone(numero.getText());
            user.setMail(email_signup.getText());
            user.setRole(role.getSelectionModel().getSelectedItem().toString());
            user.setSexe(sexe.getSelectionModel().getSelectedItem().toString());
            user.setPassword(password_signup.getText());
            user.setVille(adresse.getText());
            user.setProfileImage(file_path.getText());

            if (user.getNom().isEmpty()
                    || user.getPrenom().isEmpty()
                    || user.getNumeroTelephone().isEmpty()
                    || user.getMail().isEmpty()
                    || user.getPassword().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("DocDirect :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Enter all required fields!");
                alert.showAndWait();
            } else if (password_signup.getText().length() < 8 || !Objects.equals(confirm_password.getText(), password_signup.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("DocDirect :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Password must be at least 8 characters long and match the confirmation!");
                alert.showAndWait();
            } else if (!ValidationEmail()) {
                // L'email est invalide
            } else if (!user.getNom().matches("[a-zA-Z]+") || !user.getPrenom().matches("[a-zA-Z]+")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("DocDirect :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Name and surname should only contain letters!");
                alert.showAndWait();
            } else if (!user.getSexe().equalsIgnoreCase("homme") && !user.getSexe().equalsIgnoreCase("femme")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("DocDirect :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Gender should be either 'homme' or 'femme'!");
                alert.showAndWait();

            } else if (!user.getNumeroTelephone().matches("\\d{8}")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("DocDirect :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Phone number must contain 8 digits!");
                alert.showAndWait();
            } else {
                // Vérifier si l'email, le nom ou le prénom existent déjà dans la base de données
                String checkQuery = "SELECT * FROM user WHERE mail = ? OR nom = ? OR prenom = ?";
                PreparedStatement checkStatement = cnx.prepareStatement(checkQuery);
                checkStatement.setString(1, user.getMail());
                checkStatement.setString(2, user.getNom());
                checkStatement.setString(3, user.getPrenom());
                ResultSet resultSet = checkStatement.executeQuery();

                if (resultSet.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("DocDirect :: Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Email, name, or surname already exists in the database!");
                    alert.showAndWait();
                } else {
                    // Insérer l'utilisateur dans la base de données
                    PreparedStatement smt = cnx.prepareStatement(query);
                    smt.setString(1, user.getNom());
                    smt.setString(2, user.getPrenom());
                    smt.setString(3, user.getMail());
                    smt.setString(4, user.getRole());
                    smt.setString(5, user.getNumeroTelephone()); // Corrected order
                    smt.setString(6, user.getPassword());
                    smt.setString(7, user.getVille());
                    smt.setString(8, user.getSexe());
                    smt.setString(9, user.getProfileImage());
                    smt.setBoolean(10, false); // or true depending on your logic

                    smt.executeUpdate();

                    System.out.println("Added successfully");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("DocDirect :: Welcome");
                    alert.setHeaderText(null);
                    alert.setContentText("You are registered!");
                    alert.showAndWait();

                    registerClearFields();
                    login_form.setVisible(true);
                    signup_form.setVisible(false);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void insertImage(){

        FileChooser open = new FileChooser();

        Stage stage = (Stage)signup_form.getScene().getWindow();

        File file = open.showOpenDialog(stage);

        if(file != null){

            String path = file.getAbsolutePath();

            path = path.replace("\\", "\\\\");

            file_path.setText(path);

            Image image = new Image(file.toURI().toString(), 110, 110, false, true);

            image_view.setImage(image);

        }else{

            System.out.println("NO DATA EXIST!");

        }
    }
    // TO CLEAR ALL FIELDS OF REGISTRATION FORM
    public void registerClearFields() {
        nom.setText("");
        prenom.setText("");
        numero.setText("");
        email_signup.setText("");
        password_signup.setText("");
        role.getSelectionModel().clearSelection();
        img.setText("");
        sexe.getSelectionModel().clearSelection();
        adresse.setText("");
        confirm_password.setText("");
        image_view.setImage(null);
    }
    @FXML
    void resetPaswword_btn( ActionEvent event)  {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ResetPassword.fxml"));
            Parent root = loader.load();
            ((Node)(event.getSource())).getScene().getWindow().hide();

            // Create a new stage for the loaded FXML scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Reset Password");
            stage.initStyle(StageStyle.TRANSPARENT); // Optional, depending on your design
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading Reset Password: " + e.getMessage());
        }
        //sendMail(email_signin.getText());
        //sendPassword();
    }
    @FXML
    void sendPaswword_btn( ) throws SQLException {
        sendPassword();
    }

    void sendPassword() throws SQLException {
        PreparedStatement smt=sv.sendPass();
        String email1="empty";
        try {
            //PreparedStatement smt = cnx.prepareStatement(query2);
            smt.setString(1, email_signin.getText());
            ResultSet rs= smt.executeQuery();
            if(rs.next()){
                email1=rs.getString("mail");
                System.out.println(email1);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        sendMail(email1);
    }
    public void sendMail(String recepient){
        System.out.println("Preparing to send email");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        //properties.put("mail.smtp.host","smtp.gmail.com");
        //properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.host","sandbox.smtp.mailtrap.io");
        properties.put("mail.smtp.port","2525");
        String myAccountEmail = "88ea3361e00c9b";
        String passwordd = "84911dab728d75";

        Session session = Session.getInstance(properties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myAccountEmail,passwordd);
            }
        });
        Message message =preparedMessage(session,myAccountEmail,recepient);
        try{
            Transport.send(message);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DocDirect :: Boite Mail");
            alert.setHeaderText(null);
            alert.setContentText("consulter votre boite mail !!");
            alert.showAndWait();

        }catch(Exception ex){
            ex.printStackTrace();

        }

    }
    private Message preparedMessage(Session session, String myAccountEmail, String recepient){
        String query2="select * from user where mail=?";
        String userEmail="null" ;
        String pass="empty";
        try {
            //cnx = MyConnection.getInstance().getCnx();
            PreparedStatement smt = cnx.prepareStatement(query2);
            smt.setString(1, email_signin.getText());
            ResultSet rs= smt.executeQuery();
            System.out.println(rs);
            if(rs.next()){
                pass=rs.getString("password");
                userEmail=rs.getString("mail");                }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.print("c est en cours");
        String text="Votre mot de pass est :"+pass+"";
        String object ="Recupération de mot de passe";
        Message message = new MimeMessage(session);
        try{
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            message.setSubject(object);
            String htmlcode ="<h1> "+text+" </h1> <h2> <b> </b2> </h2> ";
            message.setContent(htmlcode, "text/html");
            System.out.println("Message envoyeer");

            System.out.println(pass);

            return message;

        }catch(MessagingException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dropShadowEffect();
        cnx = MyDB.getInstance().getConnection();

    }

    @FXML
    private void showPassword(MouseEvent event) {
        // Toggle the visibility of the password field

            login_showPassword.setText(password_signin.getText());
            login_showPassword.setVisible(true);
            password_signin.setVisible(false);



    }

}
