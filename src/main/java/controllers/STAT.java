package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import services.CommentService;
import entities.Comment;
import java.net.URL;
import java.util.ResourceBundle;

public class STAT implements Initializable {

    @FXML
    private PieChart likePieChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CommentService commentService = new CommentService();
        Comment mostLikedComment = commentService.getMostLikedComment();

        if (mostLikedComment != null) {
            likePieChart.getData().add(new PieChart.Data(mostLikedComment.getContent(), mostLikedComment.getLikes()));
        }
    }
}
