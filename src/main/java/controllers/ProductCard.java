package controllers;

import entities.Order;
import entities.OrderDetails;
import entities.Product;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import services.ServiceOrder;
import services.ServiceOrderDetails;
import utils.QRCodeGenerator;
import utils.SessionManager;

import java.io.File;

public class ProductCard {

    @FXML
    private ImageView img;
    @FXML
    private Button btnaddcart;
    @FXML
    private Label lprice;

    @FXML
    private Label lname;

    @FXML
    private Label lsub;

    @FXML
    private Label ldesc;

    @FXML
    private Label lqte;

    @FXML
    private ImageView oosimg;
    Product product;
    @FXML
    private Label lquantitytoadd;
    @FXML
    private ImageView qrimg;
    int nbQuantity=1;
    public void remplireData(Product product){
        this.product=product;
        lname.setText(product.getName());
        if(product.getDescription().length()>20){
            ldesc.setText(product.getDescription().substring(0,20));
        }
        else{
            ldesc.setText(product.getDescription());
        }

        lqte.setText(String.valueOf(product.getQuantity()));

        lprice.setText(String.valueOf(product.getPrice())+" DT");
        lsub.setText(product.getSubtitle());
        File file=new File("C:\\PiJava-gestion_users\\src\\main\\resources\\tn\\esprit\\docdirect\\img\\"+product.getIllustration());
        Image image=new Image(file.toURI().toString());
        img.setImage(image);
        if(product.getQuantity()==0){
            oosimg.setVisible(true);
            btnaddcart.setDisable(true);
        }
        QRCodeGenerator.generateQrCode(product.toString(), product.getId());
        File file2=new File("C:\\PiJava-gestion_users\\src\\main\\resources\\qrcode\\Product"+product.getId()+".png");
        Image image2=new Image(file2.toURI().toString());
        qrimg.setImage(image2);
    }
    @FXML
    public void initialize(){
        oosimg.setVisible(false);
    }
    ServiceOrder serviceOrder=new ServiceOrder();
    ServiceOrderDetails serviceOrderDetails=new ServiceOrderDetails();
    @FXML
    void addtocart(ActionEvent event) {
        System.out.println(User.Current_User);
        OrderDetails orderDetails=new OrderDetails();
        Order order=serviceOrder.getOrderByUser(User.Current_User.getId());//idUser
        if(order!=null){
            orderDetails.setMyorder_id(order.getId());

        }
        else{
            Order order1=new Order(User.Current_User.getId(),"",false);
            serviceOrder.ajouter(order1);

            orderDetails.setMyorder_id(serviceOrder.getOrderByUser(2).getId());
        }
        orderDetails.setProduct(product.getName());
        orderDetails.setPrice(product.getPrice());
        orderDetails.setQuantity(nbQuantity);
        orderDetails.setTotal(product.getPrice()*nbQuantity);
        serviceOrderDetails.ajouter(orderDetails);
        //NOTIFICATION
        Notifications.create()
                .title("Product")
                .text("Product added to cart successfully")
                .hideAfter(Duration.seconds(10)).showConfirm();
    }
    @FXML
    void decrement(ActionEvent event) {
        if(nbQuantity>1){
            nbQuantity--;
            lquantitytoadd.setText(String.valueOf(nbQuantity));
        }

    }

    @FXML
    void increment(ActionEvent event) {
        if(nbQuantity<product.getQuantity()){
            nbQuantity++;
            lquantitytoadd.setText(String.valueOf(nbQuantity));
        }

    }


}
