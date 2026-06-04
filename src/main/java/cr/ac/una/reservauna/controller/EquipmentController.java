package cr.ac.una.reservauna.controller;

import cr.ac.una.reservauna.dao.EquipmentDAO;
import cr.ac.una.reservauna.model.Equipment;
import cr.ac.una.reservauna.model.ResourceState;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EquipmentController implements Initializable {

    @FXML 
    private TableView<Equipment> equipmentsList;
    @FXML 
    private TableColumn<Equipment, Integer> idColumn;
    @FXML 
    private TableColumn<Equipment, String> nameColumn;
    @FXML
    private TableColumn<Equipment, String> brandColumn;
    @FXML
    private TableColumn<Equipment, String> modelColumn;
    @FXML
    private TableColumn<Equipment, String> seriesColumn;
    @FXML
    private TableColumn<Equipment, ResourceState> stateColumn;
    @FXML
    private TableColumn<Equipment, String> descriptionColumn;
    @FXML
    private TextField nameText;
    @FXML
    private TextField brandText;
    @FXML
    private TextField modelText;
    @FXML
    private TextField seriesText;
    @FXML
    private ComboBox<ResourceState> stateCombo;
    @FXML
    private TextField updateIdText;
    @FXML
    private Button backButton;
    @FXML
    private Button addButton;
    @FXML
    private Button searchButton;
    @FXML
    private TextField findIdText;
    @FXML
    private Button showButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    @FXML
    private TextField descriptionText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stateCombo.getItems().setAll(ResourceState.values());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("resourceId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("resourceName"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        seriesColumn.setCellValueFactory(new PropertyValueFactory<>("series"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("resourceState"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory("description"));
        fillTable();
    }
    
    private void showAlert(Alert.AlertType type, String title, String message){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void fillTable() {
        EquipmentDAO eDao = new EquipmentDAO();
        List<Equipment> equipments = eDao.getAllEquipmentes();
        equipmentsList.getItems().setAll(equipments);
    }

    @FXML
    private void addEquipment(ActionEvent event) {
        String name = nameText.getText().trim();
        String brand = brandText.getText().trim();
        String model = modelText.getText().trim();
        String series = seriesText.getText().trim();
        String description = descriptionText.getText().trim();
        ResourceState state = stateCombo.getSelectionModel().getSelectedItem();
        if(name.isEmpty() || brand.isEmpty() || model.isEmpty() || series.isEmpty() || description.isEmpty() || state==null){
            showAlert(Alert.AlertType.WARNING,"Espacios incompletos","No deje espacios en blanco ni opciones por seleccionar.");
            return;
        }
        try{
            EquipmentDAO eDao = new EquipmentDAO();
            Equipment equipment = new Equipment(name,description,state,brand,model,series);
            if(eDao.insertEquipment(equipment)){
                equipmentsList.getItems().add(equipment);
                showAlert(Alert.AlertType.CONFIRMATION,"Equipo agregado","Equipo agregado exitosamente.");
            } 
        }catch(Exception ex){
            System.out.println("Error e:"+ex.getMessage());
        }
    }

    @FXML
    private void searchEquipmentById(ActionEvent event) {
        String equipmentId = findIdText.getText().trim();
        if(equipmentId.isEmpty()){
            showAlert(Alert.AlertType.WARNING,"Espacios en blanco","No deje espacios en blanco.");
            return;
        }
        try{
            int id = Integer.parseInt(equipmentId);
            EquipmentDAO eDao = new EquipmentDAO();
            Equipment equipment = eDao.findEquipmentById(id);
            equipmentsList.getItems().clear();
            if(equipment != null){
                equipmentsList.getItems().add(equipment);
            }
        }catch(Exception ex){
            System.out.println("Error e: "+ex.getMessage());
        }
    }

    @FXML
    private void showAllEquipments(ActionEvent event) {
        fillTable();
    }

    @FXML
    private void clearFields(ActionEvent event) {
        nameText.clear();
        brandText.clear();
        modelText.clear();
        seriesText.clear();
        updateIdText.clear();
        findIdText.clear();
        descriptionText.clear();
    }

    @FXML
    private void deleteEquipments(ActionEvent event) {
        Equipment selected = equipmentsList.getSelectionModel().getSelectedItem();
        if(selected == null){
            showAlert(Alert.AlertType.WARNING,"Opción sin seleccionar","No deje opciones sin seleccionar.");
            return;
        }
        try{
            EquipmentDAO eDao = new EquipmentDAO();
            boolean success = eDao.deleteEquipment(selected);
            if(success){
                showAlert(Alert.AlertType.CONFIRMATION,"Equipo eliminado","Equipo eliminado exitosamente.");
                equipmentsList.getItems().remove(selected);
            }
        }catch(Exception ex){
            System.out.println("Error e: "+ex.getMessage());
        }
    }

    @FXML
    private void updateEquipments(ActionEvent event) {
        String equipmentId = updateIdText.getText().trim();
        String name = nameText.getText().trim();
        String brand = brandText.getText().trim();
        String model = modelText.getText().trim();
        String series = seriesText.getText().trim();
        String description = descriptionText.getText().trim();
        ResourceState state = stateCombo.getSelectionModel().getSelectedItem();
        if(equipmentId.isEmpty() || name.isEmpty() || brand.isEmpty() || model.isEmpty() || series.isEmpty() || description.isEmpty() || state==null){
            showAlert(Alert.AlertType.WARNING,"Espacios incompletos","No deje espacios en blanco ni opciones por seleccionar.");
            return;
        }
        try{
            int id = Integer.parseInt(equipmentId);
            EquipmentDAO eDao = new EquipmentDAO();
            Equipment equipment = new Equipment(id,name,description,state,brand,model,series);
            if(eDao.updateEquipment(equipment)){
                fillTable();
                showAlert(Alert.AlertType.CONFIRMATION,"Equipo actualizado","Equipo actualizado exitosamente.");
            } 
        }catch(Exception ex){
            System.out.println("Error e: "+ex.getMessage());
        }
    } 
}