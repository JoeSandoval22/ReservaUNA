package cr.ac.una.reservauna;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import cr.ac.una.reservauna.conexion.Conexion;
import cr.ac.una.reservauna.dao.EquipmentDAO;
import cr.ac.una.reservauna.dao.UserDAO;
import cr.ac.una.reservauna.model.Equipment;

import cr.ac.una.reservauna.model.Role;
import cr.ac.una.reservauna.model.User;
import java.util.List;
import java.util.Scanner;
/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
EquipmentDAO equipmentDAO = new EquipmentDAO();
int opcion;
do {
    System.out.println("\n===== RESERVAUNA - EQUIPOS =====");
    System.out.println("1. Registrar equipo");
    System.out.println("2. Listar equipos");
    System.out.println("3. Buscar equipo por ID");
    System.out.println("4. Actualizar equipo");
    System.out.println("5. Borrar equipo");
    System.out.println("0. Salir");
    System.out.print("Seleccione una opción: ");
    opcion = Integer.parseInt(sc.nextLine());
    switch (opcion) {
        case 1 -> {
            System.out.print("Nombre del recurso: ");
            String nombre = sc.nextLine();
            System.out.print("Descripción: ");
            String descripcion = sc.nextLine();
            System.out.print("Estado (DISPONIBLE/NO DISPONIBLE): ");
            String estado = sc.nextLine();
            System.out.print("Marca: ");
            String marca = sc.nextLine();
            System.out.print("Modelo: ");
            String modelo = sc.nextLine();
            System.out.print("Serie: ");
            String serie = sc.nextLine();
            Equipment equipment = new Equipment(nombre, descripcion, estado, marca, modelo, serie);
            equipmentDAO.insertEquipment(equipment);
        }
        case 2 -> {
            List<Equipment> equipments = equipmentDAO.getAllEquipmentes();
            equipments.forEach(e -> System.out.println(e.getResourceId() + " - " + e.getResourceName() + " - " + e.getBrand() + " - " + e.getModel()));
        }
        case 3 -> {
            System.out.print("ID del equipo: ");
            int id = Integer.parseInt(sc.nextLine());
            Equipment e = equipmentDAO.findEquipmentById(id);
            if (e != null) {
                System.out.println(e.getResourceId() + " - " + e.getResourceName() + " - " + e.getBrand() + " - " + e.getModel());
            } else {
                System.out.println("Equipo no encontrado.");
            }
        }
        case 4 -> {
            System.out.print("ID del equipo a actualizar: ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Nuevo nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Nueva descripción: ");
            String descripcion = sc.nextLine();
            System.out.print("Nuevo estado (DISPONIBLE/NO DISPONIBLE): ");
            String estado = sc.nextLine();
            System.out.print("Nueva marca: ");
            String marca = sc.nextLine();
            System.out.print("Nuevo modelo: ");
            String modelo = sc.nextLine();
            System.out.print("Nueva serie: ");
            String serie = sc.nextLine();
            Equipment equipment = new Equipment(id, nombre, descripcion, estado, marca, modelo, serie);
            equipmentDAO.updateEquipment(equipment);
        }
        case 5 -> {
            System.out.print("ID del equipo a borrar: ");
            int id = Integer.parseInt(sc.nextLine());
            Equipment equipment = new Equipment(id, "", "", "", "", "", "");
            equipmentDAO.deleteEquipment(equipment);
        }
        case 0 -> System.out.println("Saliendo...");
        default -> System.out.println("Opción inválida.");
    }
} while (opcion != 0);
        launch();
    }
}