package com.example.pidevjava.test;

import com.example.pidevjava.models.Evenement;
import com.example.pidevjava.models.Sponsor;
import com.example.pidevjava.services.ServiceEvenement;
import com.example.pidevjava.services.ServiceSponsor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/pidevjava/Sign_in.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Accueil");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

/*public class Main {
    public static void main(String[] args) {
        // Creating instances of service classes
        ServiceEvenement evenementService = new ServiceEvenement();
        ServiceSponsor SponsorService = new ServiceSponsor();

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

            // Adding an Sponsor
            Sponsor newSpons = new Sponsor();
            //newSpons.setId(12);
            newSpons.setBudget(5.6);
            newSpons.setEvenement_id(3);
            newSpons.setNom_sponsor("Nomet");
            newSpons.setEmail_sponsor("emailoui");
            newSpons.setAdresse("adressenon");


            // Display the date and time of the event
            System.out.println("Event Date and Time: " + newEvent.getDate_debut());
            System.out.println("Event Date and Time: " + newEvent.getDate_fin());

            evenementService.add(newEvent);
            System.out.println("Event added successfully.");


           /* newEvent.setId(1);
            evenementService.update(newEvent);
            System.out.println("Event update successfully.");*/

            /*newEvent.setId(1);
            evenementService.delete(newEvent);*/

           /* SponsorService.add(newSpons);
            System.out.println("Sponsor added successfully.");*/


           /* newSpons.setId(12);
            SponsorService.update(newSpons);
            System.out.println("sponsor update successfully.");*/

           /* newSpons.setId(10);
            SponsorService.delete(newSpons);*/


            // Displaying all events
           /* System.out.println("Events:");
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
            System.out.println("Spons:");
            List<Sponsor> Sponsors = SponsorService.getAll();
            for (Sponsor Spons : Sponsors) {
                System.out.println("ID: " + Spons.getId());
                System.out.println("Budget: " + Spons.getBudget());
                System.out.println("Evenement_id: " + Spons.getEvenement_id());
                System.out.println("Nom: " + Spons.getNom_sponsor());
                System.out.println("Email: " + Spons.getEmail_sponsor());
                System.out.println("Adresse: " + Spons.getAdresse());

                System.out.println();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}*/