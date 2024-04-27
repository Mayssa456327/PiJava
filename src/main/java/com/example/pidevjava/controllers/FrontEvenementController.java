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


                        String nameStyle = "-fx-fill: #18593b;  -fx-font-size: 30;";
                        String labelStyle = "-fx-fill: #69bfa7; -fx-font-size: 14; -fx-font-weight: bold;";
                        String dataStyle = "-fx-fill: black; -fx-font-size: 14;";

                        Text nomText = new Text(e.getNom_evenement() + "\n");
                        nomText.setStyle(nameStyle);

                        Text typeText = new Text("Type : ");
                        typeText.setStyle(labelStyle);
                        Text typeData = new Text(e.getType_evenement() + "\n");
                        typeData.setStyle(labelStyle);

                        Text dateTextD = new Text("Date Debut: ");
                        dateTextD.setStyle(labelStyle);
                        Text dateDataD = new Text(e.getDate_debut().toString() + "\n");
                        dateDataD.setStyle(dataStyle);


                        Text dateTextF = new Text("Date FIN: ");
                        dateTextF.setStyle(labelStyle);
                        Text dateDataF = new Text(e.getDate_fin().toString() + "\n");
                        dateDataF.setStyle(dataStyle);

                        Text lieuText = new Text("Lieu_evenement: ");
                        lieuText.setStyle(labelStyle);
                        Text lieuData = new Text(e.getLieu_evenement() + "\n");
                        lieuData.setStyle(labelStyle);

                        Text budgeText = new Text("Budget: ");
                        budgeText.setStyle(labelStyle);
                        Text budgeData = new Text(String.valueOf(e.getBudget())+ "\n");
                        budgeData.setStyle(labelStyle);

                        ImageView imageView = new ImageView();
                        File imageFile = new File("C:/Users/mayss/Desktop/web/PIDEV/public/uploads/" + e.getImage_evenement());
                        Image image = new Image(imageFile.toURI().toString());
                        imageView.setImage(image);
                       // String imagePath = "uploads/" +e.getImage_evenement();
                        //Image productImage = new Image(new File(imagePath).toURI().toString());
                        //ImageView imageView = new ImageView(productImage);

                        imageView.setFitHeight(200);
                        imageView.setFitWidth(200);
                        nomText.setWrappingWidth(200);
                        dateDataF.setWrappingWidth(200);
                        dateDataD.setWrappingWidth(200);
                        dateTextF.setWrappingWidth(200);
                        dateTextD.setWrappingWidth(200);
                        typeText.setWrappingWidth(200);
                        typeData.setWrappingWidth(200);
                        lieuText.setWrappingWidth(200);
                        lieuData.setWrappingWidth(200);
                        budgeText.setWrappingWidth(200);
                        budgeData.setWrappingWidth(200);
                        ColumnConstraints col1 = new ColumnConstraints(200);
                        ColumnConstraints col2 = new ColumnConstraints(450);
                        ColumnConstraints col3 = new ColumnConstraints(200);
                        container.getColumnConstraints().addAll(col1, col2, col3);

                        textFlow.getChildren().addAll(nomText, budgeData, budgeText, lieuData,lieuText,typeData, typeText, dateTextD, dateTextF,dateDataD,dateDataF);
                        container.add(textFlow, 1, 0);
                        container.add(imageView, 0, 0);

                        ColumnConstraints columnConstraints = new ColumnConstraints();
                        columnConstraints.setHgrow(Priority.ALWAYS);
                        container.getColumnConstraints().addAll(columnConstraints, columnConstraints, columnConstraints);

                        container.setHgap(30);

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