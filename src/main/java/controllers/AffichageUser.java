package controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
public class AffichageUser {
    @FXML
    private Label lbNom;

    @FXML
    private Label lbPrenom;

    public void setLbNom(String nom) {
        lbNom.setText(nom);
    }

    public void setLbPrenom(String prenom) {
        lbPrenom.setText(prenom);
    }
   // public void setLbAge(int age) {
       // lbPrenom.setText(String.valueOf(age));
    //}
}
