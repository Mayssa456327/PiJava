package test;

import entities.User;
import services.ServiceUser;
import utils.MyDB;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        MyDB conn1 = MyDB.getInstance();

        // Création de deux utilisateurs avec les nouveaux attributs en utilisant le constructeur
        User p1 = new User("Ben Slimene", "Kenza", "kenza@example.com", "Utilisateur standard", "123456789", "motdepasse123", "Paris", "Féminin", "chemin/vers/image1.jpg");
        User p2 = new User( "Msahli", "ALi", "ali@example.com", "Administrateur", "987654321", "motdepasse456", "Tunis", "Masculin", "chemin/vers/image2.jpg");

        // Instanciation du service utilisateur
        ServiceUser s = new ServiceUser();

        // Ajout des utilisateurs à la base de données
        s.ajouter(p1);
        s.ajouter(p2);

        try {
            // Affichage des utilisateurs
            System.out.println(s.afficher());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            // Modification de l'utilisateur p1
            s.modifier(p1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

