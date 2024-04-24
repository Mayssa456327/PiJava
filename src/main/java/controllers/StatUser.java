package controllers;

import utils.MyDB;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;

public class StatUser implements Initializable {

    @FXML
    private PieChart StatUser;
    private Statement st;
    private ResultSet rs;
    private Connection cnx;
    ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
    int n;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cnx = MyDB.getInstance().getConnection();
        stat();
    }

    private void stat() {
        try {

            String query = "select COUNT(*),`role` from user GROUP BY `role`;";

            PreparedStatement PreparedStatement = cnx.prepareStatement(query);
            rs = PreparedStatement.executeQuery();
            while (rs.next()) {
                data.add(new PieChart.Data(rs.getString("role"), rs.getInt("COUNT(*)")));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        StatUser.setTitle("**Statistiques Des Utilisateurs **");
        StatUser.setLegendSide(Side.LEFT);
        StatUser.setData(data);
    }
}


