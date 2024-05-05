package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;
import services.CommentService;
import entities.Comment;
import java.net.URL;
import java.util.*;

public class STAT implements Initializable {

    @FXML
    private PieChart likePieChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CommentService commentService = new CommentService();
        List<Comment> comments = commentService.getAllCommentsOrderedByLikes();

        // Map to store like counts and their corresponding list of comments
        Map<Integer, List<Comment>> likeCountMap = new HashMap<>();

        // Group comments by like count
        for (Comment comment : comments) {
            int likes = comment.getLikes();
            if (!likeCountMap.containsKey(likes)) {
                likeCountMap.put(likes, new ArrayList<>());
            }
            likeCountMap.get(likes).add(comment);
        }

        // Prepare data for PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Add data to PieChart based on grouped like counts
        for (Map.Entry<Integer, List<Comment>> entry : likeCountMap.entrySet()) {
            int likes = entry.getKey();
            List<Comment> commentsWithLikes = entry.getValue();
            int totalCount = commentsWithLikes.size();

            // Create a PieChart.Data object for this like count group
            PieChart.Data data = new PieChart.Data(likes + " Likes (" + totalCount + " comments)", totalCount);
            pieChartData.add(data);
        }

        // Set the data to the PieChart
        likePieChart.setData(pieChartData);

        // Attach event handler to show details when a slice is clicked
        for (PieChart.Data data : likePieChart.getData()) {
            data.getNode().addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> {
                System.out.println(data.getName()); // Print the group details when clicked
            });
        }
    }
}
