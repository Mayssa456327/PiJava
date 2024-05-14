package controllers;

import entities.OrderDetails;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import services.Listener;
import services.ServiceOrderDetails;

public class ProductCartCard {

    @FXML
    private Button btnaddcart;

    @FXML
    private Label lprice;

    @FXML
    private Label lname;

    @FXML
    private Label lqte;
    @FXML
    private Label ltotal;
    OrderDetails orderDetails;
    ServiceOrderDetails serviceOrderDetails=new ServiceOrderDetails();
    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @FXML
    void removeFromCart(ActionEvent event) {
        if(listener!=null){
            serviceOrderDetails.supprimer(orderDetails.getId());
            listener.ondeleteClicked();
        }

    }
    void remplireData(OrderDetails orderDetails){
        this.orderDetails=orderDetails;
        lprice.setText(String.valueOf(orderDetails.getPrice()));
        lname.setText(orderDetails.getProduct());
        lqte.setText(String.valueOf(orderDetails.getQuantity()));
        ltotal.setText(String.valueOf(orderDetails.getTotal()));


    }

}
