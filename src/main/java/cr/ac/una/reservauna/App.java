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
import cr.ac.una.reservauna.dao.PlaceDAO;
import cr.ac.una.reservauna.dao.UserDAO;
import cr.ac.una.reservauna.model.Equipment;
import cr.ac.una.reservauna.model.Place;

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
PlaceDAO placeDAO = new PlaceDAO();
int opcion;
do {
    System.out.println("\n===== RESERVAUNA - LUGARES =====");
    System.out.println("1. Registrar lugar");
    System.out.println("2. Listar lugares");
    System.out.println("3. Buscar lugar por ID");
    System.out.println("4. Actualizar lugar");
    System.out.println("5. Borrar lugar");
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
            System.out.print("Capacidad: ");
            int capacidad = Integer.parseInt(sc.nextLine());
            System.out.print("Ubicación: ");
            String ubicacion = sc.nextLine();
            System.out.print("Tipo (LABORATORIO/AULA): ");
            String tipo = sc.nextLine();
            Place place = new Place(nombre, descripcion, estado, capacidad, ubicacion, tipo);
            placeDAO.insertPlace(place);
        }
        case 2 -> {
            List<Place> places = placeDAO.getAllPlaces();
            places.forEach(p -> System.out.println(p.getResourceId() + " - " + p.getResourceName() + " - " + p.getLocation() + " - " + p.getType()));
        }
        case 3 -> {
            System.out.print("ID del lugar: ");
            int id = Integer.parseInt(sc.nextLine());
            Place p = placeDAO.findPlaceById(id);
            if (p != null) {
                System.out.println(p.getResourceId() + " - " + p.getResourceName() + " - " + p.getLocation() + " - " + p.getType());
            } else {
                System.out.println("Lugar no encontrado.");
            }
        }
        case 4 -> {
            System.out.print("ID del lugar a actualizar: ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Nuevo nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Nueva descripción: ");
            String descripcion = sc.nextLine();
            System.out.print("Nuevo estado (DISPONIBLE/NO DISPONIBLE): ");
            String estado = sc.nextLine();
            System.out.print("Nueva capacidad: ");
            int capacidad = Integer.parseInt(sc.nextLine());
            System.out.print("Nueva ubicación: ");
            String ubicacion = sc.nextLine();
            System.out.print("Nuevo tipo (LABORATORIO/AULA): ");
            String tipo = sc.nextLine();
            Place place = new Place(id, nombre, descripcion, estado, capacidad, ubicacion, tipo);
            placeDAO.updatePlace(place);
        }
        case 5 -> {
            System.out.print("ID del lugar a borrar: ");
            int id = Integer.parseInt(sc.nextLine());
            Place place = new Place(id, "", "", "", 0, "", "");
            placeDAO.deletePlace(place);
        }
        case 0 -> System.out.println("Saliendo...");
        default -> System.out.println("Opción inválida.");
    }
} while (opcion != 0);
        launch();
    }
}