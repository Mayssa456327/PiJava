package services;


import entities.OrderDetails;
import utils.Myconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceOrderDetails implements IService2<OrderDetails> {
    private Connection cnx;

    public ServiceOrderDetails() {
        cnx = Myconnection.getInstance().getCnx();
    }

    @Override
    public void ajouter(OrderDetails orderDetails) {
        String query = "INSERT INTO `orderdetails` (`myorder_id`, `product`, `quantity`, `price`, `total`) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, orderDetails.getMyorder_id());
            pst.setString(2, orderDetails.getProduct());
            pst.setInt(3, orderDetails.getQuantity());
            pst.setDouble(4, orderDetails.getPrice());
            pst.setDouble(5, orderDetails.getTotal());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
    }

    @Override
    public void modifier(OrderDetails orderDetails) {
        String query = "UPDATE `orderdetails` SET `myorder_id` = ?, `product` = ?, `quantity` = ?, `price` = ?, `total` = ? WHERE `id` = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, orderDetails.getMyorder_id());
            pst.setString(2, orderDetails.getProduct());
            pst.setInt(3, orderDetails.getQuantity());
            pst.setDouble(4, orderDetails.getPrice());
            pst.setDouble(5, orderDetails.getTotal());
            pst.setInt(6, orderDetails.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String query = "DELETE FROM `orderdetails` WHERE `id` = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
    }

    @Override
    public List<OrderDetails> afficher() {
        List<OrderDetails> list = new ArrayList<>();
        String query = "SELECT * FROM `orderdetails`";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                OrderDetails orderDetails = new OrderDetails(
                        rs.getInt("id"),
                        rs.getInt("myorder_id"),
                        rs.getString("product"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getDouble("total")
                );
                list.add(orderDetails);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
        return list;
    }
    public List<OrderDetails> getByOderId(int id){
        return afficher().stream().filter(o->o.getMyorder_id()==id).collect(Collectors.toList());
    }
}
