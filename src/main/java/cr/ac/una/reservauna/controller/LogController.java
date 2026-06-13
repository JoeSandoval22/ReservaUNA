package cr.ac.una.reservauna.controller;

import cr.ac.una.reservauna.dao.LogDAO;
import cr.ac.una.reservauna.model.Log;
import cr.ac.una.reservauna.model.Reserve;
import cr.ac.una.reservauna.model.User;
import java.net.URL;
import java.time.LocalDateTime;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class LogController implements Initializable {

    @FXML
    private TableView<Log> logsList;
    @FXML
    private TableColumn<Log, Integer> idColumn;
    @FXML
    private TableColumn<Log, Reserve> reserveIdColumn;
    @FXML
    private TableColumn<Log, User> userColumn;
    @FXML
    private TableColumn<Log, String> actionColumn;
    @FXML
    private TableColumn<Log, LocalDateTime> dateColumn;
    @FXML
    private TableColumn<Log, String> detailColumn;
    @FXML
    private Button backButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button showButton;
    @FXML
    private TextField idText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("logId"));
        reserveIdColumn.setCellValueFactory(new PropertyValueFactory<>("logReserve"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("userLog"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("actionPerformed"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        detailColumn.setCellValueFactory(new PropertyValueFactory<>("detail"));
    }

    @FXML
    private void backToAdminGUI(ActionEvent event) {
        
    }

    @FXML
    private void searchLog(ActionEvent event) {
        String id = idText.getText().trim();
        if(id.isEmpty()){
            showAlert(Alert.AlertType.WARNING,"Espacios vacíos","No deje espacios vacíos.");
            return;
        }
        try{
            int logId = Integer.parseInt(id);
            LogDAO logDao = new LogDAO();
            Log log = logDao.findLogById(logId);
            logsList.getItems().clear();
            if(log==null){
                showAlert(Alert.AlertType.INFORMATION,"Sin resultados","No existe una bitácora con esas credenciales.");
                return;
            }else{
                logsList.getItems().add(log);
            }
        }catch(Exception ex){
            System.out.println("Error l: "+ex.getMessage());
        }
    }

    @FXML
    private void showLogs(ActionEvent event) {
        fillTables();
    }
    
    private void fillTables(){
        LogDAO logDao = new LogDAO();
        List<Log> logs = logDao.getAllLogs();
        logsList.getItems().setAll(logs);
    }
    
    private void showAlert(Alert.AlertType type, String title, String message){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}