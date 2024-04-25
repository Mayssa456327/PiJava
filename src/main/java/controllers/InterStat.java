package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class InterStat {

    public void goToStatistics(ActionEvent actionEvent) {
        try {
            // Load the Statistics view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/STAT.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Statistics");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToTopBlogs(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TopActiveBlogs.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Top Blogs");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
