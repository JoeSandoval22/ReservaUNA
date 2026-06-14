package cr.ac.una.reservauna.controller;

import cr.ac.una.reservauna.dao.EquipmentDAO;
import cr.ac.una.reservauna.dao.PlaceDAO;
import cr.ac.una.reservauna.dao.ReserveDao;
import cr.ac.una.reservauna.dao.ReserveItemDAO;
import cr.ac.una.reservauna.model.Equipment;
import cr.ac.una.reservauna.model.Place;
import cr.ac.una.reservauna.model.Reserve;
import cr.ac.una.reservauna.model.ReserveItem;
import cr.ac.una.reservauna.model.Resource;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;

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

    // Adds a new reserve item
    @FXML
private void addReserveItems(ActionEvent event) {
    String startHour = startHourText.getText().trim();
    String endHour = endHourText.getText().trim();
    String parentId = itemIdText.getText().trim();

    if (parentId.isEmpty() || startDatePicker.getValue() == null || startHour.isEmpty()
            || endDatePickr.getValue() == null || endHour.isEmpty()) {
        showAlert(Alert.AlertType.WARNING, "Empty fields", "Do not leave fields empty or options unselected.");
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
            showAlert(Alert.AlertType.ERROR, "Not found", "No reserve found with ID: " + id);
            return;
        }

        ReserveItem item = new ReserveItem(parentReserve, resource, LocalDateTime.of(start, sHour), LocalDateTime.of(end, eHour));

        if (reserveItemDAO.insertReserveItem(item)) {
            itemsList.getItems().add(item);
            showAlert(Alert.AlertType.CONFIRMATION, "Item added", "The reserve item was added successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed", "Could not add the reserve item. Check limits or overlaps.");
        }
    } catch (DateTimeException | NumberFormatException ex) {
        System.out.println("Error: " + ex.getMessage());
        showAlert(Alert.AlertType.ERROR, "Invalid data", "Make sure the hours follow the format 00:00 - 23:59.");
    }
}

    // Toggles between Place and Equipment combo
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

    // Goes back to the previous screen
    @FXML
    private void back(ActionEvent event) {
    }

    // Searches a reserve item by its ID
    @FXML
    private void searchReserveItem(ActionEvent event) {
        String idInput = idText.getText().trim();

        if (idInput.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Empty field", "Do not leave the ID field empty.");
            return;
        }

        try {
            int id = Integer.parseInt(idInput);
            ReserveItem found = reserveItemDAO.findReserveItemById(id);
            itemsList.getItems().clear();

            if (found != null) {
                itemsList.getItems().add(found);
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Not found", "No reserve item found with ID: " + id);
            }
        } catch (NumberFormatException ex) {
            System.out.println("Error: " + ex.getMessage());
            showAlert(Alert.AlertType.ERROR, "Invalid data", "The ID must be a valid number.");
        }
    }

    // Clears all input fields
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

    // Updates an existing reserve item
    @FXML
    private void updateItems(ActionEvent event) {
        String reserveItemId = itemIdText.getText().trim();
        String startHour = startHourText.getText().trim();
        String endHour = endHourText.getText().trim();

        if (reserveItemId.isEmpty() || startDatePicker.getValue() == null || startHour.isEmpty()
                || endDatePickr.getValue() == null || endHour.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Empty fields", "Do not leave fields empty or options unselected.");
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
            LocalDate start = startDatePicker.getValue();
            LocalDate end = endDatePickr.getValue();
            LocalTime sHour = LocalTime.parse(startHour);
            LocalTime eHour = LocalTime.parse(endHour);

            ReserveItem item = reserveItemDAO.findReserveItemById(id);
            ReserveItem updatedItem = new ReserveItem(id, item.getParentReserve(), resource,
                    LocalDateTime.of(start, sHour), LocalDateTime.of(end, eHour));

            if (reserveItemDAO.updateReserveItem(updatedItem)) {
                fillTables();
                showAlert(Alert.AlertType.CONFIRMATION, "Item updated", "The reserve item was updated successfully.");
            }
        } catch (DateTimeException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    @FXML
    private void deleteReserveItem(ActionEvent event) {
        String id = itemIdText.getText().trim();
        if (id.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Empty field", "Do not leave the ID field empty.");
            return;
        }
        try {
            int newId = Integer.parseInt(id);
            ReserveItem item = reserveItemDAO.findReserveItemById(newId);
            if (reserveItemDAO.deleteReserveItem(item)) {
                fillTables();
                showAlert(Alert.AlertType.CONFIRMATION, "Item deleted", "The reserve item was deleted successfully.");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    // Refreshes the table without appending rows
    private void fillTables() {
        List<ReserveItem> items = reserveItemDAO.getAllReserveItems();
        itemsList.getItems().setAll(items);
    }
}