package tn.esprit.test.services;

import tn.esprit.test.models.DemAn;
import tn.esprit.test.models.OrdMed;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DemAnService implements IService2<DemAn> {
    private Connection connection ;
    private PreparedStatement st  ;
    private Statement ste  ;

    public ResultSet rs  ;
    public DemAnService (){
        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void add(DemAn cert) throws SQLException {
        String sql = "INSERT INTO deman (NomP, IDP, NomM,  Type ,Description ) VALUES ( ?, ?, ?, ? , ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, cert.getNomP());
        preparedStatement.setString(2, cert.getIDP());
        preparedStatement.setString(3, cert.getNomM());
        preparedStatement.setString(4, cert.getType());
        preparedStatement.setString(5, cert.getDescription());


        preparedStatement.executeUpdate();

    }

    @Override
    public void update(DemAn cert) throws SQLException {
        String sql = "UPDATE deman SET NomP=?, IDP=?, NomM=?, Description=? , Type=? WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, cert.getNomP());
        preparedStatement.setString(2, cert.getIDP());
        preparedStatement.setString(3, cert.getNomM());
        preparedStatement.setString(4, cert.getDescription());
        preparedStatement.setString(5, cert.getType());
        preparedStatement.setInt(6, cert.getId());

        preparedStatement.executeUpdate();

    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM deman WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        preparedStatement.executeUpdate();

    }

    @Override
    public List<DemAn> getAll() throws SQLException {

        String query = "SELECT * FROM deman";
        System.out.println(query);
        List<DemAn> Certs = new ArrayList<>();



        try {

            Statement st = connection.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                DemAn cert = new DemAn();
                cert.setId(rs.getInt("id"));
                cert.setNomP(rs.getString("NomP"));
                cert.setIDP(rs.getString("IDP"));
                cert.setNomM(rs.getString("NomM"));
                // cert.setDate(rs.getDate("Date"));
                cert.setDescription(rs.getString("Description"));
                cert.setType(rs.getString("Type"));
                Certs.add(cert);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Certs;
    }

    @Override
    public DemAn getById(int id) throws SQLException {
        return null;
    }
}
