package com.example.pidevjava.test;

import com.example.pidevjava.models.Evenement;
import com.example.pidevjava.services.ServiceEvenement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/*public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}*/

public class Main {
    public static void main(String[] args) {
        // Creating instances of service classes
        ServiceEvenement evenementService = new ServiceEvenement();
        // ServiceReservation reservationService = new ServiceReservation();

        try {
            // Adding an event
            Evenement newEvent = new Evenement();
            newEvent.setId(1);
            newEvent.setNom_evenement("New ");
            newEvent.setType_evenement("Type");
            newEvent.setLieu_evenement("Lieu ");
            newEvent.setImage_evenement("image");
            LocalDateTime eventDateDebut = LocalDateTime.of(2023, 2, 12, 20, 30);
            newEvent.setDate_debut(eventDateDebut);
            LocalDateTime eventDateFin = LocalDateTime.of(2023, 2, 12, 20, 30);
            newEvent.setDate_fin(eventDateFin);
            newEvent.setBudget(4.7);

            // Set the date and time of the event
            // Set the date and time of the event


            // Display the date and time of the event
            System.out.println("Event Date and Time: " + newEvent.getDate_debut());
            System.out.println("Event Date and Time: " + newEvent.getDate_fin());

            /*evenementService.add(newEvent);
            System.out.println("Event added successfully.");*/


           /* newEvent.setId(1);
            evenementService.update(newEvent);
            System.out.println("Event update successfully.");*/

            /*newEvent.setId(1);
            evenementService.delete(newEvent);*/




            // Displaying all events
            System.out.println("Events:");
            List<Evenement> events = evenementService.getAll();
            for (Evenement event : events) {
                System.out.println("ID: " + event.getId());
                System.out.println("Nom: " + event.getNom_evenement());
                System.out.println("Image: " + event.getImage_evenement());
                System.out.println("Location: " + event.getLieu_evenement());
                System.out.println("Type: " + event.getType_evenement());
                System.out.println("Date debut: " + event.getDate_debut());
                System.out.println("Date fin: " + event.getDate_fin());
                System.out.println("budget: " + event.getBudget());
                System.out.println();
            }

           
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}