package services;


import entities.Category;
import utils.Myconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceCategory implements IService2<Category> {
    private Connection cnx;

    public ServiceCategory() {
        cnx = Myconnection.getInstance().getCnx();
    }

    @Override
    public void ajouter(Category category) {
        try {
            String query = "INSERT INTO `category`" +
                    "( `name`)" +
                    " VALUES (?)";
            PreparedStatement pst = cnx.prepareStatement(query);

            pst.setString(1, category.getName());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erreur:" + ex.getMessage());
        }


    }

    @Override
    public void modifier(Category category) {
        try {
            String query = "UPDATE `category` SET " +

                    "`name`=?," +
                    " WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(query);

            pst.setString(1, category.getName());
            pst.setInt(2, category.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erreur:" + ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String query = "DELETE FROM `category` WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erreur:" + ex.getMessage());
        }
    }

    @Override
    public List<Category> afficher() {
        List<Category> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM `category`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Category c = new Category();
                c.setName(rs.getString("name"));
                c.setId(rs.getInt("id"));
                list.add(c);
            }

        } catch (SQLException ex) {
            System.out.println("Erreur:" + ex.getMessage());
        }
        return list;
    }
    public List<String> getCategoriesNames(){
        return afficher().stream().map(c->c.getName()).collect(Collectors.toList());
    }
    public int getIdByName(String name){
        return afficher().stream().filter(c->c.getName().equals(name)).findFirst().orElse(null).getId();
    }
    public Category getById(int id){
        return afficher().stream().filter(c->c.getId()==id).findFirst().orElse(null);

    }
}
