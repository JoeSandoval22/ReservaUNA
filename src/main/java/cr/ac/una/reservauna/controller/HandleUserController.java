package cr.ac.una.reservauna.controller;

import cr.ac.una.reservauna.dao.UserDAO;
import cr.ac.una.reservauna.model.Role;
import cr.ac.una.reservauna.model.User;
import cr.ac.una.reservauna.model.UserState;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class HandleUserController implements Initializable {

    
    @FXML
    private TextField idText;
    @FXML
    private Button searchButton;
    @FXML
    private Button backButton;
    @FXML
    private TableView<User> usersList;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, Role> roleColumn;
    @FXML
    private TableColumn<User, Integer> userMaxReserves;
    @FXML
    private TableColumn<UserState, String> stateColumn;
    @FXML
    private TextField nameText;
    @FXML
    private TextField emailText;
    @FXML
    private ComboBox<Role> roleCombo;
    @FXML
    private ComboBox<UserState> userStateCombo;
    @FXML
    private Button updateButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button showButton;
    @FXML
    private TextField idUpdateText;
    @FXML
    private TextField passwordText;
    @FXML
    private Label lblMensaje;
    @FXML
    private Button deleteButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("userMail"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("userRole"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("userState"));
        roleCombo.getItems().setAll(Role.values());
        userStateCombo.getItems().setAll(UserState.values());
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }

    @FXML
    private void searchUser(ActionEvent event) {
        String id = idText.getText().trim();
        if(id.isEmpty()){
            showAlert(Alert.AlertType.WARNING,"Espacios vacíos","No deje espacios vacíos.");
            return;
        }
        try{
            int userId = Integer.parseInt(id);
            UserDAO userDao = new UserDAO();
            User user = userDao.findUserById(userId);
            usersList.getItems().clear();
            if(user!=null){
                usersList.getItems().add(user);
            }else{
                showAlert(Alert.AlertType.INFORMATION,"Sin resultados","No existe un usuario con ese ID");
                return;
            }
        }catch(Exception ex){
            showAlert(Alert.AlertType.ERROR,"Formato inválido","Asegúrese que el ID sea estrictamente numérico.");
            System.out.println("Error: "+ex.getMessage());
        }
    }

    @FXML
    private void backTo(ActionEvent event) throws IOException {
        try {
            Scene scene = ((javafx.scene.Node) event.getSource()).getScene();
            NavegationController nav = new NavegationController(scene);
            nav.goBackTo();
        } catch (Exception e) {
            System.out.println("Error al regresar al menú de Administrador: " + e.getMessage());
        }
    }

    @FXML
    private void updateUsers(ActionEvent event) {
        String userId = idUpdateText.getText().trim();
        String name = nameText.getText().trim();
        String email = emailText.getText().trim();
        String password = passwordText.getText().trim();
        Role role = roleCombo.getValue();
        UserState state = userStateCombo.getValue();
        if(userId.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty() || role==null || state==null){
            showAlert(Alert.AlertType.WARNING,"Espacios vacíos","No deje espacios vacíos u opciones sin seleccionar");
            return;
        }
        try{
            int id = Integer.parseInt(userId);
            UserDAO userDao = new UserDAO();
            User user = new User(id,name,email,role,state,password);
            if(userDao.updateUser(user)){
                fillTables();
                showAlert(Alert.AlertType.CONFIRMATION,"Usuario agregado","Usuario agregado existosamente.");
            }
        }catch(NumberFormatException ex){
            showAlert(Alert.AlertType.ERROR,"Formato inválido","Asegúrese que el ID sea estrictamente numérico.");
            System.out.println("Error: "+ex.getMessage());
        }
    }

    @FXML
    private void clearFields(ActionEvent event) {
        idText.clear();
        idUpdateText.clear();
        nameText.clear();
        emailText.clear();
        passwordText.clear();
    }

    @FXML
    private void showUsers(ActionEvent event) {
        fillTables();
    }
    
    private void fillTables(){
        UserDAO userDao = new UserDAO();
        List<User> users = userDao.getAllUsers();
        usersList.getItems().setAll(users);
    }

    @FXML
    private void deleteUsers(ActionEvent event) {
        User selected = usersList.getSelectionModel().getSelectedItem();
        if(selected==null){
            showAlert(Alert.AlertType.WARNING,"Opción inválida","Seleccione una opción para eliminar.");
            return;
        }
        try{
            UserDAO userDao = new UserDAO();
            boolean success = userDao.deleteUser(selected);
            if(success){
                usersList.getItems().remove(selected);
                showAlert(Alert.AlertType.CONFIRMATION,"Usuario eliminado","Usuario eliminado existosamente.");
            } 
        }catch(Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }
}