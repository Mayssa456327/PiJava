package controllers;

import entities.Comment;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.CommentService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherCmnt implements Initializable {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<Comment> commentListView;

    private CommentService commentService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        commentService = new CommentService();
        populateCommentListView();
        setupCellFactory();
    }

    private void populateCommentListView() {
        List<Comment> commentList = commentService.getAll();
        ObservableList<Comment> observableList = FXCollections.observableArrayList(commentList);
        commentListView.setItems(observableList);
    }

    private void setupCellFactory() {
        commentListView.setCellFactory(new Callback<ListView<Comment>, ListCell<Comment>>() {
            @Override
            public ListCell<Comment> call(ListView<Comment> param) {
                return new CommentListCell();
            }
        });

        // Allow selection of multiple items in the ListView
        commentListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Add a context menu to each item in the ListView
        ContextMenu contextMenu = new ContextMenu();
        MenuItem editMenuItem = new MenuItem("Edit");
        editMenuItem.setOnAction(event -> {
            Comment selectedComment = commentListView.getSelectionModel().getSelectedItem();
            if (selectedComment != null) {
                // Open a new window or dialog to edit the selected comment
                openEditCommentWindow(selectedComment.getId());
            }
        });
        contextMenu.getItems().add(editMenuItem);
        commentListView.setContextMenu(contextMenu);
    }

    private void openEditCommentWindow(int commentId) {
        try {
            // Load the FXML file for the modification page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifcomnt.fxml"));
            Parent root = loader.load();

            // Pass the comment ID to the controller of the modification page
            Modifcomnt controller = loader.getController();
            controller.setCommentId(commentId);

            // Create a new scene with the modification page and show it
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCommentsByBlogId(int blogId) {
        List<Comment> comments = commentService.getCommentsByBlogId(blogId);

        ObservableList<Comment> observableList = FXCollections.observableArrayList(comments);
        commentListView.setItems(observableList);
    }


    public class CommentListCell extends ListCell<Comment> {
        @Override
        protected void updateItem(Comment item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) {
                HBox hbox = new HBox();
                Label contentLabel = new Label(item.getContent());
                hbox.getChildren().add(contentLabel);
                for (int i = 0; i < item.getLikes(); i++) {
                    Label starLabel = new javafx.scene.control.Label("★");
                    starLabel.setStyle("-fx-text-fill: yellow;");
                    hbox.getChildren().add(starLabel);
                }
                setText(null);
                setGraphic(hbox);
            } else {
                setText(null);  
                setGraphic(null);
            }
        }
    }

    @FXML
    public void deleteComment(ActionEvent actionEvent) {
        // Get the selected comment
        Comment selectedComment = commentListView.getSelectionModel().getSelectedItem();
        if (selectedComment != null) {
            // Delete the selected comment from the database
            commentService.delete(selectedComment.getId());
            // Remove the selected comment from the ListView
            commentListView.getItems().remove(selectedComment);
        }
    }


    @FXML
    public void searchAction(ActionEvent actionEvent) {
        String searchQuery = searchField.getText();
        // Implement search functionality here if needed
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


}
