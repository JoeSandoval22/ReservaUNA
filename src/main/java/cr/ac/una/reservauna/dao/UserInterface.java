/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.una.reservauna.dao;

import cr.ac.una.reservauna.model.User;
import java.util.List;

/**
 *
 * @author User
 */
public interface UserInterface {
    boolean insertUser(User user);
    boolean deleteUser(User user);
    boolean updateUser(User user);
    User findUserById(int id);
    List<User> getAllUsers();
}
