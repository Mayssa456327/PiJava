package services;

import entities.User;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser implements IService<User> {

    private Connection con;

    public ServiceUser(){
        con = MyDB.getInstance().getConnection();
    }

    public void ajouter(User user) {
        String req = "INSERT INTO User(nom, prenom, mail, role, numeroTelephone, password, ville, sexe, profile_image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement st = con.prepareStatement(req);
            st.setString(1, user.getNom());
            st.setString(2, user.getPrenom());
            st.setString(3, user.getMail());
            st.setString(4, user.getRole());
            st.setString(5, user.getNumeroTelephone());
            st.setString(6, user.getPassword());
            st.setString(7, user.getVille());
            st.setString(8, user.getSexe());
            st.setString(9, user.getProfileImage());

            st.executeUpdate();
            System.out.println("Compte successfully created !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(User user) throws SQLException {
        String req = "UPDATE User SET nom=?, prenom=?, mail=?, role=?, numeroTelephone=? , password=?, ville=?, sexe=? WHERE id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, user.getNom());
        pre.setString(2, user.getPrenom());
        pre.setString(3, user.getMail());
        pre.setString(4, user.getRole());
        pre.setString(5, user.getNumeroTelephone());
        pre.setString(6, user.getPassword());
        pre.setString(7, user.getVille());
        pre.setString(8, user.getSexe());

        pre.setInt(9, user.getId());

        pre.executeUpdate();
    }


    @Override
    public void supprimer(User user) {
        try {
            String requete="delete from user where id=?";
            PreparedStatement pst = con.prepareStatement(requete);
            pst.setInt(1,user.getId());
            pst.executeUpdate();

            System.out.println("Utlisateur est supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public boolean idExists(int userId) {
        try {
            String query = "SELECT COUNT(*) FROM User WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        } catch (SQLException e) {
            System.out.println("Error checking if ID exists: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<User> afficher() throws SQLException {
        List<User> users = new ArrayList<>();

        String req = "SELECT * FROM User";
        PreparedStatement pre = con.prepareStatement(req);
        ResultSet res = pre.executeQuery();
        while (res.next()){
            User user = new User();
            user.setId(res.getInt("id"));
            user.setNom(res.getString("nom"));
            user.setPrenom(res.getString("prenom"));
            user.setMail(res.getString("mail"));
            user.setRole(res.getString("role"));
            user.setNumeroTelephone(res.getString("numeroTelephone"));
            user.setPassword(res.getString("password"));
            user.setVille(res.getString("ville"));
            user.setSexe(res.getString("sexe"));
            user.setProfileImage(res.getString("profile_image"));
            users.add(user);
        }

        return users;
    }
    public PreparedStatement sendPass() throws SQLException {
        System.out.println("cxcccccccccccccccccc");
        String query2="select * from user where mail=? ";
        PreparedStatement smt = con.prepareStatement(query2);
        return smt;
    }

}
