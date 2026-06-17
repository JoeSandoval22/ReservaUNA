package cr.ac.una.reservauna.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class AdministratorGUI implements Initializable {

    @FXML
    private MenuItem pResourceGestor;
    @FXML
    private MenuItem eResourceGestor;
    @FXML
    private MenuItem reservesGestor;
    @FXML
    private Button usersButton;
    @FXML
    private Button reservesButton;
    @FXML
    private Button placesGestor;
    @FXML
    private Button equipmentGestor;
    @FXML
    private Button reservesGestorButton;
    @FXML
    private Button logsButton;
    @FXML
    private Button logOutButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }

    private void cargarPantalla(String fxml, Button btn) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("Error al cargar: " + fxml + " — " + e.getMessage());
        }
    }

    @FXML
    private void switchToUsersHandle(ActionEvent event) {
        cargarPantalla("/cr/ac/una/reservauna/Views/usersHandle.fxml", usersButton);
    }

    @FXML
    private void switchToReserves(ActionEvent event) {
        cargarPantalla("/cr/ac/una/reservauna/Views/reserve.fxml", reservesButton);
    }

    @FXML
    private void switchToPlaces(ActionEvent event) {
        cargarPantalla("/cr/ac/una/reservauna/Views/place.fxml",placesGestor);
    }

    @FXML
    private void switchToEquipments(ActionEvent event) {
        cargarPantalla("/cr/ac/una/reservauna/Views/equipment.fxml",equipmentGestor);
    }

    @FXML
    private void switchToReservesGestor(ActionEvent event) {
        cargarPantalla("/cr/ac/una/reservauna/Views/reservesGestor.fxml",reservesGestorButton);
    }

    @FXML
    private void switchToLogsHandle(ActionEvent event) {
        cargarPantalla("/cr/ac/una/reservauna/Views/logs.fxml",logsButton);
    }

    @FXML
    private void switchToLogin(ActionEvent event) {
        cargarPantalla("/cr/ac/una/reservauna/Views/login.fxml",logOutButton);
    }
}