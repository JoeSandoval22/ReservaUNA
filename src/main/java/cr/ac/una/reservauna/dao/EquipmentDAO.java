/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.reservauna.dao;

import cr.ac.una.reservauna.conexion.Conexion;
import cr.ac.una.reservauna.model.Equipment;
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
public class EquipmentDAO implements EquipmentInterface {
    private Connection connection;
    
    public EquipmentDAO(){
        this.connection=Conexion.getConnection();
    }

    @Override
    public boolean insertEquipment(Equipment equipment) {
        String sqlResource = "INSERT INTO RESOURCES (resource_name, res_description, resource_state) VALUES (?,?,?)";
        String sql = "INSERT INTO EQUIPMENT (brand, resource_model, series, resource_id) VALUES (?,?,?,?)";
        try{
            PreparedStatement psRes = connection.prepareStatement(sqlResource,new String[]{"RESOURCE_ID"});
            psRes.setString(1, equipment.getResourceName());
            psRes.setString(2, equipment.getDescription());
            psRes.setString(3, equipment.getResourceState());
            psRes.executeUpdate();
            ResultSet rs = psRes.getGeneratedKeys();
            rs.next();
            int resourceId = rs.getInt(1);
            
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, equipment.getBrand());
            ps.setString(2, equipment.getModel());
            ps.setString(3, equipment.getSeries());
            ps.setInt(4, resourceId);
            ps.executeUpdate();
            return true;
        } catch(SQLException ex){
            System.out.println("Error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteEquipment(Equipment equipment) {
        String sqlEquipment = "DELETE FROM EQUIPMENT WHERE resource_id = ?";
        String sqlResource = "DELETE FROM RESOURCES WHERE resource_id = ?"; 
        try{
            PreparedStatement psEquipment = connection.prepareStatement(sqlEquipment);
            psEquipment.setInt(1, equipment.getResourceId());
            psEquipment.executeUpdate();
            PreparedStatement psRes = connection.prepareStatement(sqlResource);
            psRes.setInt(1, equipment.getResourceId());
            psRes.executeUpdate();
            return true;
        } catch(SQLException ex){
            System.out.println("Error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateEquipment(Equipment equipment) {
        String sqlEquipment = "UPDATE EQUIPMENT SET brand = ?, resource_model = ?, series = ? WHERE resource_id = ?";
        String sqlResource = "UPDATE RESOURCES SET resource_name = ?, res_description = ?, resource_state = ? WHERE resource_id = ?";
        try{
            PreparedStatement psEquipment = connection.prepareStatement(sqlEquipment);
            psEquipment.setString(1, equipment.getBrand());
            psEquipment.setString(2, equipment.getModel());
            psEquipment.setString(3, equipment.getSeries());
            psEquipment.setInt(4, equipment.getResourceId());
            psEquipment.executeUpdate();
            PreparedStatement psRes = connection.prepareStatement(sqlResource);
            psRes.setString(1, equipment.getResourceName());
            psRes.setString(2, equipment.getDescription());
            psRes.setString(3, equipment.getResourceState());
            psRes.setInt(4, equipment.getResourceId());
            psRes.executeUpdate();
            return true;
        } catch(SQLException ex){
            System.out.println("Error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public Equipment findEquipmentById(int id) {
        String sqlEquipment = "SELECT e.resource_id, r.resource_name, r.res_description, r.resource_state, e.brand, e.resource_model, e.series " +
                 "FROM EQUIPMENT e JOIN RESOURCES r ON e.resource_id = r.resource_id " +
                 "WHERE e.resource_id = ?";
        try{
            PreparedStatement psEquipment = connection.prepareStatement(sqlEquipment);
            psEquipment.setInt(1, id);
            ResultSet rsE = psEquipment.executeQuery();
            if(rsE.next()){
                return new Equipment(rsE.getInt("resource_id"),rsE.getString("resource_name"),rsE.getString("res_description"),rsE.getString("resource_state"),rsE.getString("brand"),rsE.getString("resource_model"),rsE.getString("series"));
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public List<Equipment> getAllEquipmentes() {
        List<Equipment> equipments = new ArrayList<>();
        String sqlEquipment = "SELECT e.resource_id, r.resource_name, r.res_description, r.resource_state, e.brand, e.resource_model, e.series " +
                 "FROM EQUIPMENT e JOIN RESOURCES r ON e.resource_id = r.resource_id";
        try{
            PreparedStatement ps = connection.prepareStatement(sqlEquipment);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Equipment equiment = new Equipment(rs.getInt("resource_id"),rs.getString("resource_name"),rs.getString("res_description"),rs.getString("resource_state"),rs.getString("brand"),rs.getString("resource_model"),rs.getString("series"));
                equipments.add(equiment);
            }
        } catch (SQLException ex) {
            System.out.println("Error: "+ex.getMessage());
            return null;
        }
        return equipments;
    }
}
