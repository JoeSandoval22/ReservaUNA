/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.reservauna.dao;

import cr.ac.una.reservauna.conexion.Conexion;
import cr.ac.una.reservauna.model.Reserve;
import cr.ac.una.reservauna.model.ReserveItem;
import cr.ac.una.reservauna.model.ReserveStatus;
import cr.ac.una.reservauna.model.Role;
import cr.ac.una.reservauna.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andre_3e6xvb2
 */
public class ReserveItemDAO implements ReserveItemInterface {
    private Connection connection;
    
    public ReserveItemDAO(){
        this.connection=Conexion.getConnection();
    }

    @Override
    public boolean insertReserveItem(ReserveItem item) {
        int userId = item.getParentReserve().getUser().getUserId();
        Role role = item.getParentReserve().getUser().getUserRole();
        if(reserveLimitPerUser(userId,role)){
            return false;
        }
        if(isThereAnOverlap(item.getResourceItem().getResourceId(), item.getStartDateItem(),item.getEndDateItem())){
            return false;
        }
        String sqlItem = "INSERT INTO RESERVE_ITEM (parent_reserve, resource_id,start_date,end_date) VALUES (?,?,?,?)";
        try{
            PreparedStatement ps = connection.prepareStatement(sqlItem);
            ps.setInt(1, item.getParentReserve().getReserveId());
            ps.setInt(2, item.getResourceItem().getResourceId());
            ps.setTimestamp(3, Timestamp.valueOf(item.getStartDateItem()));
            ps.setTimestamp(4, Timestamp.valueOf(item.getEndDateItem()));
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error:"+ex.getMessage());   
            return false;
        }
    }

    @Override
    public boolean deleteReserveItem(ReserveItem item) {
        String sqlItem = "DELETE FROM RESERVE_ITEM WHERE reserve_item_id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sqlItem);
            ps.setInt(1, item.getReserveItemId());
            ps.executeUpdate();
            return true;
        } catch(SQLException ex){
            System.out.println("Error: "+ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateReserveItem(ReserveItem item) {
        String sqlItem = "UPDATE RESERVE_ITEM SET parent_reserve = ?, resource_id = ?, start_date = ?, end_date = ? WHERE reserve_item_id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sqlItem);
            ps.setInt(1, item.getParentReserve().getReserveId());
            ps.setInt(2, item.getResourceItem().getResourceId());
            ps.setTimestamp(3, Timestamp.valueOf(item.getStartDateItem()));
            ps.setTimestamp(4, Timestamp.valueOf(item.getEndDateItem()));
            ps.setInt(5, item.getReserveItemId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error: "+ex.getMessage()); 
            return false;        
        }
    }

    @Override
    public ReserveItem findReserveItemById(int id) {
        ResourceAux aux = new ResourceAux();
        ReserveDao reserve = new ReserveDao();
        String sqlItem = "SELECT * FROM RESERVE_ITEM WHERE reserve_item_id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sqlItem);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new ReserveItem(rs.getInt("reserve_item_id"),reserve.findReserveById(rs.getInt("parent_reserve")),
                        aux.findResourceById(rs.getInt("resource_id")),rs.getTimestamp("start_date").toLocalDateTime(),
                        rs.getTimestamp("end_date").toLocalDateTime()
                );
            }
        } catch (SQLException ex) {
            System.out.println("Error: "+ex.getMessage());    
            return null;
        }
        return null;
    }

    @Override
    public List<ReserveItem> getAllReserveItems() {
        List reservesItems = new ArrayList<>();
        ResourceAux aux = new ResourceAux();
        ReserveDao reserve = new ReserveDao();
        String sqlItem = "SELECT * FROM RESERVE_ITEM";
        try{
            PreparedStatement ps = connection.prepareStatement(sqlItem);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ReserveItem item = new ReserveItem(rs.getInt("reserve_item_id"),reserve.findReserveById(rs.getInt("parent_reserve")),
                        aux.findResourceById(rs.getInt("resource_id")),rs.getTimestamp("start_date").toLocalDateTime(),
                        rs.getTimestamp("end_date").toLocalDateTime());
                reservesItems.add(item);
            }
        } catch(SQLException ex){
            System.out.println("Error: "+ex.getMessage());
            return null;
        }
        return reservesItems;
    }

    @Override
    public boolean reserveLimitPerUser(int userId, Role role) {
        String sqlItem = "SELECT COUNT(*) FROM RESERVE_ITEM rItem JOIN RESERVE r ON rItem.parent_reserve = r.reserve_id "
               + "WHERE r.user_id = ? AND (r.reserve_status = 'PENDIENTE' OR r.reserve_status = 'APROBADA')";
        try{ 
            PreparedStatement ps = connection.prepareStatement(sqlItem);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int count = rs.getInt(1);
                return count >= role.getMaxReserves();
            }
        } catch (SQLException ex) {
            System.out.println("Error: "+ex.getMessage()); 
            return false;
        }   
        return false;
    }

    @Override
    public boolean isThereAnOverlap(int id, LocalDateTime startDate, LocalDateTime endDate) {
        String sqlItem = "SELECT * FROM RESERVE_ITEM ri JOIN RESERVE r ON ri.parent_reserve = r.reserve_id "+ 
                         "WHERE ri.resource_id = ? AND ri.start_date < ? AND ri.end_date > ? AND r.reserve_status NOT IN ('CANCELADA','RECHAZADA')";       
        try{
            PreparedStatement ps = connection.prepareStatement(sqlItem);
            ps.setInt(1, id);
            ps.setTimestamp(2, Timestamp.valueOf(endDate));
            ps.setTimestamp(3, Timestamp.valueOf(startDate));
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                System.out.println("No es posible hace una reserva con este recurso en este período de tiempo.");
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Error: "+ex.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public ReserveItem findReserveById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ReserveItem> findByStatus(ReserveStatus status) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ReserveItem> findByDate(LocalDateTime startDate, LocalDateTime endDate) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ReserveItem> findByUserId(int userId, int reserveId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ReserveItem> findByUserId(int userId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean approveReserve(int reserveId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean rejectReserve(int reserveId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean cancelReserve(int reserveId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
