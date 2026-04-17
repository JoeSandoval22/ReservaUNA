/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.una.reservauna.dao;

import cr.ac.una.reservauna.model.Reserve;
import java.time.LocalDateTime;
import javafx.scene.control.ListView;

/**
 *
 * @author User
 */
public interface ReserveInterface {
    boolean insertReserve(Reserve reserve);
    boolean deleteReserve(Reserve reserve);
    boolean upgradeRserve(Reserve reserve);
    Reserve findReserveById(int id);
    ListView<Reserve> getAllReserves();
    boolean isThereAnOverlap(int id, LocalDateTime startDate,LocalDateTime endDate);
}
