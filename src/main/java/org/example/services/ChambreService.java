package org.example.services;

import org.example.models.Chambre;
import org.example.models.Hopital;
import org.example.models.Reservation;
import org.example.utils.myDataBase;
import org.example.services.ReservationService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChambreService implements IService<Chambre> {

    private final Connection connection;

    public ChambreService() {
        connection = myDataBase.getInstance().getConnection();
    }


    @Override
    public void add(Chambre chambre) throws SQLException {

        // Vérifie le nombre de chambres déjà associées à l'hôpital
        int nombreChambresExistantes = chambreExistants(chambre.getHopital_id());
        System.out.println(nombreChambresExistantes);

        // Vérifie si le nombre de chambres existantes dépasse la limite
        if (nombreChambresExistantes >= getNombreChambresForHopital(chambre.getHopital_id())) {
            throw new SQLException("Le nombre maximal de chambres pour cet hôpital a été atteint.");
        }
        String sql = "INSERT INTO chambre (disponibiliter, hopital_id, reservation_id) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setBoolean(1, chambre.getDisponibiliter());
            // Set hospital ID
            statement.setInt(2, chambre.getHopital_id());
            statement.setInt(3, chambre.getReservation_id());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                chambre.setId(id);
            } else {
                throw new SQLException("Failed to get an ID for the chambre.");
            }
        }
    }

    @Override
    public void update(Chambre chambre) throws SQLException {
        String sql = "update chambre set disponibiliter = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setBoolean(1, chambre.getDisponibiliter());
        preparedStatement.setInt(2, chambre.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        try {
            String sql = "DELETE FROM chambre WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Gérer l'exception ici, comme afficher un message d'erreur ou la journaliser
            throw new SQLException("Failed to delete chambre with ID " + id + ": " + e.getMessage(), e);
        }

    }

    @Override
    public List<Chambre> getAll() throws SQLException {
        List<Chambre> chambres = new ArrayList<>();
        String sql = "SELECT c.* FROM chambre c JOIN hopital h ON c.hopital_id = h.id";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Chambre chambre = new Chambre(
                        resultSet.getInt("id"),
                        resultSet.getBoolean("disponibiliter"),
                        resultSet.getInt("hopital_id"),
                        resultSet.getInt("reservation_id")
                );
                chambres.add(chambre);
            }
        }
        return chambres;
    }

    @Override
    public Chambre getById(int id) throws SQLException {
        String sql = "SELECT * FROM chambre WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                boolean disponibiliter = resultSet.getBoolean("disponibiliter");
                int hopitalId = resultSet.getInt("hopital_id");
                int reservationId = resultSet.getInt("reservation_id");
                // Création d'une nouvelle chambre avec les données récupérées
                return new Chambre(id, disponibiliter,hopitalId, reservationId);
            }
        }
        return null; // Retourne null si aucune chambre trouvée avec cet identifiant

    }

    public int getNombreChambresForHopital(int hopitalId) throws SQLException {
        String sql = "SELECT nombre_chambre FROM hopital WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, hopitalId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        }
    }

    public int chambreExistants(int hopitalId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM chambre WHERE hopital_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, hopitalId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        }
    }


}
