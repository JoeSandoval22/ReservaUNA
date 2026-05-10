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

public class HandleUserController implements Initializable {

    @FXML private TableView tblUsuarios;
    @FXML private TableColumn colId;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colCorreo;
    @FXML private TableColumn colRol;
    @FXML private TableColumn colMax;
    @FXML private TableColumn colEstado;
    @FXML private ComboBox cmbFiltroRol;
    @FXML private ComboBox cmbFiltroEstado;
    @FXML private ComboBox cmbRol;
    @FXML private ComboBox cmbEstado;
    @FXML private TextField txtBuscar;
    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private Label lblMensaje;
    @FXML private Button btnGuardar;
    @FXML private Button btnInactivar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnNuevo;
    @FXML private Button btnRegresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbFiltroRol.getItems().addAll(
            "Todos","ADMINISTRADOR","ENCARGADO","PROFESOR","ESTUDIANTE");
        cmbFiltroEstado.getItems().addAll("Todos","ACTIVO","INACTIVO");
        cmbRol.getItems().addAll(
            "1 · ADMINISTRADOR","2 · ENCARGADO","3 · PROFESOR","4 · ESTUDIANTE");
        cmbEstado.getItems().addAll("ACTIVO","INACTIVO");
        // TODO: SELECT u.user_id, u.user_name, u.user_mail, u.user_state,
        //              r.role_name, r.max_reserves
        //       FROM USERS_TABLE u JOIN USER_ROLE r ON u.role_id=r.role_id
    }

    @FXML
    private void BtnGuardar(ActionEvent event) {
        if (txtNombre.getText().isEmpty() || txtCorreo.getText().isEmpty()
                || cmbRol.getValue() == null || cmbEstado.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos",
                "Completá todos los campos obligatorios.");
            return;
        }
        // TODO si es nuevo:
        //   INSERT INTO USERS_TABLE (user_name, user_mail, role_id, user_state)
        //   VALUES (?, ?, ?, ?)
        // TODO si es edición:
        //   UPDATE USERS_TABLE SET user_name=?, user_mail=?, role_id=?, user_state=?
        //   WHERE user_id=?
        mostrarAlerta(Alert.AlertType.INFORMATION, "Guardado",
            "Usuario guardado correctamente.");
        BtnLimpiar(event);
    }

    @FXML
    private void BtnInactivar(ActionEvent event) {
        if (tblUsuarios.getSelectionModel().getSelectedItem() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección",
                "Seleccioná un usuario de la tabla.");
            return;
        }
        // TODO: UPDATE USERS_TABLE SET user_state='INACTIVO' WHERE user_id=?
        mostrarAlerta(Alert.AlertType.INFORMATION, "Inactivado",
            "Usuario marcado como INACTIVO.");
    }

    @FXML
    private void BtnNuevo(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(
                "/cr/ac/una/reservauna/Views/sigUp.fxml"));
            Stage stage = (Stage) btnNuevo.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    private void BtnBuscar(ActionEvent event) {
        // TODO: SELECT con filtros por user_name, user_mail, role_id, user_state
    }

    @FXML
    private void BtnLimpiar(ActionEvent event) {
        txtNombre.clear();
        txtCorreo.clear();
        cmbRol.setValue(null);
        cmbEstado.setValue(null);
        lblMensaje.setText("");
        tblUsuarios.getSelectionModel().clearSelection();
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