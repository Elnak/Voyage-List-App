module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires shared;

    exports project_prog_b2_byloos_lietar.client.views;
    opens project_prog_b2_byloos_lietar.client.views to javafx.fxml;
    exports project_prog_b2_byloos_lietar.client.controllers;
    opens project_prog_b2_byloos_lietar.client.controllers to javafx.fxml;
}