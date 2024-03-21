package com.example.pidevjava.services;

import com.example.pidevjava.models.Evenement;
import com.example.pidevjava.utils.MyDatabase;
//import com.example.pidevjava.services.IService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;*/

public class ServiceEvenement implements IService<Evenement> {
    private Connection cnx;

    public ServiceEvenement() {
        cnx = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void add(Evenement evenement) throws SQLException {

        String sql = "INSERT INTO evenement (image_evenement, type_evenement, nom_evenement, lieu_evenement,date_debut,date_fin,budget) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement pre = cnx.prepareStatement(sql)) {
            pre.setString(1, evenement.getImage_evenement());
            pre.setString(2, evenement.getType_evenement());
            pre.setString(3, evenement.getNom_evenement());
            pre.setString(4, evenement.getLieu_evenement());
            pre.setString(5, String.valueOf(evenement.getDate_debut()));
            pre.setString(6, String.valueOf(evenement.getDate_fin()));
            pre.setDouble(7, evenement.getBudget());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding event: " + e.getMessage());
        }

       /* String sql = "INSERT INTO evenement (image_evenement, type_evenement, nom_evenement, lieu_evenement,date_debut,date_fin,budget) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pre = cnx.prepareStatement(sql)) {
            pre.setString(1, evenement.getImage_evenement());
            pre.setString(2, evenement.getType_evenement());
            pre.setString(3, evenement.getLieu_evenement());
            pre.setString(4, String.valueOf(evenement.getDate_debut()));
            pre.setString(5, String.valueOf(evenement.getDate_fin()));
            pre.setDouble(6, evenement.getBudget());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding event: " + e.getMessage());
        }*/
    }
    @Override
    public void update(Evenement evenement) throws SQLException {
       /* String sql = "update evenement set image_evenement = ?,  type_evenement = ?,  nom_evenement = ?,  lieu_evenement = ?,  date_debut = ?,  date_debut = ?,  date_fin = ?,  budget = ?  where id = ?";
        PreparedStatement preparedStatement = cnx.prepareStatement(sql);
        preparedStatement.setString(1, evenement.getImage_evenement());
        preparedStatement.setString(2, evenement.getType_evenement());
        preparedStatement.setString(3, evenement.getNom_evenement());
        preparedStatement.setString(4, evenement.getLieu_evenement());
        preparedStatement.setString(5, String.valueOf(evenement.getDate_debut()));
        preparedStatement.setString(6, String.valueOf(evenement.getDate_fin()));
        preparedStatement.setDouble(7, evenement.getBudget());
        preparedStatement.executeUpdate();*/
        String sql = "update evenement set image_evenement = ?,  type_evenement = ?,  nom_evenement = ?,  lieu_evenement = ?,  date_debut = ?,  date_fin = ?,  budget = ?  where id = ?";
        PreparedStatement preparedStatement = cnx.prepareStatement(sql);
        preparedStatement.setString(1, evenement.getImage_evenement());
        preparedStatement.setString(2, evenement.getType_evenement());
        preparedStatement.setString(3, evenement.getNom_evenement());
        preparedStatement.setString(4, evenement.getLieu_evenement());
        preparedStatement.setString(5, String.valueOf(evenement.getDate_debut()));
        preparedStatement.setString(6, String.valueOf(evenement.getDate_fin()));
        preparedStatement.setDouble(7, evenement.getBudget());
        preparedStatement.setInt(8, evenement.getId());

        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(Evenement evenement) throws SQLException {

        String sql = "delete from evenement where id = ?";
        PreparedStatement preparedStatement = cnx.prepareStatement(sql);
        preparedStatement.setInt(1,evenement.getId());
        preparedStatement.executeUpdate();

}
    @Override
    public List<Evenement> getAll() throws SQLException {
        String sql = "select * from evenement";
        Statement statement = cnx.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Evenement> evenements = new ArrayList<>();
        while (rs.next()) {
            Evenement evenement = new Evenement();
            evenement.setId(rs.getInt("id"));
            evenement.setImage_evenement(rs.getString("Image_evenement"));
            evenement.setType_evenement(rs.getString("Type_evenement"));
            evenement.setNom_evenement(rs.getString("Nom_evenement"));
            evenement.setLieu_evenement(rs.getString("Lieu_evenement"));
            evenement.setDate_debut(rs.getTimestamp("Date_debut").toLocalDateTime());
            evenement.setDate_fin(rs.getTimestamp("Date_fin").toLocalDateTime());
            evenement.setBudget(rs.getDouble("Budget"));
            evenements.add(evenement);
        }
        return evenements; // Return the populated list of events
    }


    @Override
    public Evenement getById(int id) throws SQLException {

        return null;
    }
}
