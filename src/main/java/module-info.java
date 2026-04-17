module cr.ac.una.reservauna {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens cr.ac.una.reservauna to javafx.fxml;
    exports cr.ac.una.reservauna;
}
