package controllers;

import entities.Blog;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import services.BlogService;

import java.util.List;

public class TopActiveBlogs {

    @FXML
    private BarChart<String, Number> barChart;

    public void initialize() {
        // Retrieve top commented blogs
        BlogService blogService = new BlogService();
        List<Blog> topCommentedBlogs = blogService.getTopCommentedBlogs();

        // Populate the BarChart with blog titles and comment counts
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Blog blog : topCommentedBlogs) {
            series.getData().add(new XYChart.Data<>(blog.getTitre(), blog.getComments().size()));
        }

        // Add the series to the BarChart
        barChart.getData().add(series);

        // Set labels
        barChart.getXAxis().setLabel("Blog Titles");
        barChart.getYAxis().setLabel("Number of Comments");
    }
}
