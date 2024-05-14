package services;



import entities.Sponsor;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class ServiceSponsor3 implements IService3<Sponsor> {
        private Connection cnx;
        public ServiceSponsor3() {
            cnx = MyDB.getInstance().getConnection();
        }
        @Override

        public void add(Sponsor sponsor) throws SQLException {
            String req = "INSERT INTO sponsor (budget, evenement_id, nom_sponsor, email_sponsor, adresse) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pre = cnx.prepareStatement(req)) {
                pre.setDouble(1, sponsor.getBudget());
                pre.setInt(2, sponsor.getEvenement_id());
                pre.setString(3, sponsor.getNom_sponsor());
                pre.setString(4, sponsor.getEmail_sponsor());
                pre.setString(5, sponsor.getAdresse());

                int rowsAffected = pre.executeUpdate();
                if (rowsAffected == 0) {
                    System.err.println("Failed to add sponsor: No rows affected.");
                } else {
                    System.out.println("Sponsor added successfully.");
                }
            } catch (SQLException e) {
                System.err.println("Error adding sponsor: " + e.getMessage());
            }
        }

    @Override
    public void  update(Sponsor sponsor) throws SQLException {
        String sql = "UPDATE sponsor SET budget=?, evenement_id=?, nom_sponsor=?, email_sponsor=?, adresse=? WHERE id=?";
        PreparedStatement pre = cnx.prepareStatement(sql);

        pre.setDouble(1, sponsor.getBudget());
        pre.setInt(2, sponsor.getEvenement_id());
        pre.setString(3, sponsor.getNom_sponsor());
        pre.setString(4, sponsor.getEmail_sponsor());
        pre.setString(5, sponsor.getAdresse());
        pre.setInt(6, sponsor.getId());

        pre.executeUpdate();

    }

        @Override
        public void delete(Sponsor sponsor) throws SQLException {
            String sql = "DELETE FROM sponsor WHERE id=?";
            try (PreparedStatement pre = cnx.prepareStatement(sql)) {
                pre.setInt(1, sponsor.getId());
                pre.executeUpdate();
            }
        }

        @Override
        public List<Sponsor> getAll() throws SQLException {
            String sql = "SELECT sponsor.*, evenement.id " +
                    "FROM sponsor " +
                    " INNER JOIN evenement  ON sponsor.evenement_id = evenement.id ";
            Statement statement = cnx.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            List<Sponsor> sponsors = new ArrayList<>();
            while (rs.next()) {
                Sponsor sponsor = new Sponsor();
                sponsor.setId(rs.getInt("id"));
                sponsor.setBudget(rs.getDouble("Budget"));
                sponsor.setEvenement_id(rs.getInt("Evenement_id"));
                sponsor.setNom_sponsor(rs.getString("Nom_sponsor"));
                sponsor.setEmail_sponsor(rs.getString("Email_sponsor"));
                sponsor.setAdresse(rs.getString("Adresse"));
                sponsors.add(sponsor);
            }
            return sponsors ;
        }


    @Override
            public Sponsor getById(int id) throws SQLException {

                return null;
            }
}

