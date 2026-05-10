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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogController implements Initializable {

    @FXML private TableView tblLogs;
    @FXML private TableColumn colLogId;
    @FXML private TableColumn colReservaId;
    @FXML private TableColumn colUsuario;
    @FXML private TableColumn colAccion;
    @FXML private TableColumn colFecha;
    @FXML private TableColumn colDetalle;
    @FXML private ComboBox cmbFiltroAccion;
    @FXML private DatePicker dpInicio;
    @FXML private DatePicker dpFin;
    @FXML private TextField txtBuscarUsuario;
    @FXML private Label lblTotal;
    @FXML private Button btnFiltrar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnRegresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbFiltroAccion.getItems().addAll(
            "Todas","CREAR","ACTUALIZAR","ELIMINAR","APROBAR","RECHAZAR","CANCELAR"
        );
        cmbFiltroAccion.setValue("Todas");
        // TODO: SELECT l.*, u.user_name FROM LOG_TABLE l
        //       JOIN USERS_TABLE u ON l.user_id=u.user_id
        //       ORDER BY l.log_date DESC
    }

    @FXML
    private void BtnFiltrar(ActionEvent event) {
        // TODO: SELECT con filtros por action, user_id, log_date BETWEEN ? AND ?
    }

    @FXML
    private void BtnLimpiar(ActionEvent event) {
        cmbFiltroAccion.setValue("Todas");
        txtBuscarUsuario.clear();
        dpInicio.setValue(null);
        dpFin.setValue(null);
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
}