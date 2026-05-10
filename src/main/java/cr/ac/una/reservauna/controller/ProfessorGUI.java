package cr.ac.una.reservauna.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ProfessorGUI implements Initializable {

    @FXML private Label LabelNombre;
    @FXML private Label LabelCorreo;
    @FXML private Label LabelReservasActivas;
    @FXML private Label LabelPendientes;
    @FXML private Label LabelRecursosDisponible;
    @FXML private Label LabelCupoRestante;
    @FXML private TableView TablaReservas;
    @FXML private TableColumn colRecurso;
    @FXML private TableColumn colFecha;
    @FXML private TableColumn colEstado;
    @FXML private Button btnNuevaReserva;
    @FXML private Button btnMisReservas;
    @FXML private Button btnCerrarSesion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LabelNombre.setText("Carlos Mora");
        LabelCorreo.setText("cmora@una.ac.cr");
        LabelReservasActivas.setText("2");
        LabelPendientes.setText("1");
        LabelRecursosDisponible.setText("24");
        LabelCupoRestante.setText("1");
    }

    private void cargarPantalla(String fxml, Button btn) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML private void btnIngresarArchivo(ActionEvent e) {}
    @FXML private void btnIngresarReservas(ActionEvent e) {
        cargarPantalla("/cr/ac/una/reservauna/Views/reserve.fxml", btnMisReservas);
    }
    @FXML private void btnIngresarAyuda(ActionEvent e) {}
    @FXML private void btnIngresarNuevaReserva(ActionEvent e) {
        cargarPantalla("/cr/ac/una/reservauna/Views/reserve.fxml", btnNuevaReserva);
    }
    @FXML private void btnIngresarMisReservas(ActionEvent e) {
        cargarPantalla("/cr/ac/una/reservauna/Views/reserve.fxml", btnMisReservas);
    }
    @FXML private void btnIngresarCalendario(ActionEvent e) {}
    @FXML private void btnIngresarNuevaReserva2(ActionEvent e) {
        cargarPantalla("/cr/ac/una/reservauna/Views/reserve.fxml", btnNuevaReserva);
    }
    @FXML private void btnIngresarVerCalendario(ActionEvent e) {}
    @FXML private void btnIngresarMisReservas2(ActionEvent e) {
        cargarPantalla("/cr/ac/una/reservauna/Views/reserve.fxml", btnMisReservas);
    }
    @FXML private void btnRegresarInicio(ActionEvent e) {
        cargarPantalla("/cr/ac/una/reservauna/Views/login.fxml", btnCerrarSesion);
    }
}