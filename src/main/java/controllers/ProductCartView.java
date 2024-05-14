package controllers;

import entities.Order;
import entities.OrderDetails;
import entities.Product;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import services.Listener;
import services.ServiceOrder;
import services.ServiceOrderDetails;
import services.ServiceProduct;
import utils.EmailSender;
import utils.SessionManager;

import java.io.IOException;
import java.util.List;

public class ProductCartView implements Listener {

    @FXML
    private GridPane grid;
    @FXML
    private TextArea tfadresse;

    @FXML
    void gotoproduct(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view-product-user.fxml"));

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
    ServiceOrderDetails serviceOrderDetails=new ServiceOrderDetails();
    ServiceOrder serviceOrder=new ServiceOrder();
    ServiceProduct serviceProduct=new ServiceProduct();
    public void refresh(){
        grid.getChildren().clear();
        int column=0;
        int row=1;
        List<OrderDetails> list=serviceOrderDetails.getByOderId(serviceOrder.getOrderByUser(User.Current_User.getId()).getId());
        System.out.println(list);
        for(OrderDetails orderDetails:list){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/product-cart-card.fxml"));
            AnchorPane anchorPane=null;
            try {
                anchorPane=fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ProductCartCard controller=fxmlLoader.getController();
            controller.remplireData(orderDetails);
            controller.setListener(this);
            if(column==2){
                column=0;
                row++;
            }
            grid.add(anchorPane,column++,row);
        }
    }
    @FXML
    public void initialize() {
        refresh();
    }
    @FXML
    void buy(ActionEvent event) {
        double total=0;
        List<OrderDetails> list=serviceOrderDetails.getByOderId(serviceOrder.getOrderByUser(User.Current_User.getId()).getId());
        for(OrderDetails orderDetails:list){
            Product product=serviceProduct.getProductByName(orderDetails.getProduct());
            product.setQuantity(product.getQuantity()-orderDetails.getQuantity());
            serviceProduct.modifier(product);
            total+=orderDetails.getTotal();
            if(product.getQuantity()<5){
                EmailSender.sendMail("Tasnimnaili2012@gmail.com","Product: "+product.getId()+" name="+product.getName()+" quantity="+product.getQuantity(),"Product under 5");
            }
            serviceOrderDetails.supprimer(orderDetails.getId());
        }
        Order order=serviceOrder.getOrderByUser(User.Current_User.getId());

        order.setAdresse(tfadresse.getText());
        order.setIspayed(true);
        serviceOrder.modifier(order);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view-product-user.fxml"));
        EmailSender.sendMail(User.Current_User.getMail(),"Order: "+order.getId()+" total="+total,"Order detail");

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

    @Override
    public void ondeleteClicked() {
        refresh();
    }
}
