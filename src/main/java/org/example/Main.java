package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Chambre/addChambre.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("3A44");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
       // HopitalService hopitalService = new HopitalService();


       // Hopital p1 = new Hopital("flen","felten","75120123","vevrver@evrv.com",40);



        //try {
        //    hopitalService.add(p1);
        //} catch (SQLException e) {
       //     System.err.println(e.getMessage());//}
    }
}