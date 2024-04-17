package com.example.pidevjava.controllers;

        import com.example.pidevjava.models.Evenement;
        import com.example.pidevjava.services.ServiceEvenement;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.*;
        import javafx.scene.image.ImageView;
        import javafx.scene.layout.HBox;
        import javafx.scene.layout.VBox;
        import javafx.stage.FileChooser;
        import javafx.scene.image.Image;


        import java.io.File;
        import java.net.URL;
        import java.sql.SQLException;
        import java.util.List;
        import java.util.ResourceBundle;

public class FrontEvenementController implements Initializable {


    @FXML
    private VBox EventVBox;


    @FXML
    private Button sponsor;
    @FXML
    private ScrollPane Scrollpane;


    private final ServiceEvenement SE = new ServiceEvenement();
    private String imagePath;

    private Evenement selectedEvenement;

    private final String imageDirectory = "C:/Users/mayss/Desktop/web/PIDEV/public/uploads/";






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Evenement> events = SE.getAll();
            for (Evenement evenement : events) {
                HBox eventBox = new HBox(); // Create an HBox for each event
                Label eventLabel = new Label(
                        "Nom: " + evenement.getNom_evenement() +
                                ", Type: " + evenement.getType_evenement() +
                                ", Date Debut: " + evenement.getDate_debut().toString() +
                                ", Date fin: " + evenement.getDate_fin().toString() +
                                ", Lieu: " + evenement.getLieu_evenement() +
                                ", budget: " + evenement.getBudget()
                );
                ImageView imageView = new ImageView();
                try {
                    File imageFile = new File("C:/Users/mayss/Desktop/web/PIDEV/public/uploads/" + evenement.getImage_evenement());
                    javafx.scene.image.Image image = new Image(imageFile.toURI().toString());
                    imageView.setImage(image);
                    imageView.setFitWidth(100); // Set the desired width of the image
                    imageView.setPreserveRatio(true); // Preserve the aspect ratio of the image
                } catch (Exception e) {
                    // Handle image loading errors
                    System.err.println("Error loading image: " + e.getMessage());
                    // Show error message in alert dialog
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Error loading image: " + e.getMessage());
                    alert.showAndWait();
                }


                eventBox.getChildren().addAll(imageView, eventLabel); // Add ImageView and Label to the HBox
                EventVBox.getChildren().add(eventBox); // Add the HBox to the VBox
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }







    private void refreshEvents() throws SQLException {
        List<Evenement> events = SE.getAll();
        EventVBox.getChildren().clear();
        for (Evenement evenement : events) {

            Label eventLabel = new Label(
                    "Nom: " + evenement.getNom_evenement() +
                            ", Type: " + evenement.getType_evenement() +
                            ", Date Debut: "  + evenement.getDate_debut().toString() +
                            ", Date fin: "  + evenement.getDate_fin().toString() +
                            ", Lieu: " + evenement.getLieu_evenement() +
                            ", budget: " + evenement.getBudget()
                    // ",Image: " + evenement.getImage()
            );
            ImageView imageView = new ImageView();
            try {
                File imageFile = new File(imageDirectory + evenement.getImage_evenement());
                Image image = new Image(imageFile.toURI().toString());
                // Inside the try block where you load the image
                imageView.setImage(image);
                imageView.setFitWidth(100); // Set the desired width of the image
                imageView.setPreserveRatio(true); // Preserve the aspect ratio of the image


            } catch (Exception e) {
                // Handle image loading errors
                System.err.println("Error loading image: " + e.getMessage());
                // Show error message in alert dialog
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error loading image: " + e.getMessage());
                alert.showAndWait();
            }


            EventVBox.getChildren().addAll(imageView, eventLabel);

        }
    }


    @FXML
    void OnClickedSponsor(ActionEvent event) {
        SE.changeScreen(event, "/com/example/pidevjava/FrontSponsor.fxml", "Sponsor");

    }

}