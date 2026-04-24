/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.una.reservauna.dao;

import cr.ac.una.reservauna.model.ReserveItem;
import cr.ac.una.reservauna.model.ReserveStatus;
import cr.ac.una.reservauna.model.Role;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author User
 */
public interface ReserveItemInterface {
    boolean insertReserveItem(ReserveItem item);
    boolean deleteReserveItem(ReserveItem item);
    boolean updateReserveItem(ReserveItem item);
    //Esta consulta es específica gracias al uso del ID
    ReserveItem findReserveItemById(int id);
    //Esta consulta es para todas las reservas con todos los datos de cada una
    List<ReserveItem> getAllReserveItems();
    boolean reserveLimitPerUser(int userId,Role role);
    boolean isThereAnOverlap(int id, LocalDateTime startDate,LocalDateTime endDate);
    //Este método llama a una única reserva por medio de su ID
    ReserveItem findReserveById(int id);
    //Consulta de carácter general sin ID de reserva
    List<ReserveItem>findByStatus(ReserveStatus status);
    //Consulta de carácter general sin ID
    List<ReserveItem>findByDate(LocalDateTime startDate, LocalDateTime endDate);
    //Consulta de carácter específico con el ID de usuario y de reserva
    List<ReserveItem>findByUserId(int userId, int reserveId);
    //Consulta para todas las reservas de un usario con el ID de reserva
    List<ReserveItem>findByUserId(int userId);
    boolean approveReserve(int reserveId);
    boolean rejectReserve(int reserveId);
    boolean cancelReserve(int reserveId);
}
