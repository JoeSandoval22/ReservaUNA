package cr.ac.una.reservauna.controller;

import cr.ac.una.reservauna.dao.UserDAO;
import cr.ac.una.reservauna.model.User;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SingUpController implements Initializable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;
    private ComboBox cmbRol;
    private ComboBox cmbEstado;
    @FXML private Label lblMensaje;
    @FXML private Button btnGuardar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnRegresar;
    
    private UserDAO userDao = new UserDAO();
    @FXML
    private TextField roleField;
    @FXML
    private TextField userState;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbRol.getItems().addAll(
            "4 · ESTUDIANTE — máx 2",
            "3 · PROFESOR — máx 3",
            "2 · ENCARGADO — máx 4",
            "1 · ADMINISTRADOR — máx 5"
        );
        cmbEstado.getItems().addAll("ACTIVO","INACTIVO");
        cmbEstado.setValue("ACTIVO");
    }

    @FXML
    public void BtnGuardar(ActionEvent event) {
        if (txtNombre.getText().isEmpty() || txtCorreo.getText().isEmpty()
                || txtContrasena.getText().isEmpty()
                || cmbRol.getValue() == null || cmbEstado.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos",
                "Completá todos los campos obligatorios.");
            return;
        }
        //User user = new User(txtNombre.getText(),txtCorreo.getText(),txtContrasena.getText(),cmbRol.getValue(),cmbEstado.getValue());
        if (!txtCorreo.getText().contains("@")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Correo inválido",
                "El correo debe tener formato usuario@una.ac.cr");
            return;
        }
        //userDao.insertUser(user);
        String rolSeleccionado = cmbRol.getValue().toString();
        int roleId = Integer.parseInt(rolSeleccionado.substring(0, 1));
        mostrarAlerta(Alert.AlertType.INFORMATION, "Registrado",
            "Usuario registrado correctamente.");
        BtnLimpiar(event);
    }

    @FXML
    private void BtnLimpiar(ActionEvent event) {
        txtNombre.clear();
        txtCorreo.clear();
        txtContrasena.clear();
        cmbRol.setValue(null);
        cmbEstado.setValue("ACTIVO");
        lblMensaje.setText("");
    }

    @FXML
    private void BtnRegresar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(
                "/cr/ac/una/reservauna/Views/usersHandle.fxml"));
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