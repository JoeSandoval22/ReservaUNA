/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.una.reservauna.dao;

import cr.ac.una.reservauna.model.ReserveItem;
import cr.ac.una.reservauna.model.Role;
import javafx.scene.control.ListView;

/**
 *
 * @author User
 */
public interface ReserveItemInterface {
    boolean insertReserveItem(ReserveItem item);
    boolean deleteReserveItem(ReserveItem item);
    boolean upgradeReserveItem(ReserveItem item);
    ReserveItem findReserveItemById(int id);
    ListView<ReserveItem> getAllReserveItems();
    boolean reserveLimitPerUser(Role role);
}
