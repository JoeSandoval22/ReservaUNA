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
import cr.ac.una.reservauna.dao.ReserveItemDAO;
import cr.ac.una.reservauna.dao.ResourceAux;
import cr.ac.una.reservauna.dao.UserDAO;
import cr.ac.una.reservauna.model.Equipment;
import cr.ac.una.reservauna.model.Place;
import cr.ac.una.reservauna.model.Reserve;
import cr.ac.una.reservauna.model.ReserveItem;
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
        // Asegúrate de tener estas instancias inicializadas antes del do-while
Scanner sc = new Scanner(System.in);
ReserveDao reserveDAO = new ReserveDao();
ReserveItemDAO itemDAO = new ReserveItemDAO(); // Tu clase DAO para items
UserDAO userDAO = new UserDAO();
ResourceAux resourceAux = new ResourceAux();

int opcion;
do {
    System.out.println("\n===== RESERVAUNA - GESTIÓN INTEGRAL =====");
    System.out.println("1. Registrar reserva (Padre)");
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
    System.out.println("--- GESTIÓN MULTI-RESERVA (ITEMS) ---");
    System.out.println("13. Agregar Item a Reserva");
    System.out.println("14. Listar todos los Items");
    System.out.println("15. Borrar Item de Reserva");
    System.out.println("0. Salir");
    System.out.print("Seleccione una opción: ");
    
    opcion = Integer.parseInt(sc.nextLine());

    switch (opcion) {
        case 1 -> {
            System.out.print("ID del usuario: ");
            int userId = Integer.parseInt(sc.nextLine());
            System.out.print("ID del recurso principal: ");
            int resourceId = Integer.parseInt(sc.nextLine());
            System.out.print("Fecha inicio (yyyy-MM-dd HH:mm): ");
            LocalDateTime startDate = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            System.out.print("Fecha fin (yyyy-MM-dd HH:mm): ");
            LocalDateTime endDate = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            System.out.print("Motivo: ");
            String reason = sc.nextLine();
            
            User user = userDAO.findUserById(userId);
            Resource res = resourceAux.findResourceById(resourceId);
            
            if (user != null && res != null) {
                Reserve reserve = new Reserve(user, res, startDate, endDate, reason, LocalDateTime.now(), ReserveStatus.PENDIENTE);
                reserveDAO.insertReserve(reserve);
            } else {
                System.out.println("Error: Usuario o Recurso no encontrado.");
            }
        }
        case 2 -> {
            List<Reserve> reserves = reserveDAO.getAllReserves();
            reserves.forEach(r -> System.out.println(r.getReserveId() + " - " + r.getUser().getUserName() + " - " + r.getStatus()));
        }
        case 3 -> {
            System.out.print("ID de la reserva: ");
            int id = Integer.parseInt(sc.nextLine());
            Reserve r = reserveDAO.findReserveById(id);
            if (r != null) System.out.println(r.getReserveId() + " - " + r.getUser().getUserName() + " - " + r.getStatus());
            else System.out.println("Reserva no encontrada.");
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
            reserveDAO.approveReserve(Integer.parseInt(sc.nextLine()));
        }
        case 7 -> {
            System.out.print("ID de la reserva a rechazar: ");
            reserveDAO.rejectReserve(Integer.parseInt(sc.nextLine()));
        }
        case 8 -> {
            System.out.print("ID de la reserva a cancelar: ");
            reserveDAO.cancelReserve(Integer.parseInt(sc.nextLine()));
        }
        case 9 -> {
            System.out.print("Estado (PENDIENTE/APROBADA/RECHAZADA/CANCELADA): ");
            ReserveStatus status = ReserveStatus.valueOf(sc.nextLine().toUpperCase());
            List<Reserve> reserves = reserveDAO.findByStatus(status);
            reserves.forEach(r -> System.out.println(r.getReserveId() + " - " + r.getUser().getUserName() + " - " + r.getStatus()));
        }
        case 10 -> {
            System.out.print("Fecha inicio (yyyy-MM-dd HH:mm): ");
            LocalDateTime sDate = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            System.out.print("Fecha fin (yyyy-MM-dd HH:mm): ");
            LocalDateTime eDate = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            reserveDAO.findByDate(sDate, eDate).forEach(r -> System.out.println(r.getReserveId() + " - " + r.getUser().getUserName()));
        }
        case 11 -> {
            System.out.print("ID del usuario: ");
            reserveDAO.findByUserId(Integer.parseInt(sc.nextLine())).forEach(r -> System.out.println(r.getReserveId() + " - " + r.getStatus()));
        }
        case 12 -> {
            System.out.print("ID del usuario: ");
            int uId = Integer.parseInt(sc.nextLine());
            System.out.print("ID de la reserva: ");
            int rId = Integer.parseInt(sc.nextLine());
            Reserve r = reserveDAO.findByUserId(uId, rId);
            if (r != null) System.out.println(r.getReserveId() + " - " + r.getStatus());
            else System.out.println("Reserva no encontrada.");
        }
        // --- NUEVA LÓGICA DE ITEMS ---
        case 13 -> {
            System.out.print("ID de la reserva padre (parent_reserve): ");
            int parentId = Integer.parseInt(sc.nextLine());
            Reserve parent = reserveDAO.findReserveById(parentId);
            
            if (parent != null) {
                System.out.print("ID del recurso: ");
                int resId = Integer.parseInt(sc.nextLine());
                Resource res = resourceAux.findResourceById(resId);
                System.out.print("Fecha inicio (yyyy-MM-dd HH:mm): ");
                LocalDateTime start = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                System.out.print("Fecha fin (yyyy-MM-dd HH:mm): ");
                LocalDateTime end = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                
                ReserveItem newItem = new ReserveItem(0, parent, res, start, end);
                if (itemDAO.insertReserveItem(newItem)) System.out.println("Item agregado correctamente.");
                else System.out.println("Error: No se pudo agregar el item (verificar límites o solapamientos).");
            } else {
                System.out.println("Error: Reserva padre no encontrada.");
            }
        }
        case 14 -> {
            List<ReserveItem> items = itemDAO.getAllReserveItems();
            if (items != null) items.forEach(i -> System.out.println("ID Item: " + i.getReserveItemId() + " | Reserva Padre: " + i.getParentReserve().getReserveId()));
        }
        case 15 -> {
            System.out.print("ID del item a borrar: ");
            int id = Integer.parseInt(sc.nextLine());
            ReserveItem item = new ReserveItem(id, null, null, null, null);
            if (itemDAO.deleteReserveItem(item)) System.out.println("Item eliminado.");
            else System.out.println("Error al borrar el item.");
        }
        case 0 -> System.out.println("Saliendo...");
        default -> System.out.println("Opción inválida.");
    }
} while (opcion != 0);        
        launch();
    }
}