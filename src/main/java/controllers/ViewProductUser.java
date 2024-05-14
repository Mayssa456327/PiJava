package controllers;

import entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import services.ServiceProduct;
import test.MainGUI;

import java.io.IOException;
import java.util.List;

public class ViewProductUser
{
    @FXML
    private GridPane grid;
    @FXML
    private TextField tfrecherche;

    @FXML
    public void initialize() {
        refresh(serviceProduct.afficher());
        recherche_avance();
    }
    ServiceProduct serviceProduct=new ServiceProduct();

    public void refresh(List<Product> list){
        grid.getChildren().clear();
        int column=0;
        int row=1;
        for(Product p:list){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/product-card.fxml"));
            AnchorPane anchorPane=null;
            try {
                anchorPane=fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ProductCard controller=fxmlLoader.getController();
            controller.remplireData(p);
            if(column==2){
                column=0;
                row++;
            }
            grid.add(anchorPane,column++,row);
        }
    }
    @FXML
    void gotocart(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/product-cart-view.fxml"));

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ((Stage)grid.getScene().getWindow()).close();
        Stage stage=new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void gofront(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AffichageUser.fxml"));

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ((Stage)grid.getScene().getWindow()).close();
        Stage stage=new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    void recherche_avance(){
        ObservableList<Product> data= FXCollections.observableArrayList(serviceProduct.afficher());
        FilteredList<Product> filteredList=new FilteredList<>(data,p->true);
        tfrecherche.textProperty().addListener((observable,oldValue,newValue)->{
            filteredList.setPredicate(p->{
                if(newValue.isEmpty() || newValue==null){
                    return true;
                }
                if(String.valueOf(p.getQuantity()).contains(newValue)){
                    return true;
                } else if (String.valueOf(p.getPrice()).contains(newValue)) {
                    return true;
                } else if (p.getName().contains(newValue)) {
                    return true;
                } else if (p.getDescription().contains(newValue)) {
                    return true;
                } else if (p.getSubtitle().contains(newValue)) {
                    return true;
                } else{
                    return false;
                }
            });
            refresh(filteredList);
        });
    }
}