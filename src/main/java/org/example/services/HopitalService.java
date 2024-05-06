package org.example.services;
import org.example.models.Hopital;
import org.example.utils.myDataBase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class HopitalService implements IService<Hopital> {

    private final Connection connection;
    public HopitalService(){
        connection = myDataBase.getInstance().getConnection();
    }
    @Override
    public void add (Hopital hopital) throws SQLException{
        String sql = "insert into hopital (nom,adresse,telephone,email,nombre_chambre) VALUES (?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, hopital.getNom());
            statement.setString(2, hopital.getAdresse());
            statement.setString(3, hopital.getTelephone());
            statement.setString(4,hopital.getEmail());
            statement.setInt(5,hopital.getNombre_chambre());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()){
                int id = generatedKeys.getInt(1);
                hopital.setId(id);
            } else {
                throw new SQLException("failed to get an ID ");
            }
        }

    }

    @Override
    public void update(Hopital hopital) throws SQLException {
        String sql = "update hopital set nom = ?,  adresse = ?, telephone = ? , email = ? , nombre_chambre = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, hopital.getNom());
        preparedStatement.setString(2, hopital.getAdresse());
        preparedStatement.setString(3, hopital.getTelephone());
        preparedStatement.setString(4, hopital.getEmail());
        preparedStatement.setInt(5, hopital.getNombre_chambre());
        preparedStatement.setInt(6, hopital.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        try {
            String sql = "DELETE FROM hopital WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Gérer l'exception ici, comme afficher un message d'erreur ou la journaliser
            throw new SQLException("Failed to delete hopital with ID " + id + ": " + e.getMessage(), e);
        }
    }
    @Override
    public List<Hopital> getAll() throws SQLException {
        List<Hopital> hopitals = new ArrayList<>();
        String sql = "SELECT * FROM hopital";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Hopital hopital = new Hopital(
                        resultSet.getString("Nom"),
                        resultSet.getString("Adresse"),
                        resultSet.getString("Telephone"),
                        resultSet.getString("Email"),
                        resultSet.getInt("nombre_chambre")
                );
                hopitals.add(hopital);
            }
        }
        return hopitals;
    }


    @Override
    public Hopital getById(int id) throws SQLException {
        String sql = "SELECT * FROM hopital WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Hopital(
                        resultSet.getString("nom"),
                        resultSet.getString("adresse"),
                        resultSet.getString("telephone"),
                        resultSet.getString("email"),
                        resultSet.getInt("nombre_chambre")
                );
            } else {
                throw new SQLException("Hopital with id " + id + " not found");
            }
        }
    }

    public Hopital getHospitalByName(String hospitalName) throws SQLException {
        String sql = "SELECT * FROM hopital WHERE nom = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, hospitalName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String adresse = resultSet.getString("adresse");
                String telephone = resultSet.getString("telephone");
                String email = resultSet.getString("email");
                int nombreChambre = resultSet.getInt("nombre_chambre");

                // Créer et retourner l'objet Hopital
                return new Hopital(nom, adresse, telephone, email, nombreChambre);
            } else {
                // Retourner null si aucun hôpital trouvé avec ce nom
                return null;
            }
        }
    }



}
