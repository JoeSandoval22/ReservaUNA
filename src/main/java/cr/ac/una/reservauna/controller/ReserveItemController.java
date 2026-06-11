package cr.ac.una.reservauna.controller;

import cr.ac.una.reservauna.model.Equipment;
import cr.ac.una.reservauna.model.Place;
import cr.ac.una.reservauna.model.ReserveItem;
import cr.ac.una.reservauna.model.Resource;
import java.net.URL;
import java.time.LocalDateTime;
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
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class ReserveItemController implements Initializable {

    private TableView tblItems;
    private ComboBox cmbRecurso;
    private DatePicker dpInicio;
    private DatePicker dpFin;
    private TextField txtHoraInicio;
    private TextField txtHoraFin;
    @FXML private Label lblReservaPadre;
    @FXML private Label lblMensaje;
    private Button btnRegresar;
    @FXML
    private TableView<ReserveItem> itemsList;
    @FXML
    private TableColumn<ReserveItem, Integer> itemIdColumn;
    @FXML
    private TableColumn<ReserveItem, Resource> resourceColumn;
    @FXML
    private TableColumn<ReserveItem, LocalDateTime> startDateColumn;
    @FXML
    private TableColumn<ReserveItem, LocalDateTime> endDateColumn;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private TextField startHourText;
    @FXML
    private DatePicker endDatePickr;
    @FXML
    private TextField endHourText;
    @FXML
    private ComboBox<Place> placeCombo;
    @FXML
    private ComboBox<Equipment> equipmentCombo;
    @FXML
    private Button addButton;
    @FXML
    private ToggleButton choiceButton;
    @FXML
    private Button backButton;
    @FXML
    private TextField idText;
    @FXML
    private Button searchButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button updateButton;
    @FXML
    private TextField itemIdText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
           
    }
    
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }

    //Agrega más ítems de reserva
    @FXML
    private void addReserveItems(ActionEvent event) {
    }
    //Permite llamar al ComboBox de lugares y viceversa
    @FXML
    private void getPlaceCombo(ActionEvent event) {
    }
    
    @FXML
    private void back(ActionEvent event) {
    }
    //Busca los ítems por su id
    @FXML
    private void searchReserveItem(ActionEvent event) {
    }
    //Esta función limpia los campos
    @FXML
    private void clearFields(ActionEvent event) {
    }
    //Actualiza los ítems
    @FXML
    private void updateItems(ActionEvent event) {
    }
    /*
    Úsela para llenar una fila, no es necesario usar un botón con esta, es simplemente que para cuado se 
        se actualizan los ítems de modo que la animación de cambio se vea y no añada debajo.
    */
    private void fillTables(){
        
    }
    
}