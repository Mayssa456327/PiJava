package controllers;


import entities.Evenement;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.json.JSONArray;
import org.json.JSONObject;
import services.ServiceEvenement3;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FrontEvenementController implements Initializable {

    @FXML
    private ListView<Evenement> listEvents;

    private ServiceEvenement3 SE = new ServiceEvenement3();
    private final String imageDirectory = "C:/Users/mayss/Desktop/web/PIDEV/public/uploads/";

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
                    createEventCard(e);
                }
            }

            private void createEventCard(Evenement e) {
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);

                ImageView imageView = new ImageView();
                File imageFile = new File(imageDirectory + e.getImage_evenement());
                Image image = new Image(imageFile.toURI().toString(), 100, 100, true, true);
                imageView.setImage(image);

                Label nameLabel = new Label("Name: " + e.getNom_evenement());
                Label typeLabel = new Label("Type: " + e.getType_evenement());
                Label dateLabel = new Label("Dates: " + e.getDate_debut() + " to " + e.getDate_fin());
                Label locationLabel = new Label("Location: " + e.getLieu_evenement());
                Label budgetLabel = new Label("Budget: $" + e.getBudget());

                Button mapButton = new Button("Show on Map");
                mapButton.setOnAction(event -> {
                    System.out.println("Geocode button clicked for address: " + e.getLieu_evenement());
                    geocodeAddress(e.getLieu_evenement(), e.getNom_evenement());
                });

                grid.add(imageView, 0, 0, 1, 5);
                grid.add(nameLabel, 1, 0);
                grid.add(typeLabel, 1, 1);
                grid.add(dateLabel, 1, 2);
                grid.add(locationLabel, 1, 3);
                grid.add(budgetLabel, 1, 4);
                grid.add(mapButton, 1, 5);

                setGraphic(grid);
            }
        });

        try {
            listEvents.getItems().addAll(SE.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void geocodeAddress(String address, String eventName) {
        new Thread(() -> {
            try {
                String apiUrl = "https://nominatim.openstreetmap.org/search?format=json&q=" + address.replaceAll(" ", "+");
                URL url = new URL(apiUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", "Mozilla/5.0");

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                con.disconnect();

                JSONArray jsonArray = new JSONArray(content.toString());
                if (jsonArray.length() > 0) {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    double lat = jsonObject.getDouble("lat");
                    double lon = jsonObject.getDouble("lon");

                    // Open the dialog with the map on the JavaFX Application Thread
                    Platform.runLater(() -> openMapDialog(lat, lon, eventName));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    private void openMapDialog(double lat, double lon, String eventName) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Map");

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(getClass().getResource("/map.html").toExternalForm());
        webEngine.setJavaScriptEnabled(true);

        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                // Call JavaScript function to update the marker with the event name
                webEngine.executeScript("updateMarker(" + lat + ", " + lon + ", '" + escapeJavaScriptString(eventName) + "');");
            }
        });

        dialog.getDialogPane().setContent(webView);
        dialog.getDialogPane().setPrefSize(600, 400);
        ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(closeButton);
        dialog.showAndWait();
    }
    private String escapeJavaScriptString(String string) {
        if (string == null) return null;
        return string.replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
    }



    @FXML
    void OnClickedSponsor(ActionEvent event) {
        SE.changeScreen(event, "/FrontSponsor.fxml", "Sponsor");

    }


    @FXML
    void OnClickedBack(ActionEvent event) {
        SE.changeScreen(event,"/AffichageUser.fxml", " Accueille ");

    }
}
