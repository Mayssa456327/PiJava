package services;


import entities.Order;
import utils.Myconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceOrder implements IService2<Order> {
    private Connection cnx;

    public ServiceOrder() {
        cnx = Myconnection.getInstance().getCnx();
    }

    @Override
    public void ajouter(Order order) {
        String query = "INSERT INTO `order` (`user_id`, `adresse`, `ispayed`) VALUES (?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, order.getUser_id());
            pst.setString(2, order.getAdresse());
            pst.setBoolean(3, order.isIspayed());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
    }

    @Override
    public void modifier(Order order) {
        String query = "UPDATE `order` SET `user_id` = ?, `adresse` = ?, `ispayed` = ? WHERE `id` = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, order.getUser_id());
            pst.setString(2, order.getAdresse());
            pst.setBoolean(3, order.isIspayed());
            pst.setInt(4, order.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String query = "DELETE FROM `order` WHERE `id` = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
    }

    @Override
    public List<Order> afficher() {
        List<Order> list = new ArrayList<>();
        String query = "SELECT * FROM `order`";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("adresse"),
                        rs.getBoolean("ispayed")
                );
                list.add(order);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
        return list;
    }
    public Order getOrderByUser(int id){
        return afficher().stream().filter(o->o.getUser_id()==id).findFirst().orElse(null);

    }
}
