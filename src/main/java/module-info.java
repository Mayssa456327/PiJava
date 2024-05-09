module tn.esprit.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires itextpdf;
    requires com.google.zxing;
    requires com.google.api.client.auth;
    requires com.google.api.client.extensions.java6.auth;
    requires google.api.client;
    requires com.google.api.client;
    requires com.google.api.client.json.jackson2;


    opens tn.esprit.test to javafx.fxml;
    exports tn.esprit.test;
    opens utils to javafx.fxml;
    exports utils;
    exports tn.esprit.test.models;
    opens tn.esprit.test.models to javafx.fxml;
    exports tn.esprit.test.Controllers;
    opens tn.esprit.test.Controllers to javafx.fxml;

}