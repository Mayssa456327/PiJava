

module com.example.pidevjava {

    requires javafx.graphics;
    requires java.sql;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.swing;
    requires javafx.controls;
    requires freetts;
    requires org.apache.pdfbox;
    // other dependencies and declarations
       // exports com.example.pidevjava.controllers;
    exports com.example.pidevjava.test;

    exports com.example.pidevjava.controllers;
    opens com.example.pidevjava.controllers;
    opens com.example.pidevjava.test to javafx.fxml;
    exports com.example.demo;
    opens com.example.demo;


}
