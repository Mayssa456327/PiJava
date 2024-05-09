package tn.esprit.test.services;

import tn.esprit.test.models.Cert;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CertService implements IService<Cert> {
    private Connection connection ;
    private PreparedStatement st  ;
    private Statement ste  ;

    public ResultSet rs  ;
    public CertService (){
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void add(Cert cert) throws SQLException {
      String sql = "INSERT INTO certs (NomP, IDP, NomM, Description) VALUES ( ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, cert.getNomP());
        preparedStatement.setString(2, cert.getIDP());
        preparedStatement.setString(3, cert.getNomM());
        preparedStatement.setString(4, cert.getDescription());

        preparedStatement.executeUpdate();

    }

    @Override
    public void update(Cert cert) throws SQLException {
        String sql = "UPDATE certs SET NomP=?, IDP=?, NomM=?, Description=? WHERE id=?";

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
        String sql = "DELETE FROM certs WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        preparedStatement.executeUpdate();

    }
    @Override
    public List<Cert> getByIDP(String IDP) throws SQLException {
        List<Cert> searchResults = new ArrayList<>();
        String query = "SELECT * FROM certs WHERE IDP = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, IDP);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            Cert cert = new Cert();
            cert.setId(rs.getInt("id"));
            cert.setNomP(rs.getString("NomP"));
            cert.setIDP(rs.getString("IDP"));
            cert.setNomM(rs.getString("NomM"));
          //  cert.setDate(rs.getDate("Date"));
            cert.setDescription(rs.getString("Description"));
            searchResults.add(cert);
        }

        return searchResults;
    }


    @Override
    public List<Cert> getAll() throws SQLException {

        String query = "SELECT * FROM certs";
        System.out.println(query);
        List<Cert> Certs = new ArrayList<>();



        try {

            Statement st = connection.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                Cert cert = new Cert();
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




}
