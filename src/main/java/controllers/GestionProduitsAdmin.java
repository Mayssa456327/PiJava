package controllers;

import entities.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import services.ServiceCategory;
import services.ServiceProduct;

import java.io.File;
import java.io.IOException;

public class GestionProduitsAdmin {
    @FXML
    private ComboBox<String> cbsort;
    @FXML
    private TextField tfsearch;

    @FXML
    private ComboBox<String> cbcategory;


    @FXML
    private TextArea tfdescription;

    @FXML
    private TextField tfimage;

    @FXML
    private TextField tfname;

    @FXML
    private TextField tfprice;

    @FXML
    private TextField tfquantity;

    @FXML
    private TextField tfsubtitle;
    @FXML
    private ListView<Product> lvproduit;

    ServiceProduct serviceProduct=new ServiceProduct();
    ServiceCategory serviceCategory=new ServiceCategory();
    @FXML
    void initialize(){
        cbcategory.getItems().setAll(serviceCategory.getCategoriesNames());
        cbsort.getItems().setAll("name","subtitle","price","quantity","category");
        refresh();
        tfimage.setDisable(true);
    }

    void refresh(){

        lvproduit.getItems().setAll(serviceProduct.afficher());

    }
    @FXML
    void sort(ActionEvent event) {
        lvproduit.getItems().setAll(serviceProduct.sortProductByCritere(cbsort.getValue()));
    }


    @FXML
    void add(ActionEvent event) {
        if(controlleDeSaisie().length()>0){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Formulaire invalide");
            alert.setContentText(controlleDeSaisie());
            alert.showAndWait();
        }
        else{
            Product product=new Product();
            product.setDescription(tfdescription.getText());
            product.setName(tfname.getText());
            product.setPrice(Double.valueOf(tfprice.getText()));
            product.setQuantity(Integer.valueOf(tfquantity.getText()));
            product.setSubtitle(tfsubtitle.getText());
            product.setIllustration(tfimage.getText());
            product.setCategory_id(serviceCategory.getIdByName(cbcategory.getValue()));
            product.setSlug(product.getName());
            product.setSlug(product.getSlug().replaceAll(" ","_"));

            serviceProduct.ajouter(product);
            refresh();
            //NOTIFICATION
            Notifications.create()
                    .title("Product")
                    .text("Product added successfully")
                    .hideAfter(Duration.seconds(10)).showConfirm();
        }


    }


    @FXML
    void delete(ActionEvent event) {
        Product product=lvproduit.getSelectionModel().getSelectedItem();
        if(product!=null){
            serviceProduct.supprimer(product.getId());
            refresh();
        }
    }


    @FXML
    void update(ActionEvent event) {
        Product product=lvproduit.getSelectionModel().getSelectedItem();
        if(product!=null){
            if(controlleDeSaisie().length()>0){
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Formulaire invalide");
                alert.setContentText(controlleDeSaisie());
                alert.showAndWait();
            }
            else{
                product.setDescription(tfdescription.getText());
                product.setName(tfname.getText());
                product.setPrice(Double.valueOf(tfprice.getText()));
                product.setQuantity(Integer.valueOf(tfquantity.getText()));
                product.setSubtitle(tfsubtitle.getText());
                product.setIllustration(tfimage.getText());
                product.setCategory_id(serviceCategory.getIdByName(cbcategory.getValue()));
                product.setSlug(product.getName());
                product.setSlug(product.getSlug().replaceAll(" ","_"));

                serviceProduct.modifier(product);
                refresh();
            }

        }

    }

    @FXML
    void upload(ActionEvent event) {
        FileChooser fileChooser=new FileChooser();
        File file=fileChooser.showOpenDialog(tfdescription.getScene().getWindow());
        if(file!=null){
            tfimage.setText(file.getName());
        }
    }


    public void fillforum(javafx.scene.input.MouseEvent mouseEvent) {
        Product product=lvproduit.getSelectionModel().getSelectedItem();
        if(product!=null){
            tfdescription.setText(product.getDescription());
            tfimage.setText(product.getIllustration());
            tfname.setText(product.getName());
            tfprice.setText(String.valueOf(product.getPrice()));
            tfquantity.setText(String.valueOf(product.getQuantity()));
            tfsubtitle.setText(product.getSubtitle());
            cbcategory.setValue(serviceCategory.getById(product.getCategory_id()).getName());
        }
    }
    public String controlleDeSaisie(){
        String erreur="";
        if(tfimage.getText().isEmpty()){
            erreur+="Image vide!\n";
        }
        if(tfsubtitle.getText().isEmpty()){
            erreur+="Subtitle vide!\n";
        }
        if(tfquantity.getText().isEmpty() || !tfquantity.getText().matches("\\d+")){
            erreur+="Quantity vide ou invalide!\n";
        }
        if(tfprice.getText().isEmpty() ||!tfprice.getText().matches("[0-9]{1,13}(\\.[0-9]+)?")){
            erreur+="Price vide ou invalide!\n";
        }
        if(tfname.getText().isEmpty()){
            erreur+="Name vide!\n";
        }
        if(cbcategory.getValue()==null){
            erreur+="Category vide!\n";
        }
        return erreur;
    }
    @FXML
    void gotoCategory(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gestion-category-admin.fxml"));

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ((Stage)tfdescription.getScene().getWindow()).close();
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
        ((Stage)tfdescription.getScene().getWindow()).close();
        Stage stage=new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void gotoadmin(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/admin.fxml"));

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ((Stage)tfdescription.getScene().getWindow()).close();
        Stage stage=new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

}