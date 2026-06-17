package cr.ac.una.reservauna.controller;

import cr.ac.una.reservauna.dao.EquipmentDAO;
import cr.ac.una.reservauna.dao.PlaceDAO;
import cr.ac.una.reservauna.dao.ReserveDao;
import cr.ac.una.reservauna.dao.ReserveItemDAO;
import cr.ac.una.reservauna.dao.UserDAO;
import cr.ac.una.reservauna.model.Equipment;
import cr.ac.una.reservauna.model.Place;
import cr.ac.una.reservauna.model.Reserve;
import cr.ac.una.reservauna.model.ReserveItem;
import cr.ac.una.reservauna.model.Resource;
import java.io.IOException;
import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ReserveItemController implements Initializable {

    @FXML private Label lblReservaPadre;
    @FXML private Label lblMensaje;

    @FXML private TableView<ReserveItem> itemsList;
    @FXML private TableColumn<ReserveItem, Integer> itemIdColumn;
    @FXML private TableColumn<ReserveItem, Resource> resourceColumn;
    @FXML private TableColumn<ReserveItem, LocalDateTime> startDateColumn;
    @FXML private TableColumn<ReserveItem, LocalDateTime> endDateColumn;

    @FXML private DatePicker startDatePicker;
    @FXML private TextField startHourText;
    @FXML private DatePicker endDatePickr;
    @FXML private TextField endHourText;

    @FXML private ComboBox<Place> placeCombo;
    @FXML private ComboBox<Equipment> equipmentCombo;

    @FXML private Button addButton;
    @FXML private ToggleButton choiceButton;
    @FXML private Button backButton;

    @FXML private TextField idText;
    @FXML private Button searchButton;
    @FXML private Button clearButton;
    @FXML private Button updateButton;
    @FXML private TextField itemIdText;

    private EquipmentDAO equipment = new EquipmentDAO();
    private PlaceDAO place = new PlaceDAO();
    private ReserveItemDAO reserveItemDAO = new ReserveItemDAO();
    @FXML
    private Button deleteButton;
    @FXML
    private TextField parentText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        equipmentCombo.getItems().setAll(equipment.getAllEquipmentes());
        placeCombo.getItems().setAll(place.getAllPlaces());
        equipmentCombo.setVisible(true);
        placeCombo.setVisible(false);

        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("reserveItemId"));
        resourceColumn.setCellValueFactory(new PropertyValueFactory<>("resourceItem"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDateItem"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDateItem"));
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }

    @FXML
