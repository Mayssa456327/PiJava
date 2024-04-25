package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import entities.Blog;
import javafx.stage.Stage;
import services.BlogService;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class AfficherBlog {

    @FXML
    private ListView<Blog> bloglistview;

    @FXML
    private Button findby;

    @FXML
    private TextField findentry;

    @FXML
    private Button goback;




    @FXML
    void findbybtn(ActionEvent event) {
        String input = findentry.getText().trim();
        if (!input.isEmpty()) {
            BlogService blogService = new BlogService();
            List<Blog> searchResults = blogService.searchBlogs(input);

            bloglistview.getItems().clear();
            if (!searchResults.isEmpty()) {
                bloglistview.getItems().addAll(searchResults);
                // Notify the user about the successful search
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Results");
                alert.setHeaderText(null);
                alert.setContentText("Search completed. Found " + searchResults.size() + " blogs matching the search criteria.");
                alert.showAndWait();
            } else {
                // Notify the user if no blogs were found
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Results");
                alert.setHeaderText(null);
                alert.setContentText("No blogs found matching the search criteria.");
                alert.showAndWait();
            }
        } else {
            // Notify the user if the search term is empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Search Term");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a search term.");
            alert.showAndWait();
        }
    }




    public void initialize() {
        BlogService blogService = new BlogService();
        List<Blog> blogList = blogService.getAll();
        bloglistview.getItems().addAll(blogList);

        // Create a context menu for deleting, updating, and adding comments to a blog entry
        ContextMenu contextMenu = new ContextMenu();

        // Delete menu item
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(event -> deleteSelectedBlog());
        contextMenu.getItems().add(deleteMenuItem);

        // Update menu item
        MenuItem updateMenuItem = new MenuItem("Update");
        updateMenuItem.setOnAction(event -> updateSelectedBlog());
        contextMenu.getItems().add(updateMenuItem);

        // Add comment menu item
        MenuItem addCommentMenuItem = new MenuItem("Add Comment");
        addCommentMenuItem.setOnAction(event -> addCommentToSelectedBlog());
        contextMenu.getItems().add(addCommentMenuItem);

        // Associate the context menu with the ListView
        bloglistview.setContextMenu(contextMenu);
    }
    private void addCommentToSelectedBlog() {
        Blog selectedBlog = bloglistview.getSelectionModel().getSelectedItem();
        if (selectedBlog != null) {
            try {
                // Load the AjouterCommentaire view
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCommentaire.fxml"));
                Parent root = loader.load();

                AjouterCommentaire ajouterCommentaireController = loader.getController();

                ajouterCommentaireController.setBlogId(selectedBlog.getId());

                // Create a new stage for the AjouterCommentaire view
                Stage ajouterCommentaireStage = new Stage();
                ajouterCommentaireStage.setScene(new Scene(root));

                // Set the title of the AjouterCommentaire window
                ajouterCommentaireStage.setTitle("Add Comment");

                // Show the AjouterCommentaire window
                ajouterCommentaireStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void deleteSelectedBlog() {
        Blog selectedBlog = bloglistview.getSelectionModel().getSelectedItem();
        if (selectedBlog != null) {
            BlogService blogService = new BlogService();
            blogService.delete(selectedBlog.getId());

            // Remove the deleted blog from the ListView
            ObservableList<Blog> items = bloglistview.getItems();
            items.remove(selectedBlog);
        }
    }

    private void updateSelectedBlog() {
        Blog selectedBlog = bloglistview.getSelectionModel().getSelectedItem();
        if (selectedBlog != null) {
            try {
                // Load the update blog view
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateBlog.fxml"));
                Parent root = loader.load();

                // Get the controller for the update blog view
                UpdateBlog updateBlogController = loader.getController();

                // Pass the selected blog to the update blog controller
                updateBlogController.setBlogToUpdate(selectedBlog);

                // Create a new stage for the update blog view
                Stage updateBlogStage = new Stage();
                updateBlogStage.setScene(new Scene(root));

                // Set the title of the update blog window
                updateBlogStage.setTitle("Update Blog");

                // Show the update blog window
                updateBlogStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private Button seecomments;

    @FXML
    void seecommentson(ActionEvent event) {
        Blog selectedBlog = bloglistview.getSelectionModel().getSelectedItem();
        if (selectedBlog != null) {
            // Pass the selected blog ID to the AfficherCmnt controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCmnt.fxml"));
            Parent root;
            try {
                root = loader.load();
                AfficherCmnt Controller = loader.getController();
                Controller.loadCommentsByBlogId(selectedBlog.getId());

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Comments for Blog ID: " + selectedBlog.getId());
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please select a blog first.");
        }
    }

    @FXML
    private Button gotoadd;

    @FXML
    public void gotoaddon(ActionEvent actionEvent) {
        try {
            // Load the /add blog.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterBlog.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) gotoadd.getScene().getWindow();

            // Create a new stage for the /add blog.fxml
            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            newStage.setScene(scene);

            // Close the current stage
            stage.close();

            // Show the new stage
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
