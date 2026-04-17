/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.una.reservauna.dao;

import cr.ac.una.reservauna.model.Role;
import javafx.scene.control.ListView;

/**
 *
 * @author User
 */
public interface RoleInterface {
    Role findRoleById(int id);
    ListView<Role> callRoles();
}
