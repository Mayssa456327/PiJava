package controllers;

import entities.Blog;
import entities.Comment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.controlsfx.control.Rating;
import services.CommentService;

import java.time.LocalDateTime;

public class AjouterCommentaire {

    @FXML
    private TextField commentContentField;
    @FXML
    private Rating commentLikesRating;
    @FXML
    private Label commentContentErrorLabel;
    @FXML
    private Label commentLikesErrorLabel;

    private Integer blogId;

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    @FXML
    void submitComment(ActionEvent event) {
        String content = commentContentField.getText();
        int likes = (int) commentLikesRating.getRating();

        // Reset error labels
        commentContentErrorLabel.setVisible(false);
        commentLikesErrorLabel.setVisible(false);

        // Check for empty content field
        if (content.isEmpty()) {
            commentContentErrorLabel.setVisible(true);
            return;
        }

        // Check for missing blog ID
        if (blogId == null) {
            System.err.println("Error: Missing blog ID.");
            return;
        }

        // Check for bad words in the content
        if (containsBadWords(content)) {
            // Display a notification about the bad word
            showBadWordNotification();
            return;
        }

        // Create a new comment object
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setLikes(likes);
        comment.setDate(LocalDateTime.now());
        comment.setBlog(new Blog(blogId));

        // Save the comment to the database
        CommentService commentService = new CommentService();
        commentService.create(comment);

        // Optionally, you can close the window after adding the comment
        commentContentField.getScene().getWindow().hide();
    }

    private void showBadWordNotification() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Bad Word Detected");
        alert.setHeaderText("Inappropriate Content");
        alert.setContentText("Please avoid using inappropriate language in your comment.");

        alert.showAndWait();
    }

    private boolean containsBadWords(String content) {
        // Define your list of bad words here
        String[] badWords = {"fuck", "shit", "cunt","stupid","israel","Terrorist","i hope you die"};

        // Check if the content contains any of the bad words
        for (String word : badWords) {
            if (content.toLowerCase().contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
