package cr.ac.una.reservauna.controller;

import cr.ac.una.reservauna.dao.EquipmentDAO;
import cr.ac.una.reservauna.dao.PlaceDAO;
import cr.ac.una.reservauna.dao.ReserveDao;
import cr.ac.una.reservauna.dao.ResourceAux;
import cr.ac.una.reservauna.dao.UserDAO;
import cr.ac.una.reservauna.model.Equipment;
import cr.ac.una.reservauna.model.Place;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
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
    private Button addReserveButton;
    @FXML
    private Button searchButton;
    @FXML
    private TextField idText;
    @FXML
    private TextField userIdText;
    @FXML
    private Label lblMensaje;
    @FXML
    private ToggleButton choiceButton;
    @FXML
    private ComboBox<Place> placeCombo;
    @FXML
    private ComboBox<Equipment> equipmentCombo;
    
    private EquipmentDAO equipment = new EquipmentDAO();
    private PlaceDAO place = new PlaceDAO();
    @FXML
    private TextField searchTextField;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        equipmentCombo.getItems().setAll(equipment.getAllEquipmentes());
        placeCombo.getItems().setAll(place.getAllPlaces());
        equipmentCombo.setVisible(true);
        placeCombo.setVisible(false);
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
        String idText = searchTextField.getText().trim();
        if(idText.isEmpty()){
            showAlert(Alert.AlertType.WARNING,"Espacios vacíos","No deje espacios vacíos.");
            return;
        }
        try{
            int id = Integer.parseInt(idText);
            ReserveDao reserveDao = new ReserveDao();
            Reserve reserve = reserveDao.findReserveById(id);
            reservesList.getItems().clear();
            if(reserve != null){
                reservesList.getItems().add(reserve);
            }
        }catch(Exception ex){
            System.out.println("Error r: "+ex.getMessage());
        }
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
                fillTables();
                showAlert(Alert.AlertType.CONFIRMATION,"Reserva cancelada","La reserva ha sido cancelada existosamente.");
            }
        }catch(Exception ex){
            System.out.println("Error r: "+ex.getMessage());
        }
    }

    @FXML
    private void updateReserves(ActionEvent event) {
        String reserveId = idText.getText().trim();
        String startHour = startHourText.getText().trim();
        String endHour = endHourText.getText().trim();
        String reason = reasonText.getText().trim();
        if(reserveId.isEmpty() || startDatePicker.getValue()==null || startHour.isEmpty() || endDatePicker.getValue()==null || endHour.isEmpty() || reason.isEmpty()){
            showAlert(Alert.AlertType.WARNING,"Espacios en blanco","No deje espacios en blanco ni opciones sin seleccionar.");
            return;
        }
        Resource resource = null;
        if(equipmentCombo.isVisible()){
            resource = equipmentCombo.getSelectionModel().getSelectedItem();
        }else{
            resource = placeCombo.getSelectionModel().getSelectedItem();
        }
        try{
            int id = Integer.parseInt(reserveId);
            LocalDate start = startDatePicker.getValue();
            LocalDate end = endDatePicker.getValue();
            LocalTime sHour = LocalTime.parse(startHour);
            LocalTime eHour = LocalTime.parse(endHour);
            ReserveDao reserveDao = new ReserveDao();
            Reserve reserve = new Reserve(id,resource,LocalDateTime.of(start, sHour),LocalDateTime.of(end, eHour),reason,LocalDateTime.now());
            if(reserveDao.updateReserve(reserve)){
                fillTables();
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
        Resource resource = null;
        if(equipmentCombo.isVisible()){
            resource = equipmentCombo.getValue();
        }else{
            resource = placeCombo.getValue();
        }
        try{
            int id = Integer.parseInt(userId);
            LocalDate start = startDatePicker.getValue();
            LocalDate end = endDatePicker.getValue();
            LocalTime sHour = LocalTime.parse(startHour);
            LocalTime eHour = LocalTime.parse(endHour);
            UserDAO userDao = new UserDAO();
            User user = userDao.findUserById(id); 
            ReserveDao reserveDao = new ReserveDao();
            Reserve reserve = new Reserve(user,resource,LocalDateTime.of(start, sHour),LocalDateTime.of(end, eHour),reason,LocalDateTime.now(),ReserveStatus.PENDIENTE);
            if(reserveDao.insertReserve(reserve)){
                reservesList.getItems().add(reserve);
                showAlert(Alert.AlertType.CONFIRMATION,"Reserva agregada","La reserva ha sido agregada exitosamente.");
            }
        }catch(DateTimeException | NumberFormatException ex){
            System.out.println("Error r: "+ex.getMessage());
            showAlert(Alert.AlertType.ERROR,"Datos inválidos","Asegúrese que el ID sea estrictamente numérico y que las horas tengan formato 00:00 - 23:59");
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

    @FXML
    private void getPlaceCombo(ActionEvent event) {
        if(choiceButton.isSelected()){
            placeCombo.setVisible(true);
            equipmentCombo.setVisible(false);
        }else{
            placeCombo.setVisible(false);
            equipmentCombo.setVisible(true);
        }
    }
    
    private void fillTables(){
        ReserveDao reserveDao = new ReserveDao();
        List<Reserve> reserve = reserveDao.getAllReserves();
        reservesList.getItems().setAll(reserve);
    }

    private void showReserves(ActionEvent event) {
        fillTables();
    }
}