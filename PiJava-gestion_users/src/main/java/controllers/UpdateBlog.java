package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import entities.Blog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import services.BlogService;

import java.io.IOException;

public class UpdateBlog {

    @FXML
    private TextField typeField;

    @FXML
    private TextField titreField;

    @FXML
    private TextArea contenuArea;

    @FXML
    private TextField imageField;
    @FXML
    private ImageView imagedisplay;
    // Inject the BlogService
    private final BlogService blogService = new BlogService();

    private Blog blogToUpdate; // Store the blog to be updated

    // Setter method to set the blog to be updated
    public void setBlogToUpdate(Blog blog) {
        this.blogToUpdate = blog;
        // Populate the fields with the current values of the blog
        if (blog != null) {
            typeField.setText(blog.getType());
            titreField.setText(blog.getTitre());
            contenuArea.setText(blog.getContent());
            imageField.setText(blog.getImage());

            // Load and set the image to the ImageView
            String imageUrl = blog.getImage();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                try {
                    Image image = new Image(imageUrl);
                    imagedisplay.setImage(image);
                } catch (Exception e) {
                    // Handle image loading error
                    e.printStackTrace();
                }
            } else {
                // If the image URL is null or empty, clear the ImageView
                imagedisplay.setImage(null);
            }
        }
    }
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
    @FXML
    void updateBlog(ActionEvent event) {
        if (blogToUpdate != null) {
            // Update the blog details
            blogToUpdate.setType(typeField.getText());
            blogToUpdate.setTitre(titreField.getText());
            blogToUpdate.setContent(contenuArea.getText());
            blogToUpdate.setImage(imageField.getText());

            // Call the update method from the BlogService
            blogService.update(blogToUpdate);

        }
    }


}
