package com.example.pidevjava.controllers;

        import com.example.pidevjava.models.Evenement;
        import com.example.pidevjava.services.IService;
        import com.example.pidevjava.services.ServiceEvenement;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.*;
        import javafx.scene.image.ImageView;
        import javafx.scene.layout.GridPane;
        import javafx.scene.layout.HBox;
        import javafx.scene.layout.VBox;
        import javafx.scene.paint.Color;
        import javafx.scene.text.Font;
        import javafx.scene.text.Text;
        import javafx.stage.FileChooser;
        import javafx.scene.image.Image;

        import javafx.scene.layout.StackPane;
        import java.io.File;
        import java.net.URL;
        import java.sql.SQLException;
        import java.util.List;
        import java.util.ResourceBundle;

public class FrontEvenementController implements Initializable {


    @FXML
    private VBox EventVBox;
    @FXML
    private Button backBtn;


    @FXML
    private Button sponsor;
    @FXML
    private ScrollPane Scrollpane;

    @FXML
    private Label nom;
    private final ServiceEvenement SE = new ServiceEvenement();
    private ServiceEvenement SE2 = new ServiceEvenement();
    private String imagePath;

    private Evenement selectedEvenement;

    private final String imageDirectory = "C:/Users/mayss/Desktop/web/PIDEV/public/uploads/";
    private IService<VBox> gridPane;
    private int columnIndex;
    private int rowIndex;


    @FXML
    void OnClickedBack(ActionEvent event) {
        SE.changeScreen(event,"/com/example/pidevjava/Sign_in.fxml", " Accueille ");

    }
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            try {
                String nameStyle = "-fx-fill: #5252a4;  -fx-font-size: 15;";
                String labelStyle = "-fx-fill: #69bfa7; -fx-font-size: 10; -fx-font-weight: bold;";
                String dataStyle = "-fx-fill: black; -fx-font-size: 14;";

                List<Evenement> events = SE.getAll();
                for (Evenement evenement : events) {
                    HBox eventBox = new HBox(); // Create an HBox for each event

                    Label eventLabel = new Label();

                    Text nomText = new Text("Nom: ");
                    nomText.setStyle(nameStyle);
                    Text nomValueText = new Text(evenement.getNom_evenement());

                    Text typeText = new Text("Type: ");
                    typeText.setStyle(nameStyle);
                    Text typeValueText = new Text(evenement.getType_evenement());

                    Text dateDebutText = new Text("Date Debut: ");
                    dateDebutText.setStyle(nameStyle);
                    Text dateDebutValueText = new Text(evenement.getDate_debut().toString());

                    Text dateFinText = new Text("Date fin: ");
                    dateFinText.setStyle(nameStyle);
                    Text dateFinValueText = new Text(evenement.getDate_fin().toString());

                    Text lieuText = new Text("Lieu: ");
                    lieuText.setStyle(nameStyle);
                    Text lieuValueText = new Text(evenement.getLieu_evenement());

                    Text budgetText = new Text("Budget: ");
                    budgetText.setStyle(nameStyle);
                    Text budgetValueText = new Text(String.valueOf(evenement.getBudget()));


                    VBox vbox = new VBox(); // Pour contenir les Text
                    vbox.getChildren().addAll(
                            nomText, nomValueText,
                            typeText, typeValueText,
                            dateDebutText, dateDebutValueText,
                            dateFinText, dateFinValueText,
                            lieuText, lieuValueText,
                            budgetText, budgetValueText
                    );
                    eventLabel.setMinWidth(200);
                    eventLabel.setGraphic(vbox);
                    eventLabel.setStyle("-fx-font-family: Arial; -fx-font-size: 14;");
                    eventLabel.setWrapText(true);
                    eventLabel.setStyle("-fx-padding: 10;");
                    ImageView imageView = new ImageView();
                    try {
                        File imageFile = new File("C:/Users/mayss/Desktop/web/PIDEV/public/uploads/" + evenement.getImage_evenement());
                        Image image = new Image(imageFile.toURI().toString());
                        imageView.setImage(image);
                        //imageView.setFitHeight(200);
                        imageView.setFitWidth(200); // Set the desired width of the image
                        imageView.setPreserveRatio(true); // Preserve the aspect ratio of the image

                    } catch (Exception e) {
                        // Handle image loading errors
                        System.err.println("Error loading image: " + e.getMessage());
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Error loading image: " + e.getMessage());
                        alert.showAndWait();
                    }

                    eventBox.getChildren().addAll(imageView, eventLabel); // Add ImageView and Label to the HBox
                    //eventBox.setStyle(""); // Style for the event box
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
                System.err.println("Erreur de chargement de l’image :" + e.getMessage());
                // Show error message in alert dialog
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Erreur de chargement de l’image : " + e.getMessage());
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