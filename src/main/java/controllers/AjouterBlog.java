package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import entities.Blog;
import javafx.scene.input.MouseEvent;
import services.BlogService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
public class AjouterBlog {

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
    private Button uploadimagebtn;

    @FXML
    void AjouterBlog(ActionEvent event) {
        String type = typeentry.getText();
        String titre = titrey.getText();
        String contenu = contententry.getText();
        String image = imageentry.getText();

        typerr.setTextFill(javafx.scene.paint.Color.RED);
        titreerr.setTextFill(javafx.scene.paint.Color.RED);
        contenterr.setTextFill(javafx.scene.paint.Color.RED);
        addmessage.setText("");

        boolean hasError = false;
        StringBuilder errorMessages = new StringBuilder();

        if (type.isEmpty()) {
            errorMessages.append("Type is required\n");
            hasError = true;
        }

        if (titre.isEmpty()) {
            errorMessages.append("Title is required\n");
            hasError = true;
        }

        if (contenu.isEmpty()) {
            errorMessages.append("Content is required\n");
            hasError = true;
        }

        if (hasError) {
            typerr.setText(errorMessages.toString());
            return; // Stop execution if there are any errors
        }

        // Create blog object and add to database
        Blog blog = new Blog();
        blog.setType(type);
        blog.setTitre(titre);
        blog.setContent(contenu);
        blog.setImage(image);
        blog.setDate(LocalDateTime.now());

        BlogService blogService = new BlogService();
        blogService.create(blog);

        // Display success message in green
        addmessage.setText("Blog added successfully");
        addmessage.setTextFill(javafx.scene.paint.Color.GREEN);

        // Clear input fields after successful addition
        typeentry.clear();
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
    private Button goback;
    @FXML
    void goback(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherBlog.fxml"));
            Parent root = loader.load();

            // Get the stage from the event
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Affiche Blog");

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
