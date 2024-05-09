package org.example.services;

import org.example.models.Hopital;
import org.example.models.Reservation;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.example.utils.myDataBase;
import java.sql.*;
import org.example.services.ChambreService;

public class ReservationService implements IService<Reservation>{

    private Connection connection;
    public ReservationService(){
        connection = myDataBase.getInstance().getConnection();
    }
    private final ChambreService chambreService = new ChambreService();

    @Override
    public void add(Reservation reservation) throws SQLException {

        // Récupérer les hôpitaux avec des chambres disponibles
        List<Hopital> hopitauxDisponibles = chambreService.getHopitauxAvecChambresDisponibles();

        // Vérifier si l'hôpital sélectionné est dans la liste des hôpitaux disponibles
        boolean hopitalDisponible = hopitauxDisponibles.stream()
                .anyMatch(hopital -> hopital.getNom().equals(reservation.getHopital().getNom()));

        if (!hopitalDisponible) {
            throw new SQLException("Aucune chambre disponible dans cet hôpital.");
        }
        String sql = "INSERT INTO reservation (nom_patient, date_debut, date_fin, hopital_id, email, telephone) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, reservation.getNom_patient());
            statement.setString(2, reservation.getDate_debut());
            statement.setString(3, reservation.getDate_fin());

            // Get hospital ID by name
            int hopitalId = getHospitalIdByName(reservation.getHopital().getNom());

            // Set hospital ID
            statement.setInt(4, hopitalId);

            statement.setString(5, reservation.getEmail());
            statement.setString(6, reservation.getTelephone());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                reservation.setId(id);
            } else {
                throw new SQLException("Failed to get an ID");
            }

        }
    }




    @Override
    public void update(Reservation reservation) throws SQLException {
        String sql = "update reservation set nom_patient = ?,  date_debut = ?, date_fin= ?,  email = ? ,telephone = ?  where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, reservation.getNom_patient());
        preparedStatement.setString(2, reservation.getDate_debut());
        preparedStatement.setString(3, reservation.getDate_fin());
        preparedStatement.setString(4, reservation.getEmail());
        preparedStatement.setString(5, reservation.getTelephone());
        preparedStatement.setInt(6, reservation.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        try {
            String sql = "DELETE FROM reservation WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Gérer l'exception ici, comme afficher un message d'erreur ou la journaliser
            throw new SQLException("Failed to delete reservation with ID " + id + ": " + e.getMessage(), e);
        }

    }

    @Override
    public List<Reservation> getAll() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT r.* FROM reservation r JOIN hopital h ON r.hopital_id = h.id";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Hopital hopital = new Hopital();
                Reservation reservation = new Reservation(
                        resultSet.getInt("hopital_id"),
                        resultSet.getString("nom_patient"),
                        resultSet.getString("date_debut"),
                        resultSet.getString("date_fin"),
                        hopital,
                        resultSet.getString("email"),
                        resultSet.getString("telephone")
                );
                reservations.add(reservation);
            }
        }
        return reservations;
    }





    @Override
    public Reservation getById(int id) throws SQLException {
        String sql = "SELECT r.*, h.nom_hopital FROM reservation r JOIN hopital h ON r.hopital_id = h.id WHERE r.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Hopital hopital = new Hopital();
                hopital.setNom(resultSet.getString("nom_hopital"));

                Reservation reservation = new Reservation(
                        resultSet.getInt("hopital_id"),
                        resultSet.getString("nom_patient"),
                        resultSet.getString("date_debut"),
                        resultSet.getString("date_fin"),
                        hopital,
                        resultSet.getString("email"),
                        resultSet.getString("telephone")
                );
                reservation.setId(id);
                return reservation;
            }
        }
        return null;
    }
    public List<String> getAllHospitalNames() throws SQLException {
        List<String> hospitalNames = new ArrayList<>();
        String sql = "SELECT nom FROM hopital"; // Assuming 'nom' is the column name for hospital names
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String hospitalName = resultSet.getString("nom");
                hospitalNames.add(hospitalName);
            }
        }
        return hospitalNames;
    }
    public int getHospitalIdByName(String hospitalName) throws SQLException {
        // Get all hospital names
        List<String> hospitalNames = getAllHospitalNames();

        // Check if the desired hospital name exists in the list
        if (!hospitalNames.contains(hospitalName)) {
            throw new SQLException("Hospital with name " + hospitalName + " not found");
        }

        // If the hospital name exists, proceed to get its ID
        String sql = "SELECT id FROM hopital WHERE nom = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, hospitalName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                throw new SQLException("Hospital with name " + hospitalName + " not found");
            }
        }
    }


}
