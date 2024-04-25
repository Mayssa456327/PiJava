package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Rating;
import services.CommentService;
import entities.Comment;

import java.time.LocalDateTime;

public class Modifcomnt {

    @FXML
    private TextField commentContentField;
    @FXML
    private Rating commentLikesRating;
    @FXML
    private Label commentContentErrorLabel;
    @FXML
    private Label commentLikesErrorLabel;
    @FXML
    private Button updateButton;

    private int commentId;

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public void updateComment(ActionEvent actionEvent) {
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

        // Create a new comment object
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setContent(content);
        comment.setLikes(likes);
        comment.setDate(LocalDateTime.now()); // Set the current date and time

        // Save the updated comment to the database
        CommentService commentService = new CommentService();
        commentService.update(comment);

        // Optionally, you can close the window after updating the comment
        AnchorPane parentAnchorPane = (AnchorPane) updateButton.getParent().getParent();
        if (parentAnchorPane != null && parentAnchorPane.getScene() != null) {
            parentAnchorPane.getScene().getWindow().hide();
        }
    }

}
