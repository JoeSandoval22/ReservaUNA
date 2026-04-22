/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.reservauna.dao;

import cr.ac.una.reservauna.conexion.Conexion;
import cr.ac.una.reservauna.model.Place;
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
public class PlaceDAO implements PlaceInterface {
    private Connection connection;
    
    public PlaceDAO(){
        this.connection=Conexion.getConnection();
    }

    @Override
    public boolean insertPlace(Place place) {
        String sqlRes = "INSERT INTO RESOURCES (resource_name,res_description,resource_state) VALUES (?,?,?)";
        String sqlPlace = "INSERT INTO PLACE (resource_capacity, resource_location, resource_type, resource_id) VALUES (?,?,?,?)";
        try{
            PreparedStatement psRes = connection.prepareStatement(sqlRes,new String[]{"RESOURCE_ID"});
            psRes.setString(1, place.getResourceName());
            psRes.setString(2, place.getDescription());
            psRes.setString(3, place.getResourceState());
            psRes.executeUpdate();
            ResultSet rs = psRes.getGeneratedKeys();
            rs.next();
            int resourceId = rs.getInt(1);
            
            PreparedStatement psPlace = connection.prepareStatement(sqlPlace);
            psPlace.setInt(1, place.getCapacity());
            psPlace.setString(2, place.getLocation());
            psPlace.setString(3, place.getType());
            psPlace.setInt(4, resourceId);
            psPlace.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error: "+ ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deletePlace(Place place) {
        String sqlPlace = "DELETE FROM PLACE WHERE resource_id = ?";
        String sqlRes = "DELETE FROM RESOURCES WHERE resource_id = ?";
        try{
            PreparedStatement psPlace = connection.prepareStatement(sqlPlace);
            psPlace.setInt(1, place.getResourceId());
            psPlace.executeUpdate();
            PreparedStatement psRes = connection.prepareStatement(sqlRes);
            psRes.setInt(1, place.getResourceId());
            psRes.executeUpdate();
            return true;
        } catch(SQLException ex){
            System.out.println("Error: "+ ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean updatePlace(Place place) {
        String sqlPlace = "UPDATE PLACE SET resource_capacity = ?, resource_location = ?, resource_type = ? WHERE resource_id = ?";
        String sqlRes = "UPDATE RESOURCES SET resource_name = ?, res_description = ?, resource_state = ? WHERE resource_id = ?";
        try{
            PreparedStatement psPlace = connection.prepareStatement(sqlPlace);
            psPlace.setInt(1, place.getCapacity());
            psPlace.setString(2, place.getLocation());
            psPlace.setString(3, place.getType());
            psPlace.setInt(4, place.getResourceId());
            psPlace.executeUpdate();
            PreparedStatement psRes = connection.prepareStatement(sqlRes);
            psRes.setString(1, place.getResourceName());
            psRes.setString(2, place.getDescription());
            psRes.setString(3, place.getResourceState());
            psRes.setInt(4, place.getResourceId());
            psRes.executeUpdate();
            return true;
        } catch(SQLException ex){
            System.out.println("Error: "+ ex.getMessage());
            return false;
        }
    }

    @Override
    public Place findPlaceById(int id) {
        String sqlPlace = "SELECT e.resource_id, r.resource_name, r.res_description, r.resource_state, e.resource_capacity, e.resource_location, e.resource_type "+
                "FROM PLACE e JOIN RESOURCES r ON e.resource_id = r.resource_id " +
                "WHERE e.resource_id = ?";
        try{
            PreparedStatement psPlace = connection.prepareStatement(sqlPlace);
            psPlace.setInt(1, id);
            ResultSet rs = psPlace.executeQuery();
            if(rs.next()){
                return new Place(rs.getInt("resource_id"), rs.getString("resource_name"),rs.getString("res_description"),rs.getString("resource_state"),rs.getInt("resource_capacity"),rs.getString("resource_location"),rs.getString("resource_type"));
            }
        } catch(SQLException ex){
            System.out.println("Error: "+ex.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public List<Place> getAllPlaces() {
        List<Place> places = new ArrayList<>();
        String sqlPlace = "SELECT e.resource_id, r.resource_name, r.res_description, r.resource_state, e.resource_capacity, e.resource_location, e.resource_type " +
                "FROM PLACE e JOIN RESOURCES r ON e.resource_id = r.resource_id ";
        try{
            PreparedStatement ps = connection.prepareStatement(sqlPlace);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Place place = new Place(rs.getInt("resource_id"), rs.getString("resource_name"),rs.getString("res_description"),rs.getString("resource_state"),rs.getInt("resource_capacity"),rs.getString("resource_location"),rs.getString("resource_type"));
                places.add(place);
            }
        } catch(SQLException ex){
            System.out.println("Error: "+ex.getMessage());
            return null;
        }
        return places;
    }    
}
