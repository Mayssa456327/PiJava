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
import java.util.Random;
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
public class ResetPassword implements Initializable {
    @FXML
    private TextField email_reset;
    @FXML
    private TextField verify_code;
    @FXML
    private Button send_btn;
    @FXML
    private Button verify_c;

    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    int randomCode;
    public void textfieldDesign(){
        if (email_reset.isFocused()){
            email_reset.setStyle("-fx-background-color:#fff;"
                    + "-fx-border-width:2px");
            verify_code.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");


        }else if (verify_code.isFocused()){
            email_reset.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            verify_code.setStyle("-fx-background-color:#fff;"
                    + "-fx-border-width:2px");
        }
    }
    @FXML
    public void send() {
        try {
            Random rand = new Random();
            randomCode = rand.nextInt(999999);

            String user = "88ea3361e00c9b"; // Your Gmail address
            String pass = "84911dab728d75"; // Your Gmail password
            String to = email_reset.getText();
            String subject = "Resetting Code";
            String message = "Your reset code is " + randomCode;
            boolean sessionDebug = false;
            Properties pros = System.getProperties();
            pros.put("mail.smtp.starttls.enable", "true");
            pros.put("mail.smtp.host","sandbox.smtp.mailtrap.io");
            pros.put("mail.smtp.port", "2525"); // Gmail SMTP port
            pros.put("mail.smtp.auth", "true");
            pros.put("mail.smtp.ssl.trust", "sandbox.smtp.mailtrap.io");



            Session mailSession = Session.getInstance(pros, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pass);
                }
            });

            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(user));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Doc Direct :: Success Message");
            alert.setHeaderText(null);
            alert.setContentText("Email has been sent!!");
            alert.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DocDirect :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("ERROR !!");
            alert.showAndWait();
        }
    }

    @FXML
    public void verify() {
        try {
            String inputCode = verify_code.getText();
            if (inputCode.isEmpty()) {
                // Show an error if the verification code field is empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("DocDirect :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Verification code cannot be empty!");
                alert.showAndWait();
                return;
            }

            int enteredCode = Integer.parseInt(inputCode);
            if (enteredCode == randomCode) {
                // Verification successful, perform appropriate action
                // For example, show the Reset interface
                // Replace "Reset" with the appropriate controller or component to display
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reset.fxml"));
                Parent resetInterface = loader.load();
                // Access the controller and set/reset any necessary data
                ResettController resetController = loader.getController();
                //resetController.initData(email_reset.getText());
                // Show the reset interface
                Stage stage = new Stage();
                stage.setScene(new Scene(resetInterface));
                stage.show();
                // Close the current stage or hide the current interface if needed
                ((Node) (verify_code)).getScene().getWindow().hide();
            } else {
                // Show an error if the entered code doesn't match
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("DocDirect :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Verification code does not match!");
                alert.showAndWait();
            }
        } catch (NumberFormatException ex) {
            // Handle the case where the entered code is not a valid integer
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DocDirect :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid verification code format!");
            alert.showAndWait();
        } catch (IOException ex) {
            // Handle any IO exceptions that might occur while loading the Reset interface
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DocDirect :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Error loading the Reset interface!");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
