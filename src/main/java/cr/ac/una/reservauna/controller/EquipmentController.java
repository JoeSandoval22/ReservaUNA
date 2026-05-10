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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EquipmentController implements Initializable {

    @FXML private TableView tblEquipos;
    @FXML private TableColumn colId;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colMarca;
    @FXML private TableColumn colModelo;
    @FXML private TableColumn colSerie;
    @FXML private TableColumn colEstado;
    @FXML private ComboBox cmbFiltroEstado;
    @FXML private ComboBox cmbEstado;
    @FXML private TextField txtBuscar;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtMarca;
    @FXML private TextField txtModelo;
    @FXML private TextField txtSerie;
    @FXML private Label lblMensaje;
    @FXML private Button btnGuardar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnRegresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbFiltroEstado.getItems().addAll("Todos","DISPONIBLE","NO DISPONIBLE");
        cmbEstado.getItems().addAll("DISPONIBLE","NO DISPONIBLE");
        // TODO: SELECT r.resource_id, r.resource_name, r.resource_state,
        //              e.brand, e.resource_model, e.series
        //       FROM RESOURCES r JOIN EQUIPMENT e ON r.resource_id=e.resource_id
    }

    @FXML
    private void BtnGuardar(ActionEvent event) {
        if (txtNombre.getText().isEmpty() || txtDescripcion.getText().isEmpty()
                || txtMarca.getText().isEmpty() || txtModelo.getText().isEmpty()
                || txtSerie.getText().isEmpty() || cmbEstado.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos",
                "Completá todos los campos obligatorios.");
            return;
        }
        // TODO: INSERT INTO RESOURCES (resource_name, res_description, resource_state)
        //       VALUES (?, ?, ?) → obtener resource_id generado con getGeneratedKeys()
        // TODO: INSERT INTO EQUIPMENT (brand, resource_model, series, resource_id)
        //       VALUES (?, ?, ?, ?)
        mostrarAlerta(Alert.AlertType.INFORMATION, "Guardado",
            "Equipo registrado correctamente.");
        BtnLimpiar(event);
    }

    @FXML
    private void BtnEliminar(ActionEvent event) {
        if (tblEquipos.getSelectionModel().getSelectedItem() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección",
                "Seleccioná un equipo de la tabla.");
            return;
        }
        // TODO: UPDATE RESOURCES SET resource_state='NO DISPONIBLE'
        //       WHERE resource_id=?
        mostrarAlerta(Alert.AlertType.INFORMATION, "Eliminado",
            "Equipo marcado como NO DISPONIBLE.");
    }

    @FXML
    private void BtnBuscar(ActionEvent event) {
        // TODO: SELECT con filtros por resource_name, brand, resource_state
    }

    @FXML
    private void BtnLimpiar(ActionEvent event) {
        txtNombre.clear();
        txtDescripcion.clear();
        txtMarca.clear();
        txtModelo.clear();
        txtSerie.clear();
        cmbEstado.setValue(null);
        lblMensaje.setText("");
    }

    @FXML
    private void BtnRegresar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(
                "/cr/ac/una/reservauna/Views/administrator.fxml"));
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