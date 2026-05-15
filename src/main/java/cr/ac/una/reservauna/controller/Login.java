package cr.ac.una.reservauna.controller;

import java.io.IOException;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login implements Initializable {

    @FXML private TextField TextFCorreo;
    @FXML private PasswordField TextFContraseña;
    @FXML private Button btnIniciarSesion;
    @FXML private Label lblMensaje;
    @FXML
    private Button registerId;
    
    private SingUpController singUp = new SingUpController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void BtnIniciarSesion(ActionEvent event) {
        String correo = TextFCorreo.getText().trim();
        String contrasena = TextFContraseña.getText().trim();
        if (correo.isEmpty() || contrasena.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING,
                "Campos vacíos",
                "Ingresá el correo y la contraseña.");
            return;
        }
        String rol = "ADMINISTRADOR";
        try {
            String fxml = switch (rol) {
                case "ADMINISTRADOR" -> "/cr/ac/una/reservauna/Views/administrator.fxml";
                case "ENCARGADO"     -> "/cr/ac/una/reservauna/Views/manager.fxml";
                case "PROFESOR"      -> "/cr/ac/una/reservauna/Views/professor.fxml";
                case "ESTUDIANTE"    -> "/cr/ac/una/reservauna/Views/student.fxml";
                default -> null;
            };
            if (fxml == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Rol no reconocido.");
                return;
            }
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo cargar la pantalla: " + e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    

    @FXML
    private void switchToSingUp(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/cr/ac/una/reservauna/Views/sigUp.fxml"));
        javafx.scene.Node source = (javafx.scene.Node) event.getSource();
        Stage currentWindow = (Stage) source.getScene().getWindow();
        currentWindow.getScene().setRoot(root);
    }
}