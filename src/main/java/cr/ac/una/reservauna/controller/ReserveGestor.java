/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.reservauna.controller;

import cr.ac.una.reservauna.dao.ReserveDao;
import cr.ac.una.reservauna.model.Reserve;
import cr.ac.una.reservauna.model.ReserveStatus;
import cr.ac.una.reservauna.model.Resource;
import cr.ac.una.reservauna.model.User;
import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author andre_3e6xvb2
 */
public class ReserveGestor implements Initializable{

    @FXML
    private TableView<Reserve> reservesList;
    @FXML
    private TableColumn<Reserve, Integer> idColumn;
    @FXML
    private TableColumn<Reserve, User> userColumn;
    @FXML
    private TableColumn<Reserve, Resource> resourceColumn;
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
    private MenuButton searchMenu;
    @FXML
    private MenuItem showAllButton;
    @FXML
    private MenuItem reserveIdbutton;
    @FXML
    private MenuItem statusButton;
    @FXML
    private MenuItem userAndReserveId;
    @FXML
    private MenuItem userIdButton;
    @FXML
    private MenuItem dateButton;
    @FXML
    private TextField reserveIdText;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private TextField startHourText;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField endHourText;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField userIdText;
    @FXML
    private Button approveButton;
    @FXML
    private Button rejectButton;
    @FXML
    private Button backButton;
    @FXML
    private ComboBox<ReserveStatus> statusCombo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        statusCombo.getItems().setAll(ReserveStatus.values());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("reserveId"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        resourceColumn.setCellValueFactory(new PropertyValueFactory<>("resource"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        createAtColumn.setCellValueFactory(new PropertyValueFactory<>("createAt"));
        showAllButton.setOnAction(event ->fillTables());
        reserveIdbutton.setOnAction(event ->getByReserveId());
        statusButton.setOnAction(event ->getByStatus());
        userAndReserveId.setOnAction(event ->getByUserAndReserveId());
        userIdButton.setOnAction(event ->getByUserId());
        dateButton.setOnAction(event ->getByDate());
    }
    
    
    
    @FXML
    private void choiceSearchType(ActionEvent event) {
        
    }
    
    private void getByDate(){
        String startHour = startHourText.getText().trim();
        String endHour = endHourText.getText().trim();
        if(startDatePicker.getValue()==null || endDatePicker.getValue()==null || startHour.isEmpty() || endHour.isEmpty()){
            showAlert(Alert.AlertType.WARNING,"Espacios incompletos","No deje espacios en blanco ni opciones sin seleccionar.");
            return;
        }
        try{
            LocalDate start = startDatePicker.getValue();
            LocalDate end = endDatePicker.getValue();
            LocalTime sHour = LocalTime.parse(startHour);
            LocalTime eHour = LocalTime.parse(endHour);
            ReserveDao reserveDao = new ReserveDao();
            List<Reserve> reserve = reserveDao.findByDate(LocalDateTime.of(start, sHour), LocalDateTime.of(end, eHour));
            reservesList.getItems().setAll(reserve);
            if(reserve.isEmpty()){
                showAlert(Alert.AlertType.INFORMATION,"Sin resultados","No hay reservas creadas para ese período de tiempo.");
                return;
            }
        }catch(DateTimeException ex){
            System.out.println("Error r: "+ex.getMessage());
            showAlert(Alert.AlertType.ERROR,"Formato inválido","Asegúrese que las horas tengan el formato 00:00 - 23:59");
        }
    }
    
    private void getByUserAndReserveId(){
        String userId = userIdText.getText().trim();
        String reserveId = reserveIdText.getText().trim();
        if(userId.isEmpty() || reserveId.isEmpty()){
            showAlert(Alert.AlertType.WARNING,"Espacios incompletos","No deje espacios incompletos.");
            return;
        }
        try{
            int uID = Integer.parseInt(userId);
            int rID = Integer.parseInt(reserveId);
            ReserveDao reserveDao = new ReserveDao();
            Reserve reserve = reserveDao.findByUserId(uID, rID);
            reservesList.getItems().clear();
            if(reserve!=null){
                reservesList.getItems().add(reserve);
            }
        }catch(NumberFormatException ex){
            System.out.println("Error r:"+ex.getMessage());
            showAlert(Alert.AlertType.ERROR,"Formato inválido","El ID debe ser estrictamente numérico.");
        }
    }
    
    private void getByUserId(){
        String userId = userIdText.getText().trim();
        if(userId.isEmpty()){
            showAlert(Alert.AlertType.WARNING,"Espacios incompletos","No deje espacios sin completar.");
            return;
        }
        try{
            int id = Integer.parseInt(userId);
            ReserveDao reserveDao = new ReserveDao();
            List<Reserve> reserve = reserveDao.findByUserId(id);
            reservesList.getItems().setAll(reserve);
            if(reserve.isEmpty()){
                showAlert(Alert.AlertType.INFORMATION,"Sin resultados","No existen reservas a nombre de este ID de usuario.");
                return;
            }
        }catch(Exception ex){
            System.out.println("Error r: "+ex.getMessage());
        } 
    }
    
    private void getByStatus(){
        if(statusCombo.getValue()==null){
            showAlert(Alert.AlertType.WARNING,"Opción inválida","Seleccione una opción primero antes de hacer la búsqueda.");
            return;
        }
        try{
            ReserveDao reserveDao = new ReserveDao();
            List<Reserve> reserve = reserveDao.findByStatus(statusCombo.getValue());
            reservesList.getItems().clear();
            if(!reserve.isEmpty()){
                reservesList.getItems().setAll(reserve);
                
            }else{
                showAlert(Alert.AlertType.INFORMATION,"Sin resultados","No existen reservas con ese status.");
                return;
            }
        }catch(Exception ex){
            System.out.println("Error r: "+ex.getMessage());
        }
    }
    
    private void getByReserveId(){
        String reserveId = reserveIdText.getText().trim();
        if(reserveId.isEmpty()){
            showAlert(Alert.AlertType.WARNING,"Espacios incompletos","No deje espacios sin completar.");
            return;
        }
        try{
            int id = Integer.parseInt(reserveId);
            ReserveDao reserveDao = new ReserveDao();
            Reserve reserve = reserveDao.findReserveById(id);
            reservesList.getItems().clear();
            if(reserve!=null){
                reservesList.getItems().add(reserve);
            }
        }catch(Exception ex){
            System.out.println("Error r: "+ex.getMessage());
        }
    }

    private void fillTables(){
        ReserveDao reserveDao = new ReserveDao();
        List<Reserve> reserve = reserveDao.getAllReserves();
        reservesList.getItems().setAll(reserve);
    }
    
    @FXML
    private void approveReserves(ActionEvent event) {
        String idText = reserveIdText.getText().trim();
        if(idText.isEmpty()){
            showAlert(Alert.AlertType.WARNING,"Espacios incompletos","No deje espacios sin completar.");
            return;
        }
        try{
            int id = Integer.parseInt(idText);
            ReserveDao reserveDao = new ReserveDao();
            boolean success = reserveDao.approveReserve(id);
            if(success){
                fillTables();
                showAlert(Alert.AlertType.CONFIRMATION,"Reserva aprobada","La reserva ha sido aprobada existosamente.");
            }
        }catch(Exception ex){
            System.out.println("Error r: "+ex.getMessage());
        }
            
    }

    @FXML
    private void rejectReserves(ActionEvent event) {
        String idText = reserveIdText.getText().trim();
        if(idText.isEmpty()){
            showAlert(Alert.AlertType.WARNING,"Espacios incompletos","No deje espacios sin completar.");
            return;
        }
        try{
            int id = Integer.parseInt(idText);
            ReserveDao reserveDao = new ReserveDao();
            boolean success = reserveDao.rejectReserve(id);
            if(success){
                fillTables();
                showAlert(Alert.AlertType.CONFIRMATION,"Reserva rechazada","La reserva ha sido rechazada existosamente.");
            }
        }catch(Exception ex){
            System.out.println("Error r: "+ex.getMessage());
        }
    }

    @FXML
    private void deleteReserves(ActionEvent event) {
        Reserve selected = reservesList.getSelectionModel().getSelectedItem();
        if(selected==null){
            showAlert(Alert.AlertType.WARNING,"Opción inválida","Por favor seleccione una reserva para eliminar.");
            return;
        }
        try{
            ReserveDao reserveDao = new ReserveDao();
            boolean success = reserveDao.deleteReserve(selected);
            if(success){
                showAlert(Alert.AlertType.CONFIRMATION,"Reserva eliminada","La reserva ha sido eliminada existosamente.");
                reservesList.getItems().remove(selected);
            }
        }catch(Exception ex){
            System.out.println("Error r: "+ex.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }  
}
