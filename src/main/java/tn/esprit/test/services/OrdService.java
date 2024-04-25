package tn.esprit.test.services;

import tn.esprit.test.models.OrdMed;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdService implements IService1 <OrdMed> {
    private Connection connection ;
    private PreparedStatement st  ;
    private Statement ste  ;

    public ResultSet rs  ;
    public OrdService (){
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void add(OrdMed cert) throws SQLException {
        String sql = "INSERT INTO ordmed (NomP, IDP, NomM, Description) VALUES ( ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, cert.getNomP());
        preparedStatement.setString(2, cert.getIDP());
        preparedStatement.setString(3, cert.getNomM());
        preparedStatement.setString(4, cert.getDescription());

        preparedStatement.executeUpdate();

    }

    @Override
    public void update(OrdMed cert) throws SQLException {
        String sql = "UPDATE ordmed SET NomP=?, IDP=?, NomM=?, Description=? WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, cert.getNomP());
        preparedStatement.setString(2, cert.getIDP());
        preparedStatement.setString(3, cert.getNomM());
        preparedStatement.setString(4, cert.getDescription());
        preparedStatement.setInt(5, cert.getId());

        preparedStatement.executeUpdate();

    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM ordmed WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        preparedStatement.executeUpdate();

    }

    @Override
    public List<OrdMed> getAll() throws SQLException {

        String query = "SELECT * FROM ordmed";
        System.out.println(query);
        List<OrdMed> Certs = new ArrayList<>();



        try {

            Statement st = connection.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                OrdMed cert = new OrdMed();
                cert.setId(rs.getInt("id"));
                cert.setNomP(rs.getString("NomP"));
                cert.setIDP(rs.getString("IDP"));
                cert.setNomM(rs.getString("NomM"));
                // cert.setDate(rs.getDate("Date"));
                cert.setDescription(rs.getString("Description"));
                Certs.add(cert);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Certs;
    }

    @Override
    public OrdMed getById(int id) throws SQLException {
        return null;
    }
}
