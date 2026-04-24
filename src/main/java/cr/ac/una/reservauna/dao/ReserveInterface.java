/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.una.reservauna.dao;

import cr.ac.una.reservauna.model.Reserve;
import cr.ac.una.reservauna.model.ReserveStatus;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author User
 */
public interface ReserveInterface {
    boolean insertReserve(Reserve reserve);
    boolean deleteReserve(Reserve reserve);
    boolean updateReserve(Reserve reserve);
    boolean isThereAnOverlap(int id, LocalDateTime startDate,LocalDateTime endDate);
    //Este método llama a una única reserva por medio de su ID
    Reserve findReserveById(int id);
    //Esta consulta es para todas las reservas con todos los datos de cada una
    List<Reserve> getAllReserves();
    //Consulta de carácter general con es status de las reservas
    List<Reserve>findByStatus(ReserveStatus status);
    //Busca de forma específica al usuario como a una de sus posibles reservas
    Reserve findByUserId(int userId, int reserveId);
    //Consulta de carácter general con las fechas de las reservas
    List<Reserve>findByDate(LocalDateTime starDate, LocalDateTime endDate);
    //Consulta para todas las reservas de un usario con el ID de reserva
    List<Reserve>findByUserId(int userId);
    boolean approveReserve(int reserveId);
    boolean rejectReserve(int reserveId);
    boolean cancelReserve(int reserveId);
}
