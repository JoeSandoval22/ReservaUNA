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

public class HandleStatusController implements Initializable {

    @FXML private TableView tblReservas;
    @FXML private TableColumn colId;
    @FXML private TableColumn colUsuario;
    @FXML private TableColumn colRecurso;
    @FXML private TableColumn colInicio;
    @FXML private TableColumn colFin;
    @FXML private TableColumn colMotivo;
    @FXML private TableColumn colEstado;
    @FXML private ComboBox cmbFiltroEstado;
    @FXML private DatePicker dpFecha;
    @FXML private TextField txtBuscarUsuario;
    @FXML private TextArea txtMotivo;
    @FXML private Label lblReservaSeleccionada;
    @FXML private Label lblMensaje;
    @FXML private Button btnAprobar;
    @FXML private Button btnRechazar;
    @FXML private Button btnRegresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbFiltroEstado.getItems().addAll("PENDIENTE","APROBADA","RECHAZADA","CANCELADA");
        cmbFiltroEstado.setValue("PENDIENTE");
        // TODO: SELECT r.*, u.user_name, res.resource_name FROM RESERVE r
        //       JOIN USERS_TABLE u ON r.user_id=u.user_id
        //       JOIN RESOURCES res ON r.resource_id=res.resource_id
        //       WHERE r.reserve_status='PENDIENTE'
    }

    @FXML
    private void BtnAprobar(ActionEvent event) {
        if (tblReservas.getSelectionModel().getSelectedItem() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Seleccioná una reserva.");
            return;
        }
        if (txtMotivo.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Motivo requerido", "Ingresá el motivo de aprobación.");
            return;
        }
        // TODO: UPDATE RESERVE SET reserve_status='APROBADA' WHERE reserve_id=?
        // TODO: INSERT INTO LOG_TABLE (reserve_id,user_id,log_date,detail,action)
        //       VALUES (?,?,CURRENT_TIMESTAMP,?,'APROBAR')
        mostrarAlerta(Alert.AlertType.INFORMATION, "Aprobada", "Reserva aprobada correctamente.");
        txtMotivo.clear();
    }

    @FXML
    private void BtnRechazar(ActionEvent event) {
        if (tblReservas.getSelectionModel().getSelectedItem() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Seleccioná una reserva.");
            return;
        }
        if (txtMotivo.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Motivo requerido", "Ingresá el motivo de rechazo.");
            return;
        }
        // TODO: UPDATE RESERVE SET reserve_status='RECHAZADA' WHERE reserve_id=?
        // TODO: INSERT INTO LOG_TABLE ... action='RECHAZAR'
        mostrarAlerta(Alert.AlertType.INFORMATION, "Rechazada", "Reserva rechazada.");
        txtMotivo.clear();
    }

    @FXML
    private void BtnBuscar(ActionEvent event) {
        // TODO: SELECT con filtro por reserve_status y fecha
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