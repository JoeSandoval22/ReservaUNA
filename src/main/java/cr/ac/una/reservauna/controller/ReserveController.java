package cr.ac.una.reservauna.controller;

import cr.ac.una.reservauna.dao.ReserveDao;
import cr.ac.una.reservauna.dao.ResourceAux;
import cr.ac.una.reservauna.model.Reserve;
import cr.ac.una.reservauna.model.ReserveStatus;
import cr.ac.una.reservauna.model.Resource;
import cr.ac.una.reservauna.model.User;
import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReserveController implements Initializable {

   
    
    @FXML 
    private TableColumn<Reserve, Integer> idColumn;
    @FXML
    private TableColumn<Reserve, User> userColumn;
    @FXML 
    private TableColumn<Reserve, Resource> resourceNameColumn;
    @FXML 
    private TableColumn<Reserve, LocalDateTime> startDateColumn;
    @FXML
    private TableColumn<Reserve, LocalDateTime> endDateColumn;
    @FXML
    private TableColumn<Reserve, String> reasonColumn;
    @FXML
    private TableColumn<Reserve, ReserveStatus> statusColumn;
    @FXML
    private TableColumn<Reserve, LocalDateTime> createAtColumn;
    @FXML
    private Button backButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button updateButton; 
    @FXML
    private TextField startHourText;
    @FXML
    private TextField endHourText;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField reasonText;
    @FXML
    private TableView<Reserve> reservesList;
    @FXML
    private ComboBox<Resource> resourceCombo;
    @FXML
    private Button addReserveButton;
    @FXML
    private Button searchButton;
    @FXML
    private TextField idText;
    @FXML
    private TextField userIdText;
    
//Queda pendiente de verificar
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            Resource resource = resourceCombo.getValue();
            int id = resource.getResourceId();
             
        }catch(Exception ex){
            System.out.println("Error r "+ex.getMessage());
        }
        idColumn.setCellValueFactory(new PropertyValueFactory<>("reserveId"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        resourceNameColumn.setCellValueFactory(new PropertyValueFactory<>("resource"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        createAtColumn.setCellValueFactory(new PropertyValueFactory<>("createAt"));
    }
    
    

    @FXML
    private void clearFields(ActionEvent event) {
        startHourText.clear();
        endHourText.clear();
        reasonText.clear();
        idText.clear();
        userIdText.clear();
    }
    
    @FXML
    private void searchReserve(ActionEvent event) {
        
    }

    @FXML
    private void cancelReserves(ActionEvent event) {
        String id = idText.getText().trim();
        if(id.isEmpty()){
            showAlert(Alert.AlertType.WARNING,"Espacios en blanco","No deje espacios en blanco.");
            return;
        }
        try{
            int newId = Integer.parseInt(id);
            ReserveDao reserveDao = new ReserveDao();
            boolean success = reserveDao.cancelReserve(newId);
            if(success){
                showAlert(Alert.AlertType.CONFIRMATION,"Reserva cancelada","La reserva ha sido cancelada existosamente.");
            }
        }catch(Exception ex){
            System.out.println("Error r: "+ex.getMessage());
        }
    }

    @FXML
    private void updateReserves(ActionEvent event) {
        String startHour = startHourText.getText().trim();
        String endHour = endHourText.getText().trim();
        String reason = reasonText.getText().trim();
        if(startDatePicker.getValue()==null || startHour.isEmpty() || endDatePicker.getValue()==null || endHour.isEmpty() || reason.isEmpty()){
            showAlert(Alert.AlertType.WARNING,"Espacios en blanco","No deje espacios en blanco ni opciones sin seleccionar.");
            return;
        }
        try{
            LocalDate start = startDatePicker.getValue();
            LocalDate end = endDatePicker.getValue();
            LocalTime sHour = LocalTime.parse(startHour);
            LocalTime eHour = LocalTime.parse(endHour);
            ReserveDao reserveDao = new ReserveDao();
            Reserve reserve = new Reserve(LocalDateTime.of(start, sHour),LocalDateTime.of(end, eHour),reason,LocalDateTime.now());
            if(reserveDao.updateReserve(reserve)){
                reservesList.getItems().add(reserve);
                showAlert(Alert.AlertType.CONFIRMATION,"Reserva actualizada","La reserva se actualizó exitosamente.");
            }
        }catch(DateTimeException ex){
            System.out.println("Error r: "+ex.getMessage());
        }
    }

    @FXML
    private void addReserve(ActionEvent event) {
        String userId = userIdText.getText().trim();
        String startHour = startHourText.getText().trim();
        String endHour = endHourText.getText().trim();
        String reason = reasonText.getText().trim();
        if(userId.isEmpty() || startDatePicker.getValue()==null || startHour.isEmpty() || endDatePicker.getValue()==null || endHour.isEmpty() || reason.isEmpty()){
            showAlert(Alert.AlertType.WARNING,"Espacios en blanco","No deje espacios en blanco ni opciones sin seleccionar.");
            return;
        }
        try{
            LocalDate start = startDatePicker.getValue();
            LocalDate end = endDatePicker.getValue();
            LocalTime sHour = LocalTime.parse(startHour);
            LocalTime eHour = LocalTime.parse(endHour);
            ReserveDao reserveDao = new ReserveDao();
            //Falta cambiar este constructor, necesito el combo box de recursos
            Reserve reserve = new Reserve(LocalDateTime.of(start, sHour),LocalDateTime.of(end, eHour),reason,LocalDateTime.now());
            if(reserveDao.insertReserve(reserve)){
                reservesList.getItems().add(reserve);
                showAlert(Alert.AlertType.CONFIRMATION,"Reserva agregada","La reserva ha sido agregada exitosamente.");
            }
        }catch(DateTimeException ex){
            System.out.println("Error r: "+ex.getMessage());
        }
    }

    @FXML
    private void backToAdminGUI(ActionEvent event) {
        
    }
    
    private void showAlert(Alert.AlertType type, String title, String message){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}