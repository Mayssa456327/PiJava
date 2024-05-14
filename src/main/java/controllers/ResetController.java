package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import utils.MyDB;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ResetController implements Initializable {
    @FXML
    private TextField new_password;
    @FXML
    private TextField verify_pwd;
    @FXML
    private Button reset_btn;

    private final MyDB myDB = MyDB.getInstance();
    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    @FXML
    public void reset_psw() {
        String newPassword = new_password.getText();
        String verifyPassword = verify_pwd.getText();

        // Check if the new password and verify password fields are not empty
        if (newPassword.isEmpty() || verifyPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill in both New Password and Verify Password fields!");
            return;
        }

        // Check if the new password matches the verify password
        if (!newPassword.equals(verifyPassword)) {
            showAlert(Alert.AlertType.ERROR, "New Password and Verify Password fields do not match!");
            return;
        }

        // Get the email of the currently logged-in user
        String email = getEmailFromLoggedInUser();
        if (email == null) {
            showAlert(Alert.AlertType.ERROR, "Failed to retrieve user email. Please log in again.");
            return;
        }

        // Update the password in the database
        try (Connection connection = MyDB.getInstance().getConnection()) {
            String sql = "UPDATE user SET password = ? WHERE mail = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newPassword);
            statement.setString(2, email);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Password successfully reset!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to reset password. Please try again later.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "An error occurred while resetting the password: " + e.getMessage());
        }
    }

    // Method to get the email of the currently logged-in user
    private String getEmailFromLoggedInUser() {
        // Implement your logic to retrieve the email of the currently logged-in user
        // Here, you can use your application's authentication mechanism to get the user's email
        // Replace this method with your actual implementation
        // For example:
        // return currentUser.getEmail();
        // or retrieve it from the session if you're using a web application
        return "boubakerwessim@gmail.com";
    }

    // Helper method to show alerts
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("DocDirect");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    /*
    public void initData(String email) {
        // Set the email in the email field or perform any other initialization
        emailField.setText(email);
    }

     */

    public void textfieldDesign(){
        if (new_password.isFocused()){
            new_password.setStyle("-fx-background-color:#fff;"
                    + "-fx-border-width:2px");
            verify_pwd.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");


        }else if (verify_pwd.isFocused()){
            new_password.setStyle("-fx-background-color:transparent;"
                    + "-fx-border-width:1px");
            verify_pwd.setStyle("-fx-background-color:#fff;"
                    + "-fx-border-width:2px");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
