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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ReserveItemController implements Initializable {

    @FXML private TableView tblItems;
    @FXML private TableColumn colItemId;
    @FXML private TableColumn colRecurso;
    @FXML private TableColumn colInicio;
    @FXML private TableColumn colFin;
    @FXML private ComboBox cmbRecurso;
    @FXML private DatePicker dpInicio;
    @FXML private DatePicker dpFin;
    @FXML private TextField txtHoraInicio;
    @FXML private TextField txtHoraFin;
    @FXML private Label lblReservaPadre;
    @FXML private Label lblMensaje;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnRegresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: cargar recursos disponibles en cmbRecurso
        //       SELECT resource_id, resource_name FROM RESOURCES
        //       WHERE resource_state='DISPONIBLE'
        // TODO: cargar items de la reserva padre en tblItems
        //       SELECT ri.*, r.resource_name FROM RESERVE_ITEM ri
        //       JOIN RESOURCES r ON ri.resource_id=r.resource_id
        //       WHERE ri.parent_reserve=?
    }

    @FXML
    private void BtnAgregar(ActionEvent event) {
        if (cmbRecurso.getValue() == null || dpInicio.getValue() == null
                || dpFin.getValue() == null || txtHoraInicio.getText().isEmpty()
                || txtHoraFin.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos",
                "Completá todos los campos del item.");
            return;
        }
        // TODO: INSERT INTO RESERVE_ITEM
        //       (parent_reserve, resource_id, start_date, end_date)
        //       VALUES (?, ?, ?, ?)
        mostrarAlerta(Alert.AlertType.INFORMATION, "Agregado",
            "Item agregado a la reserva.");
        limpiarFormItem();
    }

    @FXML
    private void BtnEliminar(ActionEvent event) {
        if (tblItems.getSelectionModel().getSelectedItem() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección",
                "Seleccioná un item de la tabla.");
            return;
        }
        // TODO: DELETE FROM RESERVE_ITEM WHERE reserve_item_id=?
        mostrarAlerta(Alert.AlertType.INFORMATION, "Eliminado",
            "Item eliminado de la reserva.");
    }

    @FXML
    private void BtnRegresar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(
                "/cr/ac/una/reservauna/Views/reserve.fxml"));
            Stage stage = (Stage) btnRegresar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void limpiarFormItem() {
        cmbRecurso.setValue(null);
        dpInicio.setValue(null);
        dpFin.setValue(null);
        txtHoraInicio.clear();
        txtHoraFin.clear();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.showAndWait();
    }
}