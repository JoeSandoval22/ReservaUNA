package cr.ac.una.reservauna.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ReserveController implements Initializable {

    @FXML private TableView tblReservas;
    @FXML private TableColumn colId;
    @FXML private TableColumn colRecurso;
    @FXML private TableColumn colInicio;
    @FXML private TableColumn colFin;
    @FXML private TableColumn colMotivo;
    @FXML private TableColumn colEstado;
    @FXML private ComboBox cmbRecurso;
    @FXML private ComboBox cmbEstado;
    @FXML private ComboBox cmbFiltroEstado;
    @FXML private DatePicker dpInicio;
    @FXML private DatePicker dpFin;
    @FXML private DatePicker dpFechaInicio;
    @FXML private DatePicker dpFechaFin;
    @FXML private TextField txtHoraInicio;
    @FXML private TextField txtHoraFin;
    @FXML private TextField txtMotivo;
    @FXML private TextArea txtMotivoCancel;
    @FXML private Label lblDisponibilidad;
    @FXML private Label lblMensaje;
    @FXML private Button btnGuardar;
    @FXML private Button btnCancelar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnRegresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbEstado.getItems().addAll("PENDIENTE","APROBADA","RECHAZADA","CANCELADA");
        cmbFiltroEstado.getItems().addAll("Todos","PENDIENTE","APROBADA","RECHAZADA","CANCELADA");
    }

    @FXML
    private void BtnVerificar(ActionEvent event) {
        if (cmbRecurso.getValue() == null || dpInicio.getValue() == null || dpFin.getValue() == null) {
            lblDisponibilidad.setText("Complete todos los campos primero.");
            lblDisponibilidad.setStyle("-fx-text-fill: red;");
            return;
        }
        lblDisponibilidad.setText("Disponible — sin traslapes.");
        lblDisponibilidad.setStyle("-fx-text-fill: green;");
    }

    @FXML
    private void BtnGuardar(ActionEvent event) {
        if (cmbRecurso.getValue() == null || txtMotivo.getText().isEmpty()
                || dpInicio.getValue() == null || dpFin.getValue() == null
                || txtHoraInicio.getText().isEmpty() || txtHoraFin.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Completá todos los campos obligatorios.");
            return;
        }
        mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Reserva creada con estado PENDIENTE.");
        BtnLimpiar(event);
    }

    @FXML
    private void BtnCancelar(ActionEvent event) {
        if (tblReservas.getSelectionModel().getSelectedItem() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Seleccioná una reserva para cancelar.");
            return;
        }
        if (txtMotivoCancel.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Motivo requerido", "Ingresá el motivo de cancelación.");
            return;
        }
        mostrarAlerta(Alert.AlertType.INFORMATION, "Cancelada", "La reserva fue cancelada.");
    }

    @FXML
    private void BtnBuscar(ActionEvent event) {
        // TODO: SELECT con filtros por reserve_status, start_date BETWEEN ? AND ?
    }

    @FXML
    private void BtnLimpiar(ActionEvent event) {
        cmbRecurso.setValue(null);
        cmbEstado.setValue(null);
        dpInicio.setValue(null);
        dpFin.setValue(null);
        txtHoraInicio.clear();
        txtHoraFin.clear();
        txtMotivo.clear();
        lblDisponibilidad.setText("");
        lblMensaje.setText("");
    }

    @FXML
    private void BtnRegresar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/cr/ac/una/reservauna/Views/administrator.fxml"));
            Stage stage = (Stage) btnRegresar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.showAndWait();
    }
}