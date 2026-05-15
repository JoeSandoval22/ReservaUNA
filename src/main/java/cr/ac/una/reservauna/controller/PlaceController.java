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

public class PlaceController implements Initializable {

    @FXML private TableView tblEspacios;
    @FXML private TableColumn colId;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colCapacidad;
    @FXML private TableColumn colUbicacion;
    @FXML private TableColumn colTipo;
    @FXML private TableColumn colEstado;
    @FXML private ComboBox cmbFiltroTipo;
    @FXML private ComboBox cmbFiltroEstado;
    @FXML private ComboBox cmbTipo;
    @FXML private ComboBox cmbEstado;
    @FXML private TextField txtBuscar;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtCapacidad;
    @FXML private TextField txtUbicacion;
    @FXML private Label lblMensaje;
    @FXML private Button btnGuardar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnRegresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbFiltroTipo.getItems().addAll("Todos","AULA","LABORATORIO");
        cmbFiltroEstado.getItems().addAll("Todos","DISPONIBLE","NO DISPONIBLE");
        cmbTipo.getItems().addAll("AULA","LABORATORIO");
        cmbEstado.getItems().addAll("DISPONIBLE","NO DISPONIBLE");
    }

    @FXML
    private void BtnGuardar(ActionEvent event) {
        if (txtNombre.getText().isEmpty() || txtDescripcion.getText().isEmpty()
                || txtCapacidad.getText().isEmpty() || txtUbicacion.getText().isEmpty()
                || cmbTipo.getValue() == null || cmbEstado.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Completá todos los campos.");
            return;
        }
        mostrarAlerta(Alert.AlertType.INFORMATION, "Guardado", "Espacio registrado correctamente.");
        BtnLimpiar(event);
    }

    @FXML
    private void BtnEliminar(ActionEvent event) {
        if (tblEspacios.getSelectionModel().getSelectedItem() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Seleccioná un espacio.");
            return;
        }
        mostrarAlerta(Alert.AlertType.INFORMATION, "Eliminado", "Espacio marcado como no disponible.");
    }

    @FXML
    private void BtnBuscar(ActionEvent event) {
        // TODO: SELECT con filtros
    }

    @FXML
    private void BtnLimpiar(ActionEvent event) {
        txtNombre.clear();
        txtDescripcion.clear();
        txtCapacidad.clear();
        txtUbicacion.clear();
        cmbTipo.setValue(null);
        cmbEstado.setValue(null);
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