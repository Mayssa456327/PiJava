package controllers;

import entities.Category;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServiceCategory;

import java.io.IOException;

public class GestionCategoryAdmin {

    @FXML
    private TextField tfnom;

    @FXML
    private ListView<Category> lvcategorie;

    ServiceCategory serviceCategory=new ServiceCategory();
    @FXML
    void initialize(){
        refresh();
    }
    void refresh(){
        lvcategorie.getItems().setAll(serviceCategory.afficher());

    }

    @FXML
    void ajouterCategory(ActionEvent event) {
        Category c=new Category(tfnom.getText());
        serviceCategory.ajouter(c);
        refresh();
    }

    @FXML
    void updateCategory(ActionEvent event) {
        Category c=lvcategorie.getSelectionModel().getSelectedItem();
        if(c!=null){
            c.setName(tfnom.getText());
            serviceCategory.modifier(c);
            refresh();
        }
    }
    @FXML
    void fillForum(MouseEvent event) {
        Category c=lvcategorie.getSelectionModel().getSelectedItem();
        if(c!=null){
            tfnom.setText(c.getName());
        }
    }
    @FXML
    void supprimer(ActionEvent event) {
        Category c=lvcategorie.getSelectionModel().getSelectedItem();
        if(c!=null){

            serviceCategory.supprimer(c.getId());
            refresh();
        }
    }
    @FXML
    void gotoproduct(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gestion-produits-admin.fxml"));

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ((Stage)tfnom.getScene().getWindow()).close();
        Stage stage=new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void logout(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Sign_in.fxml"));

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ((Stage)tfnom.getScene().getWindow()).close();
        Stage stage=new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

}
