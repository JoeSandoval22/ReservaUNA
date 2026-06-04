package cr.ac.una.reservauna.controller;

import cr.ac.una.reservauna.dao.PlaceDAO;
import cr.ac.una.reservauna.model.Place;
import cr.ac.una.reservauna.model.PlaceType;
import cr.ac.una.reservauna.model.ResourceState;
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

public class PlaceController implements Initializable {

    private TableView tblEspacios;
    private ComboBox cmbFiltroTipo;
    private ComboBox cmbFiltroEstado;
    private ComboBox cmbTipo;
    private ComboBox cmbEstado;
    private TextField txtNombre;
    private TextField txtDescripcion;
    private TextField txtCapacidad;
    private TextField txtUbicacion;
    @FXML private Label lblMensaje;
    private Button btnRegresar;
    
    @FXML 
    private TableView<Place> placesList;
    @FXML 
    private TableColumn<Place, Integer> idColumn;
    @FXML 
    private TableColumn<Place, String> nameColumn;
    @FXML
    private TableColumn<Place, Integer> capacityColumn;
    @FXML
    private TableColumn<Place, String> locationColumn;
    @FXML
    private TableColumn<Place, PlaceType> typeColumn;
    @FXML
    private TableColumn<Place, ResourceState> stateColumn;
    @FXML
    private TableColumn<Place, String> descriptionColumn;
    @FXML
    private TextField nameText;
    @FXML
    private TextField capacityText;
    @FXML
    private TextField locationText;
    @FXML
    private ComboBox<PlaceType> typeCombo;
    @FXML
    private ComboBox<ResourceState> stateCombo;
    @FXML
    private TextField descriptionText;
    @FXML
    private TextField idUpdateText;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button backButton;
    @FXML
    private Button addButton;
    @FXML
    private Button searchButton;
    @FXML
    private TextField idtext;
    @FXML
    private Button showButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeCombo.getItems().setAll(PlaceType.values());
        stateCombo.getItems().setAll(ResourceState.values());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("resourceId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("resourceName"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("resourceState"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    @FXML
    private void BtnRegresar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/cr/ac/una/reservauna/Views/administrator.fxml"));
            Stage stage = (Stage) btnRegresar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void showAlert(Alert.AlertType type, String title, String message){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void fillTable(){
        PlaceDAO placeDao = new PlaceDAO();
        List<Place> places = placeDao.getAllPlaces();
        placesList.getItems().setAll(places);
    }

    @FXML
    private void updatePlaces(ActionEvent event) {
        String id = idUpdateText.getText().trim();
        String name = nameText.getText().trim();
        String capacity = capacityText.getText().trim();
        String location = locationText.getText().trim();
        String description = descriptionText.getText().trim();
        PlaceType type = typeCombo.getValue();
        ResourceState state = stateCombo.getValue();
        if(id.isEmpty() || name.isEmpty() || capacity.isEmpty() || location.isEmpty() || type==null || state==null || description.isEmpty()){
            showAlert(Alert.AlertType.WARNING,"Espacios en blanco","No deje espacios en blaco ni opciones sin seleccionar.");
            return;
        }
        try{
            int placeId = Integer.parseInt(id);
            int capacities = Integer.parseInt(capacity);
            PlaceDAO placeDao = new PlaceDAO();
            Place place = new Place(placeId,name,description,state,capacities,location,type);
            if(placeDao.updatePlace(place)){
                showAlert(Alert.AlertType.CONFIRMATION,"Lugar actualizado","Lugar actualizado existosamente.");
                fillTable();
            }
        }catch(Exception ex){
            System.out.println("Error p: "+ex.getMessage());
        }
    }

    @FXML
    private void deletePlaces(ActionEvent event) {
        Place selected = placesList.getSelectionModel().getSelectedItem();
        if(selected==null){
            showAlert(Alert.AlertType.WARNING,"Opción sin seleccionar","No deje opciones sin seleccinar.");
            return;
        }
        try{
            PlaceDAO placeDao = new PlaceDAO();
            boolean success = placeDao.deletePlace(selected);
            if(success){
                showAlert(Alert.AlertType.CONFIRMATION,"Lugar eliminado","Lugar eliminado exitosamente.");
                placesList.getItems().remove(selected);
            }
        }catch(Exception ex){
            System.out.println("Error p: "+ex.getMessage());
        }  
    }

    @FXML
    private void clearFields(ActionEvent event) {
        nameText.clear();
        capacityText.clear();
        locationText.clear();
        descriptionText.clear();
        idUpdateText.clear();
        idtext.clear();
    }

    @FXML
    private void addPlace(ActionEvent event) {
        String name = nameText.getText().trim();
        String capacity = capacityText.getText().trim();
        String location = locationText.getText().trim();
        String description = descriptionText.getText().trim();
        PlaceType type = typeCombo.getValue();
        ResourceState state = stateCombo.getValue();
        if(name.isEmpty() || capacity.isEmpty() || location.isEmpty() || type==null || state==null || description.isEmpty()){
            showAlert(Alert.AlertType.WARNING,"Espacios en blanco","No deje espacios en blaco ni opciones sin seleccionar.");
            return;
        }
        try{
            int capacities = Integer.parseInt(capacity);
            PlaceDAO placeDao = new PlaceDAO();
            Place place = new Place(name,description,state,capacities,location,type);
            if(placeDao.insertPlace(place)){
                showAlert(Alert.AlertType.CONFIRMATION,"Lugar agregado","Lugar agregado existosamente.");
                placesList.getItems().add(place);
            }
        }catch(Exception ex){
            System.out.println("Error p: "+ex.getMessage());
        }
    }

    @FXML
    private void searchPlace(ActionEvent event) {
        String id = idtext.getText().trim();
        if(id.isEmpty()){
            showAlert(Alert.AlertType.WARNING,"Espacios en blanco","No deje espacios en blanco.");
            return;
        }
        try{
            int placeId = Integer.parseInt(id);
            PlaceDAO placeDao = new PlaceDAO();
            Place place = placeDao.findPlaceById(placeId);
            placesList.getItems().clear();
            if(place != null){
                placesList.getItems().add(place);
            }
        }catch(Exception ex){
            System.out.println("Error p: "+ex.getMessage());
        }
    }

    @FXML
    private void showPlaces(ActionEvent event) {
        fillTable();
    }
}