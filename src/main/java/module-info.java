module com.example.pidevjava {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.pidevjava to javafx.fxml;
    //exports com.example.pidevjava;
    exports com.example.pidevjava.test;
    opens com.example.pidevjava.test to javafx.fxml;
}