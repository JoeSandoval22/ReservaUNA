/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.una.reservauna.dao;

import cr.ac.una.reservauna.model.User;
import javafx.scene.control.ListView;

/**
 *
 * @author User
 */
public interface UserInterface {
    boolean insertUser(User user);
    boolean deleteUser(User user);
    boolean upgradeUser(User user);
    User findUserById(int id);
    ListView getAllUsers();
}
