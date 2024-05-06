package com.example.pidevjava.services;

import com.example.pidevjava.models.Evenement;
import com.example.pidevjava.test.HelloApplication;
import com.example.pidevjava.utils.MyDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import com.example.pidevjava.services.IService;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;*/

public class ServiceEvenement implements IService<Evenement> {
    private static Connection cnx;

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


    }

    @Override
    public void update(Evenement evenement) throws SQLException {
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
        preparedStatement.setInt(1, evenement.getId());
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

    public void changeScreen(ActionEvent event, String s, String afficherBackEvenement) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(s));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(afficherBackEvenement);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int getEventIdByName(String eventName) throws SQLException {
        String sql = "SELECT id FROM evenement WHERE nom_evenement = ?";
        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setString(1, eventName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        }
        return -1; // Retourne -1 si l'événement n'est pas trouvé
    }


    public List<String> getAllEventNames() throws SQLException {
        List<String> eventNames = new ArrayList<>();
        String sql = "SELECT nom_evenement FROM evenement";
        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                eventNames.add(resultSet.getString("nom_evenement"));
            }
        }
        return eventNames;
    }
    public String getNomEvenementById(int id) throws SQLException {
        String sql = "SELECT nom_evenement FROM evenement WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString("nom_evenement");
            }
        } catch (SQLException e) {
            System.err.println("Error getting event name by ID: " + e.getMessage());
        }
        return null; // Return null if event with the given ID is not found
    }



    public int getIdByNomEvenement(String nomEvenement) throws SQLException {
        String sql = "SELECT id FROM evenement WHERE nom_evenement = ?";
        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setString(1, nomEvenement);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Error getting event ID by name: " + e.getMessage());
        }
        return -1; // Return -1 if event with the given name is not found
    }


    public List<Integer> getAllEventIds() throws SQLException {
        List<Integer> eventIds = new ArrayList<>();
        String sql = "SELECT id FROM evenement";
        try (Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int eventId = resultSet.getInt("id");
                eventIds.add(eventId);
            }
        }
        return eventIds;
    }

    public List<Evenement> afficherbyNOM(String tri) {
        String sql = "select * from evenement";
        List<Evenement> evenements = new ArrayList<>();
        try (Statement statement = cnx.createStatement();
             ResultSet rs = statement.executeQuery(sql);) {

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
        } catch (SQLException e) {
            System.err.println("Error retrieving events: " + e.getMessage());
        }
        if (tri.equals("ASC")) {
            Collections.sort(evenements, Comparator.comparing(Evenement::getNom_evenement));
        } else {
            Collections.sort(evenements, Comparator.comparing(Evenement::getNom_evenement).reversed());
        }
        return evenements;
    }

}