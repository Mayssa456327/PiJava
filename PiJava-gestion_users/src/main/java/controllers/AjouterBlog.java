package controllers;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import entities.Blog;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import services.BlogService;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.text.html.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.UUID;
public class AjouterBlog implements Initializable {
    private final BlogService MedecinS = new BlogService();
    @FXML
    private Label addmessage;

    @FXML
    private Button btnddlg;

    @FXML
    private TextArea contententry;

    @FXML
    private Label contenterr;

    @FXML
    private TextField imageentry;

    @FXML
    private Label titreerr;

    @FXML
    private TextField titrey;

    @FXML
    private Label ty;

    @FXML
    private Label imageerr;
    @FXML
    private TextField typeentry;

    @FXML
    private Label typerr;
    @FXML
    public ComboBox<String> specialiteComboBox;

    @FXML
    private ImageView imagepdp;

    private void sendEmail(String toEmail, String msg) {
        final String username = "waves.esprit@gmail.com";
        final String password = "tgao tbqg wudl aluo";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("waves.esprit@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Rendez-Vous");
            message.setText(msg);
            Transport.send(message);
            System.out.println("OTP email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void AjouterBlog(ActionEvent event) {
        String titre = titrey.getText();
        String contenu = contententry.getText();
        String image = imageentry.getText();
        String TYPE = specialiteComboBox.getValue();

        typerr.setTextFill(javafx.scene.paint.Color.RED);
        titreerr.setTextFill(javafx.scene.paint.Color.RED);
        contenterr.setTextFill(javafx.scene.paint.Color.RED);
        addmessage.setText("");

        boolean hasError = false;
        StringBuilder errorMessages = new StringBuilder();

        if (titre.isEmpty()) {
            errorMessages.append("Title is required\n");
            hasError = true;
        }

        if (contenu.isEmpty()) {
            errorMessages.append("Content is required\n");
            hasError = true;
        } else if (contenu.length() < 20) {
            errorMessages.append("Content must be at least 20 characters long\n");
            hasError = true;
        }

        if (hasError) {
            contenterr.setText(errorMessages.toString());
            return; // Stop execution if there are any errors
        }

        // Create blog object and add to database
        Blog blog = new Blog();
        blog.setType(TYPE);
        blog.setTitre(titre);
        blog.setContent(contenu);
        blog.setImage(image);
        blog.setDate(LocalDateTime.now());

        BlogService blogService = new BlogService();
        blogService.create(blog);

        // Display success message in green
        addmessage.setText("Blog added successfully");
        String msg = "Votre blog a été posté.\n\n" +
                "Détails du Blog :\n" +
                "Type :  : " + TYPE.toString() + "\n" +
                "Titre : " + titre.toString()  + "\n\n" +
                "Contenu : " + contenu.toString() + "\n" +
                "Merci pour votre publication !";

        // Envoi de l'email avec le message contenant les détails du rendez-vous
        sendEmail("emna.ounissi@esprit.tn", msg);
        addmessage.setTextFill(javafx.scene.paint.Color.GREEN);

        // Clear input fields after successful addition
        titrey.clear();
        contententry.clear();
        imageentry.clear();
    }

    @FXML
    void uploadimage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        Stage stage = (Stage) btnddlg.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            String imagePath = ((File) selectedFile).getAbsolutePath();
            imageentry.setText(imagePath); // Set the path of the selected image file in the TextField
        }
    }

    public void goforward(MouseEvent mouseEvent) {
    }
    @FXML
    void goback(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherBlog.fxml"));
            Parent root = loader.load();

            // Get the stage from the event
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new root for the current scene
            stage.getScene().setRoot(root);
            stage.setTitle("Affiche Blog");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> specialites = FXCollections.observableArrayList(
                "Aricle", "Image", "Vidéo"); // List of specialities
        specialiteComboBox.setItems(specialites);
    }
    public void uploadimagebtn(ActionEvent actionEvent) {
        String imagePath = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        Stage stage = (Stage) titreerr.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                Path destinationFolder = Paths.get("src/main/resources/img");
                if (!Files.exists(destinationFolder)) {
                    Files.createDirectories(destinationFolder);
                }
                String fileName = UUID.randomUUID().toString() + "_" + selectedFile.getName();
                Path destinationPath = destinationFolder.resolve(fileName);
                Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                imagePath = destinationPath.toString();
                System.out.println("Image uploaded successfully: " + imagePath);
                imageerr.setText(imagePath);
                if (imagePath != null) {
                    try {
                        File file = new File(imagePath);
                        FileInputStream inputStream = new FileInputStream(file);
                        Image image = new Image(inputStream);
                        imagepdp.getImage();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
