/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.una.reservauna.dao;

import cr.ac.una.reservauna.model.Equipment;
import java.util.List;

/**
 *
 * @author User
 */
public interface EquipmentInterface extends ResourceInterface {
    boolean insertEquipment(Equipment equipment);
    boolean deleteEquipment(Equipment equipment);
    boolean updateEquipment(Equipment equipment);
    Equipment findEquipmentById(int id);
    List<Equipment> getAllEquipmentes();
}
