package services;


import entities.Product;
import utils.Myconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceProduct implements IService2<Product> {
    private Connection cnx;

    public ServiceProduct() {
        cnx = Myconnection.getInstance().getCnx();
    }

    @Override
    public void ajouter(Product product) {
        try {
            String query = "INSERT INTO `product`" +
                    "(`category_id`, `name`," +
                    " `slug`, `illustration`," +
                    " `subtitle`, `description`," +
                    " `price`, `quantity`)" +
                    " VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, product.getCategory_id());
            pst.setString(2, product.getName());
            pst.setString(3, product.getSlug());
            pst.setString(4, product.getIllustration());
            pst.setString(5, product.getSubtitle());
            pst.setString(6, product.getDescription());
            pst.setDouble(7, product.getPrice());
            pst.setInt(8, product.getQuantity());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erreur:" + ex.getMessage());
        }


    }

    @Override
    public void modifier(Product product) {
        try {
            String query = "UPDATE `product` SET " +
                    "`category_id`=?," +
                    "`name`=?," +
                    "`slug`=?," +
                    "`illustration`=?," +
                    "`subtitle`=?," +
                    "`description`=?," +
                    "`price`=?," +
                    "`quantity`=?" +
                    " WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, product.getCategory_id());
            pst.setString(2, product.getName());
            pst.setString(3, product.getSlug());
            pst.setString(4, product.getIllustration());
            pst.setString(5, product.getSubtitle());
            pst.setString(6, product.getDescription());
            pst.setDouble(7, product.getPrice());
            pst.setInt(8, product.getQuantity());
            pst.setInt(9, product.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erreur:" + ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String query = "DELETE FROM `product` WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erreur:" + ex.getMessage());
        }
    }

    @Override
    public List<Product> afficher() {
        List<Product> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM `product`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Product p = new Product();
                p.setCategory_id(rs.getInt("category_id"));
                p.setDescription(rs.getString("description"));
                p.setName(rs.getString("name"));
                p.setIllustration(rs.getString("illustration"));
                p.setPrice(rs.getDouble("price"));
                p.setId(rs.getInt("id"));
                p.setQuantity(rs.getInt("quantity"));
                p.setSubtitle(rs.getString("subtitle"));
                p.setSlug(rs.getString("slug"));
                list.add(p);
            }

        } catch (SQLException ex) {
            System.out.println("Erreur:" + ex.getMessage());
        }
        return list;
    }
    public List<Product> sortProductByCritere(String critere){
        switch (critere){
            case "name":
                return afficher().stream().sorted(Comparator.comparing(Product::getName))
                        .collect(Collectors.toList());
            case "subtitle":
                return afficher().stream().sorted(Comparator.comparing(Product::getSubtitle))
                        .collect(Collectors.toList());
            case "quantity":
                return afficher().stream().sorted(Comparator.comparing(Product::getQuantity))
                        .collect(Collectors.toList());
            case "price":
                return afficher().stream().sorted(Comparator.comparing(Product::getPrice))
                        .collect(Collectors.toList());
            case "category":
                return afficher().stream().sorted(Comparator.comparing(Product::getCategory_id))
                        .collect(Collectors.toList());
            default:
                return afficher();
        }
    }
    public Product getProductByName(String name){
        return afficher().stream().filter(p->p.getName().equals(name)).findFirst().orElse(null);
    }
}
