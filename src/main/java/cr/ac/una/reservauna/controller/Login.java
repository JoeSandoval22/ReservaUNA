package cr.ac.una.reservauna.controller;

import cr.ac.una.reservauna.dao.UserDAO;
import cr.ac.una.reservauna.model.Role;
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

    @FXML 
    private Label lblMensaje;
    @FXML
    private TextField emailText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }


    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    

    @FXML
    private void switchToSingUp(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/cr/ac/una/reservauna/Views/sigUp.fxml"));
        javafx.scene.Node source = (javafx.scene.Node) event.getSource();
        Stage currentWindow = (Stage) source.getScene().getWindow();
        currentWindow.getScene().setRoot(root);
    }

    @FXML
    private void doLogin(ActionEvent event) { 
        String email = emailText.getText().trim();
        String password = passwordText.getText().trim();
        if(email.isEmpty() || password.isEmpty()){
            showAlert(Alert.AlertType.WARNING,"Espacios vacíos","No deje espacios sin completar.");
            return;
        }
        try{
            UserDAO userDao = new UserDAO();
            Role user = userDao.findUser(email, password);
            if(user==null){
                showAlert(Alert.AlertType.ERROR,"Usuario inexistente","No existe un usuario con esas credenciales.");
                return;
            }
            String fxmlFile = switch(user){
                case ADMINISTRADOR -> "/cr/ac/una/reservauna/Views/administrator.fxml";
                case ENCARGADO ->  "/cr/ac/una/reservauna/Views/manager.fxml";
                case PROFESOR -> "/cr/ac/una/reservauna/Views/professor.fxml";
                case ESTUDIANTE -> "/cr/ac/una/reservauna/Views/student.fxml";
                default -> null;
            };
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();          
        }catch(Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }
}