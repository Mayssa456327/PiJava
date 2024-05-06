package com.example.pidevjava.controllers;

import com.example.pidevjava.models.Evenement;
import com.example.pidevjava.services.IService;
import com.example.pidevjava.services.ServiceEvenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
    private Button backBtn;
    @FXML
    private ListView<Evenement>listEvents;

    @FXML
    private Button sponsor;
    @FXML
    private ScrollPane Scrollpane;

    @FXML
    private Label nom;
    private ServiceEvenement SE = new ServiceEvenement();

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
        listEvents.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Evenement e, boolean empty) {
                super.updateItem(e, empty);

                if (empty || e == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    GridPane container = new GridPane();
                    TextFlow textFlow = new TextFlow();

                    String nameStyle = "-fx-fill: #18593b;  -fx-font-size: 20;";
                    String labelStyle = "-fx-fill: #69bfa7; -fx-font-size: 14; -fx-font-weight: bold;";
                    String dataStyle = "-fx-fill: black; -fx-font-size: 14;";

                    Text nomText = new Text(e.getNom_evenement() + "\n");
                    nomText.setStyle(nameStyle);

                    Text typeText = new Text("Type: ");
                    typeText.setStyle(labelStyle);
                    Text typeData = new Text(e.getType_evenement() + "\n");
                    typeData.setStyle(dataStyle);

                    Text dateTextD = new Text("Date Debut: ");
                    dateTextD.setStyle(labelStyle);
                    Text dateDataD = new Text(e.getDate_debut().toString() + "\n");
                    dateDataD.setStyle(dataStyle);

                    Text dateTextF = new Text("Date FIN: ");
                    dateTextF.setStyle(labelStyle);
                    Text dateDataF = new Text(e.getDate_fin().toString() + "\n");
                    dateDataF.setStyle(dataStyle);

                    Text lieuText = new Text("Lieu: ");
                    lieuText.setStyle(labelStyle);
                    Text lieuData = new Text(e.getLieu_evenement() + "\n");
                    lieuData.setStyle(dataStyle);

                    Text budgeText = new Text("Budget: ");
                    budgeText.setStyle(labelStyle);
                    Text budgeData = new Text(String.valueOf(e.getBudget()) + "\n");
                    budgeData.setStyle(dataStyle);

                    ImageView imageView = new ImageView();
                    File imageFile = new File("C:/Users/mayss/Desktop/web/PIDEV/public/uploads/" + e.getImage_evenement());
                    Image image = new Image(imageFile.toURI().toString());
                    imageView.setImage(image);
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(100);

                    container.setHgap(1);
                    container.setVgap(1);

                    container.add(imageView, 0, 0);
                    container.add(nomText, 1, 0);
                    container.add(typeText, 0, 1);
                    container.add(typeData, 1, 1);
                    container.add(dateTextD, 0, 2);
                    container.add(dateDataD, 1, 2);
                    container.add(dateTextF, 0, 3);
                    container.add(dateDataF, 1, 3);
                    container.add(lieuText, 0, 4);
                    container.add(lieuData, 1, 4);
                    container.add(budgeText, 0, 5);
                    container.add(budgeData, 1, 5);

                    setGraphic(container);
                }
            }
        });

        try {
            listEvents.getItems().addAll(SE.getAll());
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