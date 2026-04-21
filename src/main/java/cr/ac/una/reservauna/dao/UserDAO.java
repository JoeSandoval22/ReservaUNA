/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.reservauna.dao;

import cr.ac.una.reservauna.conexion.Conexion;
import cr.ac.una.reservauna.model.Role;
import cr.ac.una.reservauna.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class UserDAO implements UserInterface {
    private Connection connection;
    
    public UserDAO(){
        this.connection = Conexion.getConnection();
    }

    @Override
    public boolean insertUser(User user) {
        String sql = "INSERT INTO USERS_TABLE (user_name, user_mail, role_id, user_state) VALUES (?, ?, ?, ?)";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getUserMail());
            ps.setInt(3, user.getUserRole().getRoleId());
            ps.setString(4, user.getUserState());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteUser(User user) {
        String sql = "DELETE FROM USERS_TABLE WHERE user_id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, user.getUserId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateUser(User user) {
        String sql = "UPDATE USERS_TABLE SET user_name = ?, user_mail = ?, role_id = ?, user_state = ? WHERE user_id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getUserMail());
            ps.setInt(3, user.getUserRole().getRoleId());
            ps.setString(4, user.getUserState());
            ps.setInt(5, user.getUserId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
            return false;
        }      
    }

    @Override
    public User findUserById(int id) {
        String sql = "SELECT * FROM USERS_TABLE WHERE user_id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new User(rs.getInt("user_id"),rs.getString("user_name"),rs.getString("user_mail"),Role.values()[rs.getInt("role_id")-1],rs.getString("user_state"));
            }
        } catch(SQLException ex){
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
     return null;   
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM USERS_TABLE";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                User user = new User(rs.getInt("user_id"),rs.getString("user_name"),rs.getString("user_mail"),Role.values()[rs.getInt("role_id")-1],rs.getString("user_state"));
                users.add(user);
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
        return users;
    }
    
}
