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
    private Button btnNuevaReserva;
    private Button btnMisReservas;
    private Button btnCerrarSesion;
    @FXML
    private Button reservesButton;
    @FXML
    private Button logOutButton;

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
            Scene scene = btn.getScene();
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            NavegationController nav = new NavegationController(scene);
            nav.goingTo(root);
        } catch (Exception e) {
            System.out.println("Error al cargar: " + fxml + " — " + e.getMessage());
        }
    }
    
    @FXML
    private void switchToReserves(ActionEvent event) {
        cargarPantalla("/cr/ac/una/reservauna/Views/reserve.fxml",reservesButton);
    }

    @FXML
    private void logOut(ActionEvent event) {
        cargarPantalla("/cr/ac/una/reservauna/Views/login.fxml", logOutButton);
    }
}