private void addReserveItems(ActionEvent event) {
    String startHour = startHourText.getText().trim();
    String endHour = endHourText.getText().trim();
    String parentId = parentText.getText().trim();
    if (parentId.isEmpty() || startDatePicker.getValue() == null || startHour.isEmpty() || endDatePickr.getValue() == null || endHour.isEmpty()) {
        showAlert(Alert.AlertType.WARNING, "Espacios vacíos", "No deje espacios vacíos u opciones por seleccionar.");
        return;
    }
    Resource resource = null;
    if (equipmentCombo.isVisible()) {
        resource = equipmentCombo.getValue();
    } else {
        resource = placeCombo.getValue();
    }
    try {
        int id = Integer.parseInt(parentId);
        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePickr.getValue();
        LocalTime sHour = LocalTime.parse(startHour);
        LocalTime eHour = LocalTime.parse(endHour);
        ReserveDao reserveDao = new ReserveDao();
        Reserve parentReserve = reserveDao.findReserveById(id);
        if (parentReserve == null) {
            showAlert(Alert.AlertType.ERROR, "Sin resultados", "No existe una reserva con el ID: " + id);
            return;
        }
        ReserveItem item = new ReserveItem(parentReserve, resource, LocalDateTime.of(start, sHour), LocalDateTime.of(end, eHour));
        if (reserveItemDAO.insertReserveItem(item)) {
            itemsList.getItems().add(item);
            showAlert(Alert.AlertType.CONFIRMATION, "Ítem agregado", "El ítem fue agregado exitosamente.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Fallo", "No se pudo agregar el ítem.");
        }
    } catch (DateTimeException | NumberFormatException ex) {
        System.out.println("Error: " + ex.getMessage());
        showAlert(Alert.AlertType.ERROR, "Datos inválidos", "Asegúrese que las el ID sea estrictamente numérico y las horas tengan el formato 00:00 - 23:59.");
    }
}

   
    @FXML
    private void getPlaceCombo(ActionEvent event) {
        if (choiceButton.isSelected()) {
            placeCombo.setVisible(true);
            equipmentCombo.setVisible(false);
        } else {
            placeCombo.setVisible(false);
            equipmentCombo.setVisible(true);
        }
    }

   
    @FXML
    private void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/cr/ac/una/reservauna/Views/reserve.fxml"));
        javafx.scene.Node source = (javafx.scene.Node) event.getSource();
        Stage window = (Stage) source.getScene().getWindow();
        window.getScene().setRoot(root);
    }

    
    @FXML
    private void searchReserveItem(ActionEvent event) {
        String idInput = idText.getText().trim();

        if (idInput.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Espacios vacíos", "No deje espacios vacíos.");
            return;
        }

        try {
            int id = Integer.parseInt(idInput);
            ReserveItem found = reserveItemDAO.findReserveItemById(id);
            itemsList.getItems().clear();

            if (found != null) {
                itemsList.getItems().add(found);
            } else {
                showAlert(Alert.AlertType.INFORMATION, "No encontrado", "No existe un ítem de reserva con el ID: " + id);
            }
        } catch (NumberFormatException ex) {
            System.out.println("Error: " + ex.getMessage());
            showAlert(Alert.AlertType.ERROR, "Datos inválidos", "El ID debe ser estrictamente numérico.");
        }
    }

    
    @FXML
    private void clearFields(ActionEvent event) {
        idText.clear();
        itemIdText.clear();
        startDatePicker.setValue(null);
        startHourText.clear();
        endDatePickr.setValue(null);
        endHourText.clear();
        placeCombo.getSelectionModel().clearSelection();
        equipmentCombo.getSelectionModel().clearSelection();
    }

    
    @FXML
    private void updateItems(ActionEvent event) {
        String parentId = parentText.getText().trim();
        String reserveItemId = itemIdText.getText().trim();
        String startHour = startHourText.getText().trim();
        String endHour = endHourText.getText().trim();

        if (parentId.isEmpty() || reserveItemId.isEmpty() || startDatePicker.getValue() == null || startHour.isEmpty()
                || endDatePickr.getValue() == null || endHour.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Espacios vacíos", "No deje espacios vacíos u opciones por seleccionar.");
            return;
        }

        Resource resource = null;
        if (equipmentCombo.isVisible()) {
            resource = equipmentCombo.getSelectionModel().getSelectedItem();
        } else {
            resource = placeCombo.getSelectionModel().getSelectedItem();
        }

        try {
            int id = Integer.parseInt(reserveItemId);
            int parent = Integer.parseInt(parentId);
            LocalDate start = startDatePicker.getValue();
            LocalDate end = endDatePickr.getValue();
            LocalTime sHour = LocalTime.parse(startHour);
            LocalTime eHour = LocalTime.parse(endHour);

            ReserveDao reserveDao = new ReserveDao();
            Reserve item = reserveDao.findReserveById(parent);
            ReserveItem updatedItem = new ReserveItem(id, item, resource,LocalDateTime.of(start, sHour), LocalDateTime.of(end, eHour));
            if (reserveItemDAO.updateReserveItem(updatedItem)) {
                fillTables();
                showAlert(Alert.AlertType.CONFIRMATION, "Ítem actualizado", "El ítem de reserva fue actualizado exitosamente.");
            }
        } catch (DateTimeException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    @FXML
    private void deleteReserveItem(ActionEvent event) {
        String id = itemIdText.getText().trim();
        if (id.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Espacios vacíos", "No deje espacios vacíos.");
            return;
        }
        try {
            int newId = Integer.parseInt(id);
            ReserveItem item = reserveItemDAO.findReserveItemById(newId);
            if (reserveItemDAO.deleteReserveItem(item)) {
                fillTables();
                showAlert(Alert.AlertType.CONFIRMATION, "Ítem eliminado", "El ítem de reserva fue eliminado existosamente.");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    private void fillTables() {
        List<ReserveItem> items = reserveItemDAO.getAllReserveItems();
        itemsList.getItems().setAll(items);
    }
}