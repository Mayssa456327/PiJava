package controllers;
import entities.User;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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

import utils.MyDB;
import utils.SessionManager;

public class Sign_in implements Initializable {
    public TextField nom;
    public TextField prenom;
    public TextField role;
    public TextField sexe;
    public TextField img;
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
    private PasswordField password_signup;
    @FXML
    private TextField adresse;
    @FXML
    private TextField email_signup;
    @FXML
    private Hyperlink mdp_oub;
    @FXML
    private Button login_selectShowPassword;

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

    public void login() throws IOException{
        if(email_signin.getText().equals("slim.ahmed@esprit.tn") && password_signin.getText().equals("adminadmin") )
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Doc Direct :: Success Message");
            alert.setHeaderText(null);
            alert.setContentText("Bienvenu Admin");
            alert.showAndWait();

            Parent root = FXMLLoader.load(getClass().getResource("/admin.fxml"));
            Scene scene;

            scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);

            stage.show();
        }else {

            String query2="select * from user where mail=?  and password=?";
            cnx = MyDB.getInstance().getConnection();
            try{
                PreparedStatement smt = cnx.prepareStatement(query2);

                smt.setString(1,email_signin.getText());
                smt.setString(2,password_signin.getText());
                ResultSet rs= smt.executeQuery();
                User p;
                if(rs.next()){
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

                    User.setCurrent_User(p);
                    SessionManager.getInstance().setUser(
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
                    login_btn.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("/AffichageUser.fxml"));
                    Scene scene;
                    scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);

                    stage.show();


                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("DocDirect :: Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Email/Password !!");
                    alert.showAndWait();
                }

            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }
    @FXML
    public void signUp() {
        cnx = MyDB.getInstance().getConnection();
        String query = "INSERT INTO user (nom, prenom, mail, role, numeroTelephone, password, ville, sexe, profile_image, is_verified) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            User user = new User();
            user.setNom(nom.getText());
            user.setPrenom(prenom.getText());
            user.setNumeroTelephone(numero.getText());
            user.setMail(email_signup.getText());
            user.setRole(role.getText());
            user.setPassword(password_signup.getText());
            user.setVille(adresse.getText());
            user.setSexe(sexe.getText());
            user.setProfileImage(img.getText());

            if (user.getNom().isEmpty()
                    || user.getPrenom().isEmpty()
                    || user.getNumeroTelephone().isEmpty()
                    || user.getMail().isEmpty()
                    || user.getPassword().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("DocDirect :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Enter all blank fields!");
                alert.showAndWait();
            } else if (password_signup.getText().length() < 8 || !Objects.equals(confirm_password.getText(), password_signup.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Travel Me :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Password must be at least 8 characters long!");
                alert.showAndWait();
            } else {
                if (ValidationEmail()) {
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
                    alert.setTitle("DocDirect :: WELCOME");
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
    // TO CLEAR ALL FIELDS OF REGISTRATION FORM
    public void registerClearFields() {
        nom.setText("");
        prenom.setText("");
        numero.setText("");
        email_signup.setText("");
        password_signup.setText("");
        role.setText("");
        img.setText("");
        sexe.setText("");
        adresse.setText("");
    }

    void sendPassword(){
        System.out.println("cxcccccccccccccccccc");
        String query2="select * from user where mail=? ";
        String email1="empty";
        try {
            PreparedStatement smt = cnx.prepareStatement(query2);
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
    // Method to send the password recovery email
    public void sendMail(String recepient){
        System.out.println("Preparing to send email");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.mailtrap.io");
        properties.put("mail.smtp.port", "2525"); // Use Mailtrap's SMTP port
        properties.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
        String myAccountEmail = "slim.ahmed@esprit.tn";
        String passwordd = "Hamouda007";

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

    @FXML
    void sendPaswword_btn(ActionEvent event) {
        //sendMail(email_signin.getText());
        sendPassword();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dropShadowEffect();

    }

    @FXML
    private void showPassword(MouseEvent event) {
        // Toggle the visibility of the password field
        password_signin.setManaged(true); // Ensure that the password field is managed
        password_signin.setVisible(!password_signin.isVisible());
        password_signin.setManaged(false); // Make the password field unmanaged again

    }

}
