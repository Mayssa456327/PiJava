package controllers;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import entities.Blog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import services.BlogService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class AfficherBlog {
    private final BlogService MedecinS = new BlogService();

    @FXML
    private ListView<Blog> bloglistview;

    @FXML
    private Button findby;

    @FXML
    private TextField findentry;

    @FXML
    private Button goback;


    @FXML
    void RechercheNom(ActionEvent event) {
        String recherche = findentry.getText().trim(); // Get search query from text field

        // Clear existing items in the ListView
        bloglistview.getItems().clear();

        // Perform search and populate ListView with search results
        try {
            List<Blog> searchResults = MedecinS.Rechreche(recherche);

            // Add search results to the ListView
            bloglistview.getItems().addAll(searchResults);

        } catch (Exception e) {
            e.printStackTrace(); // Handle any exceptions
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
        try {
            // Load the /add blog.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateBlog.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) update.getScene().getWindow();

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

                // Get the current stage and update its root
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.getScene().setRoot(root);
                stage.setTitle("Comments for Blog ID: " + selectedBlog.getId());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please select a blog first.");
        }
    }

    @FXML
    void Stat(ActionEvent event) {
        Blog selectedBlog = bloglistview.getSelectionModel().getSelectedItem();
        if (selectedBlog != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interStat.fxml"));
            Parent root;
            try {
                root = loader.load();
                InterStat Controller = loader.getController();
                // Get the current stage from the event source
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.getScene().setRoot(root);
                stage.setTitle("Comments for Blog ID: " + selectedBlog.getId());

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
    private Button update;
    @FXML
    public void gotoaddon(ActionEvent actionEvent) {
        try {
            // Load the /add blog.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterBlog.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) gotoadd.getScene().getWindow();

            // Set the new root for the current scene
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void extract(ActionEvent event) {
        try {
            generatePDF();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void updateSelectedBlog1(ActionEvent event) {
        Blog selectedBlog = bloglistview.getSelectionModel().getSelectedItem();
        try {
            // Load the /UpdateBlog.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateBlog.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) update.getScene().getWindow();

            // Set the new root for the current scene
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generatePDF() throws FileNotFoundException {
        // Get the path to the Downloads directory
        String downloadsDir = System.getProperty("user.home") + "/Downloads/";

        // Create a PDF file in the Downloads directory
        File file = new File(downloadsDir + "blogs.pdf");
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdf = new PdfDocument(writer);

        // Create a document
        Document document = new Document(pdf);

        // Add content to the document
        for (Blog blog : MedecinS.getAll()) {
            document.add(new Paragraph("TYPE :       " + blog.getType()));
            document.add(new Paragraph("TITRE :    " + blog.getTitre()));
            document.add(new Paragraph("CONTENT:     " + blog.getContent()));

            document.add(new Paragraph("\n")); // Add a blank line between users
        }
        // Close the document
        document.close();

        System.out.println("PDF file generated successfully at: " + file.getAbsolutePath());
    }

    @FXML
    public void returntopanel(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageUser.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
