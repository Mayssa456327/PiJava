module tn.esprit.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens tn.esprit.test to javafx.fxml;
    exports tn.esprit.test;
    opens utils to javafx.fxml;
    exports utils;
    exports tn.esprit.test.models;
    opens tn.esprit.test.models to javafx.fxml;
    exports tn.esprit.test.Controllers;
    opens tn.esprit.test.Controllers to javafx.fxml;
}