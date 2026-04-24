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
import cr.ac.una.reservauna.dao.ReserveDao;
import cr.ac.una.reservauna.dao.ResourceAux;
import cr.ac.una.reservauna.dao.UserDAO;
import cr.ac.una.reservauna.model.Equipment;
import cr.ac.una.reservauna.model.Place;
import cr.ac.una.reservauna.model.Reserve;
import cr.ac.una.reservauna.model.ReserveStatus;
import cr.ac.una.reservauna.model.Resource;

import cr.ac.una.reservauna.model.Role;
import cr.ac.una.reservauna.model.User;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
ReserveDao reserveDAO = new ReserveDao();
int opcion;
do {
    System.out.println("\n===== RESERVAUNA - RESERVAS =====");
    System.out.println("1. Registrar reserva");
    System.out.println("2. Listar reservas");
    System.out.println("3. Buscar reserva por ID");
    System.out.println("4. Actualizar reserva");
    System.out.println("5. Borrar reserva");
    System.out.println("6. Aprobar reserva");
    System.out.println("7. Rechazar reserva");
    System.out.println("8. Cancelar reserva");
    System.out.println("9. Buscar por estado");
    System.out.println("10. Buscar por fecha");
    System.out.println("11. Buscar por usuario");
    System.out.println("12. Buscar reserva específica de usuario");
    System.out.println("0. Salir");
    System.out.print("Seleccione una opción: ");
    opcion = Integer.parseInt(sc.nextLine());
    switch (opcion) {
        case 1 -> {
            System.out.print("ID del usuario: ");
            int userId = Integer.parseInt(sc.nextLine());
            System.out.print("ID del recurso: ");
            int resourceId = Integer.parseInt(sc.nextLine());
            System.out.print("Fecha inicio (yyyy-MM-dd HH:mm): ");
            LocalDateTime startDate = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            System.out.print("Fecha fin (yyyy-MM-dd HH:mm): ");
            LocalDateTime endDate = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            System.out.print("Motivo: ");
            String reason = sc.nextLine();
            User user = new UserDAO().findUserById(userId);
            Resource res = new ResourceAux().findResourceById(resourceId);
            Reserve reserve = new Reserve(user, res, startDate, endDate, reason, LocalDateTime.now(), ReserveStatus.PENDIENTE);
            reserveDAO.insertReserve(reserve);
        }
        case 2 -> {
            List<Reserve> reserves = reserveDAO.getAllReserves();
            reserves.forEach(r -> System.out.println(r.getReserveId() + " - " + r.getUser().getUserName() + " - " + r.getStatus()));
        }
        case 3 -> {
            System.out.print("ID de la reserva: ");
            int id = Integer.parseInt(sc.nextLine());
            Reserve r = reserveDAO.findReserveById(id);
            if (r != null) {
                System.out.println(r.getReserveId() + " - " + r.getUser().getUserName() + " - " + r.getStatus());
            } else {
                System.out.println("Reserva no encontrada.");
            }
        }
        case 4 -> {
            System.out.print("ID de la reserva a actualizar: ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Nueva fecha inicio (yyyy-MM-dd HH:mm): ");
            LocalDateTime startDate = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            System.out.print("Nueva fecha fin (yyyy-MM-dd HH:mm): ");
            LocalDateTime endDate = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            System.out.print("Nuevo motivo: ");
            String reason = sc.nextLine();
            reserveDAO.updateReserve(new Reserve(id, null, null, startDate, endDate, reason, LocalDateTime.now(), ReserveStatus.PENDIENTE));
        }
        case 5 -> {
            System.out.print("ID de la reserva a borrar: ");
            int id = Integer.parseInt(sc.nextLine());
            reserveDAO.deleteReserve(new Reserve(id, null, null, null, null, null, null, null));
        }
        case 6 -> {
            System.out.print("ID de la reserva a aprobar: ");
            int id = Integer.parseInt(sc.nextLine());
            reserveDAO.approveReserve(id);
        }
        case 7 -> {
            System.out.print("ID de la reserva a rechazar: ");
            int id = Integer.parseInt(sc.nextLine());
            reserveDAO.rejectReserve(id);
        }
        case 8 -> {
            System.out.print("ID de la reserva a cancelar: ");
            int id = Integer.parseInt(sc.nextLine());
            reserveDAO.cancelReserve(id);
        }
        case 9 -> {
            System.out.println("Estado (PENDIENTE/APROBADA/RECHAZADA/CANCELADA): ");
            ReserveStatus status = ReserveStatus.valueOf(sc.nextLine());
            List<Reserve> reserves = reserveDAO.findByStatus(status);
            reserves.forEach(r -> System.out.println(r.getReserveId() + " - " + r.getUser().getUserName() + " - " + r.getStatus()));
        }
        case 10 -> {
            System.out.print("Fecha inicio (yyyy-MM-dd HH:mm): ");
            LocalDateTime startDate = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            System.out.print("Fecha fin (yyyy-MM-dd HH:mm): ");
            LocalDateTime endDate = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            List<Reserve> reserves = reserveDAO.findByDate(startDate, endDate);
            reserves.forEach(r -> System.out.println(r.getReserveId() + " - " + r.getUser().getUserName() + " - " + r.getStatus()));
        }
        case 11 -> {
            System.out.print("ID del usuario: ");
            int userId = Integer.parseInt(sc.nextLine());
            List<Reserve> reserves = reserveDAO.findByUserId(userId);
            reserves.forEach(r -> System.out.println(r.getReserveId() + " - " + r.getUser().getUserName() + " - " + r.getStatus()));
        }
        case 12 -> {
            System.out.print("ID del usuario: ");
            int userId = Integer.parseInt(sc.nextLine());
            System.out.print("ID de la reserva: ");
            int reserveId = Integer.parseInt(sc.nextLine());
            Reserve r = reserveDAO.findByUserId(userId, reserveId);
            if (r != null) {
                System.out.println(r.getReserveId() + " - " + r.getUser().getUserName() + " - " + r.getStatus());
            } else {
                System.out.println("Reserva no encontrada.");
            }
        }
        case 0 -> System.out.println("Saliendo...");
        default -> System.out.println("Opción inválida.");
        }
        } while (opcion != 0);        
        launch();
    }
}