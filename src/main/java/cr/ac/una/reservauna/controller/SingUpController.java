package cr.ac.una.reservauna.controller;

import cr.ac.una.reservauna.dao.UserDAO;
import cr.ac.una.reservauna.model.Role;
import cr.ac.una.reservauna.model.User;
import cr.ac.una.reservauna.model.UserState;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SingUpController implements Initializable {
  
    @FXML
    private TextField nameText;
    @FXML
    private TextField emailText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private ComboBox<Role> roleCombo;
    @FXML
    private ComboBox<UserState> userStateCombo;
    @FXML
    private Button backButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button saveButton;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roleCombo.getItems().setAll(Role.values());
        userStateCombo.getItems().setAll(UserState.values());
    }

    @FXML
    private void backToLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/cr/ac/una/reservauna/Views/login.fxml"));
        javafx.scene.Node source = (javafx.scene.Node) event.getSource();
        Stage currentWindow = (Stage) source.getScene().getWindow();
        currentWindow.getScene().setRoot(root);
    }

    @FXML
    private void clearFields(ActionEvent event) {
        nameText.clear();
        emailText.clear();
        passwordText.clear();
        roleCombo.getItems().clear();
        userStateCombo.getItems().clear();
    }

    @FXML
    private void addUser(ActionEvent event) {
        String name = nameText.getText().trim();
        String email = emailText.getText().trim();
        String password = passwordText.getText().trim();
        Role role = roleCombo.getSelectionModel().getSelectedItem();
        UserState userState = userStateCombo.getSelectionModel().getSelectedItem();
        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || role==null || userState==null){
            showAlert(Alert.AlertType.WARNING,"Espacios incompletos","No deje espacios vacíos ni opciones sin seleccionar.");
            return;
        }
        try{
            UserDAO userDao = new UserDAO();
            User user = new User(name,email,role,userState,password);
            if(userDao.insertUser(user)){
                showAlert(Alert.AlertType.CONFIRMATION,"Éxito","Usuario registrado correctamente.");
            }
        } catch(Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }
    
    private void showAlert(Alert.AlertType type, String title, String menssage){
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(menssage);
        a.showAndWait();
    }
}