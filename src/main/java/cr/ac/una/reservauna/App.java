package cr.ac.una.reservauna;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import cr.ac.una.reservauna.conexion.Conexion;
import cr.ac.una.reservauna.dao.UserDAO;

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
UserDAO userDAO = new UserDAO();
int opcion;
do {
    System.out.println("\n===== RESERVAUNA =====");
    System.out.println("1. Registrar usuario");
    System.out.println("2. Listar usuarios");
    System.out.println("3. Buscar usuario por ID");
    System.out.println("4. Actualizar usuario");
    System.out.println("5. Borrar usuario");
    System.out.println("0. Salir");
    System.out.print("Seleccione una opción: ");
    opcion = Integer.parseInt(sc.nextLine());
    switch (opcion) {
        case 1 -> {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Correo: ");
            String correo = sc.nextLine();
            System.out.print("Rol (1=ADMINISTRADOR, 2=ENCARGADO, 3=PROFESOR, 4=ESTUDIANTE): ");
            int rolId = Integer.parseInt(sc.nextLine());
            User user = new User(nombre, correo, Role.values()[rolId - 1], "ACTIVO");
            userDAO.insertUser(user);
        }
        case 2 -> {
            List<User> users = userDAO.getAllUsers();
            users.forEach(u -> System.out.println(u.getUserId() + " - " + u.getUserName() + " - " + u.getUserMail() + " - " + u.getUserState()));
        }
        case 3 -> {
            System.out.print("ID del usuario: ");
            int id = Integer.parseInt(sc.nextLine());
            User u = userDAO.findUserById(id);
            if (u != null) {
                System.out.println(u.getUserId() + " - " + u.getUserName() + " - " + u.getUserMail());
            } else {
                System.out.println("Usuario no encontrado.");
            }
        }
        case 4 -> {
            System.out.print("ID del usuario a actualizar: ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Nuevo nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Nuevo correo: ");
            String correo = sc.nextLine();
            System.out.print("Nuevo rol (1=ADMINISTRADOR, 2=ENCARGADO, 3=PROFESOR, 4=ESTUDIANTE): ");
            int rolId = Integer.parseInt(sc.nextLine());
            System.out.print("Nuevo estado (ACTIVO/INACTIVO): ");
            String estado = sc.nextLine();
            User user = new User(id, nombre, correo, Role.values()[rolId - 1], estado);
            userDAO.updateUser(user);
        }
        case 5 -> {
            System.out.print("ID del usuario a borrar: ");
            int id = Integer.parseInt(sc.nextLine());
            User user = new User(id, "", "", Role.values()[0], "ACTIVO");
            userDAO.deleteUser(user);
        }
        case 0 -> System.out.println("Saliendo...");
        default -> System.out.println("Opción inválida.");
    }
        } while (opcion != 0);
        launch();
    }
}