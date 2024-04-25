package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utils.MyDatabase;

import java.io.InputStream;
import java.util.Objects;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent certsParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/Certs.fxml")));
        Parent ordsParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/Ords.fxml")));
       Parent demsParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/DemAn.fxml")));

        Scene certsScene = new Scene(certsParent);
        Scene ordsScene = new Scene(ordsParent);
       Scene demsScene = new Scene(demsParent);

        // Charger l'icône
        InputStream iconStream = getClass().getResourceAsStream("/image/doc.png");
        if (iconStream == null) {
            System.out.println("Icon not found!");
        } else {
            Image icon = new Image(iconStream);
            stage.getIcons().add(icon);
        }

        stage.setTitle("DocDirect");
        stage.setScene(certsScene); // Définir la scène par défaut

        // Afficher la scène des certificats
        stage.setScene(certsScene);


        stage.show();

        // Vous pouvez également changer la scène plus tard en fonction des actions de l'utilisateur, par exemple :
        // stage.setScene(ordsScene);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
