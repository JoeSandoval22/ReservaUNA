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
    
}
