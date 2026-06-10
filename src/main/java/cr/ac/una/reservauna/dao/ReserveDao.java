/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.reservauna.dao;

import cr.ac.una.reservauna.conexion.Conexion;
import cr.ac.una.reservauna.model.Log;
import cr.ac.una.reservauna.model.Reserve;
import cr.ac.una.reservauna.model.ReserveStatus;
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
public class ReserveDao implements ReserveInterface {
    private Connection connection;
    private LogDAO logDao = new LogDAO();
    
    
    
    public ReserveDao(){
        this.connection = Conexion.getConnection();
    }
    public void setLogDAO(LogDAO logDao){
        this.logDao=logDao;
    }
    
    @Override
    public boolean insertReserve(Reserve reserve) {
        if(isThereAnOverlap(reserve.getResource().getResourceId(),reserve.getStartDate(),reserve.getEndDate())){return false;}
        String sqlReserve = "INSERT INTO RESERVE (user_id,resource_id,start_date,end_date,reason,creat_at,reserve_status) VALUES (?,?,?,?,?,?,?)";
        try{
            PreparedStatement ps = connection.prepareStatement(sqlReserve, new String[]{"reserve_id"});
            ps.setInt(1, reserve.getUser().getUserId());
            ps.setInt(2, reserve.getResource().getResourceId());
            ps.setTimestamp(3, Timestamp.valueOf(reserve.getStartDate()));
            ps.setTimestamp(4, Timestamp.valueOf(reserve.getEndDate()));
            ps.setString(5, reserve.getReason());
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(7, reserve.getStatus().getStatus());
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()){
                if(rs.next()){
                    int generatedId = rs.getInt(1);
                    reserve.setReserveId(generatedId);
                }
            }
            Log log = new Log(reserve,reserve.getUser().getUserId(),LocalDateTime.now(),"CREAR","Reserva creada.");
            this.logDao.insertLog(log);
            return true;
        } catch (SQLException ex) {
            System.out.println("Error: "+ex.getMessage());
            return false;
        } 
    }

    @Override
    public boolean deleteReserve(Reserve reserve) {
        Reserve existingReserve = findReserveById(reserve.getReserveId());
        if(isThereAnOverlap(reserve.getResource().getResourceId(),reserve.getStartDate(),reserve.getEndDate())){return false;}
        System.out.println("DEBUG - reserveId: " + reserve.getReserveId() + " | existingReserve: " + existingReserve);
        String sqlReserve = "DELETE FROM RESERVE WHERE reserve_id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sqlReserve);
            ps.setInt(1, reserve.getReserveId());
            ps.executeUpdate();
            Log log = new Log(existingReserve,existingReserve.getUser().getUserId(),LocalDateTime.now(),"ELIMINAR","Reserva eliminada");
            this.logDao.insertLog(log);
            return true;
        } catch(SQLException ex){
            System.out.println("Error: "+ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateReserve(Reserve reserve) {
        Reserve existingReserve = findReserveById(reserve.getReserveId());
        String sqlReserve = "UPDATE RESERVE SET resource_id = ?, start_date = ?, end_date = ?, reason = ?, creat_at = ? WHERE reserve_id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sqlReserve);
            ps.setInt(1, reserve.getResource().getResourceId());
            ps.setTimestamp(2, Timestamp.valueOf(reserve.getStartDate()));
            ps.setTimestamp(3, Timestamp.valueOf(reserve.getEndDate()));
            ps.setString(4, reserve.getReason());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(6 , reserve.getReserveId());
            ps.executeUpdate();
            Log log = new Log(existingReserve,existingReserve.getUser().getUserId(),LocalDateTime.now(),"ACTUALIZAR","Reserva actualizada.");
            this.logDao.insertLog(log);
            return true;
        } catch(SQLException ex){
            System.out.println("Error: "+ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean isThereAnOverlap(int id, LocalDateTime startDate, LocalDateTime endDate) {
        String sql = "SELECT * FROM RESERVE WHERE reserve_id = ? AND start_date <= ? AND end_date >= ? AND reserve_status NOT IN ('CANCELADA','RECHAZADA')";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setTimestamp(2, Timestamp.valueOf(endDate));
            ps.setTimestamp(3, Timestamp.valueOf(startDate));
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                System.out.println("No es posible hace una reserva con este recurso en este periodo de tiempo.");
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Error: "+ex.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public Reserve findReserveById(int id) {
        String sql = "SELECT * FROM RESERVE WHERE reserve_id = ?";
        UserDAO userDAO = new UserDAO();
        ResourceAux aux = new ResourceAux();
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Reserve(
                            rs.getInt("reserve_id"),userDAO.findUserById(rs.getInt("user_id")),
                            aux.findResourceById(rs.getInt("resource_id")),rs.getTimestamp("start_date").toLocalDateTime(),
                            rs.getTimestamp("end_date").toLocalDateTime(),rs.getString("reason"),
                            rs.getTimestamp("creat_at").toLocalDateTime(),ReserveStatus.valueOf(rs.getString("reserve_status")));
            }
        } catch(SQLException ex){
            System.out.println("Error: "+ex.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public List<Reserve> getAllReserves() {
        List<Reserve> reserves = new ArrayList<>();
        UserDAO userDAO = new UserDAO();
        ResourceAux aux = new ResourceAux();
        String sql = "SELECT * FROM RESERVE";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Reserve reserve = new Reserve(rs.getInt("reserve_id"),
                            userDAO.findUserById(rs.getInt("user_id")),aux.findResourceById(rs.getInt("resource_id")),
                            rs.getTimestamp("start_date").toLocalDateTime(),rs.getTimestamp("end_date").toLocalDateTime(),
                            rs.getString("reason"),rs.getTimestamp("creat_at").toLocalDateTime(),
                            ReserveStatus.valueOf(rs.getString("reserve_status")));
                reserves.add(reserve);
            }
        } catch(SQLException ex){
            System.out.println("Error: "+ex.getMessage());
            return null;
        }
        return reserves;
    }

    @Override
    public List<Reserve> findByStatus(ReserveStatus status) {
        List<Reserve> reservesByStatus = new ArrayList<>();
        UserDAO userDAO = new UserDAO();
        ResourceAux aux = new ResourceAux();
        String sql = "SELECT * FROM RESERVE WHERE reserve_status = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, status.getStatus());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Reserve reserve = new Reserve(rs.getInt("reserve_id"),userDAO.findUserById(rs.getInt("user_id")),
                            aux.findResourceById(rs.getInt("resource_id")),rs.getTimestamp("start_date").toLocalDateTime(),
                            rs.getTimestamp("end_date").toLocalDateTime(),rs.getString("reason"),
                            rs.getTimestamp("creat_at").toLocalDateTime(),ReserveStatus.valueOf(rs.getString("reserve_status")));
                reservesByStatus.add(reserve);
            }  
        } catch (SQLException ex) {
            System.out.println("Error: "+ex.getMessage()); return null;
        }
        return reservesByStatus;
    }

    @Override
    public List<Reserve> findByDate(LocalDateTime startDate, LocalDateTime endDate) {
        List<Reserve> reservesByDate = new ArrayList<>();
        UserDAO userDAO = new UserDAO();
        ResourceAux aux = new ResourceAux();
        String sql = "SELECT * FROM RESERVE WHERE start_date >= ? AND end_date <= ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(startDate));
            ps.setTimestamp(2, Timestamp.valueOf(endDate));
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Reserve reserves = new Reserve(rs.getInt("reserve_id"),userDAO.findUserById(rs.getInt("user_id")),
                            aux.findResourceById(rs.getInt("resource_id")),rs.getTimestamp("start_date").toLocalDateTime(),
                            rs.getTimestamp("end_date").toLocalDateTime(),rs.getString("reason"),
                            rs.getTimestamp("creat_at").toLocalDateTime(),ReserveStatus.valueOf(rs.getString("reserve_status")));
                reservesByDate.add(reserves);
            }
        } catch (SQLException ex) {
            System.out.println("Error: "+ex.getMessage()); return null;
        }
        return reservesByDate;
    }
    
    @Override
    public Reserve findByUserId(int userId, int reserveId){
        UserDAO userDAO = new UserDAO();
        ResourceAux aux = new ResourceAux();
        String sql = "SELECT * FROM RESERVE WHERE user_id = ? AND reserve_id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, reserveId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Reserve(
                            rs.getInt("reserve_id"),
                            userDAO.findUserById(rs.getInt("user_id")),
                            aux.findResourceById(rs.getInt("resource_id")),
                            rs.getTimestamp("start_date").toLocalDateTime(),
                            rs.getTimestamp("end_date").toLocalDateTime(),
                            rs.getString("reason"),
                            rs.getTimestamp("creat_at").toLocalDateTime(),
                            ReserveStatus.valueOf(rs.getString("reserve_status")));
            }
        } catch (SQLException ex) {
            System.out.println("Error: "+ex.getMessage()); return null;
        }
        return null;
    }

    @Override
    public List<Reserve> findByUserId(int userId) {
        List<Reserve> reservesByUserId = new ArrayList<>();
        UserDAO userDAO = new UserDAO();
        ResourceAux aux = new ResourceAux();
        String sql = "SELECT * FROM RESERVE WHERE user_id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Reserve reserve = new Reserve(rs.getInt("reserve_id"),
                            userDAO.findUserById(rs.getInt("user_id")),
                            aux.findResourceById(rs.getInt("resource_id")),
                            rs.getTimestamp("start_date").toLocalDateTime(),
                            rs.getTimestamp("end_date").toLocalDateTime(),
                            rs.getString("reason"),
                            rs.getTimestamp("creat_at").toLocalDateTime(),
                            ReserveStatus.valueOf(rs.getString("reserve_status")));
                reservesByUserId.add(reserve);
            }
        } catch(SQLException ex){
            System.out.println("Error: "+ex.getMessage()); return null;
        }
        return reservesByUserId;
    }
    
    @Override
    public boolean canChangeStatus(int reserveId, ReserveStatus status){
        Reserve reserve = findReserveById(reserveId);
        if(reserve==null)return false;
        ReserveStatus currentStatus = reserve.getStatus();
        switch (currentStatus){
            case PENDIENTE->{
                return status == ReserveStatus.APROBADA || status == ReserveStatus.RECHAZADA || status == ReserveStatus.CANCELADA;
            }
            case APROBADA->{
                return status == ReserveStatus.CANCELADA;
            }
            case RECHAZADA, CANCELADA->{
                return false;
            }
            default->{
                return false;
            }
        }
    }

    @Override
    public boolean approveReserve(int reserveId) {
        Reserve reserveUser = findReserveById(reserveId);
        if(!canChangeStatus(reserveId,ReserveStatus.APROBADA)){
            System.out.println("No es posible aprobar esta solicitud.");
            return false;
        }
        String sqlApprove = "UPDATE RESERVE SET reserve_status = 'APROBADA' WHERE reserve_id = ? ";
        try{
            PreparedStatement ps = connection.prepareStatement(sqlApprove);
            ps.setInt(1, reserveId);
            ps.executeUpdate();
            Log log = new Log(reserveUser,reserveUser.getUser().getUserId(),LocalDateTime.now(),"APROBAR","Reserva aprobada");
            this.logDao.insertLog(log);
            return true;
        }catch(SQLException ex){
            System.out.println("Error: "+ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean rejectReserve(int reserveId) {
        Reserve reserveUser = findReserveById(reserveId);
        if(!canChangeStatus(reserveId,ReserveStatus.RECHAZADA)){
            System.out.println("No es posible rechazar esta solicitud.");
            return false;
        }
        String sqlReject = "UPDATE RESERVE SET reserve_status = 'RECHAZADA' WHERE reserve_id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sqlReject);
            ps.setInt(1, reserveId);
            ps.executeUpdate();
            Log log = new Log(reserveUser,reserveUser.getUser().getUserId(),LocalDateTime.now(),"RECHAZAR","Reserva rechazada");
            this.logDao.insertLog(log);
            return true;
        } catch(SQLException ex){
            System.out.println("Error: "+ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean cancelReserve(int reserveId) {
        Reserve reserveUser = findReserveById(reserveId);
        if(!canChangeStatus(reserveId,ReserveStatus.CANCELADA)){
            System.out.println("No es posible cancelar esta solicitud.");
            return false;
        }
        String sqlCancel = "UPDATE RESERVE SET reserve_status = 'CANCELADA' WHERE reserve_id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sqlCancel);
            ps.setInt(1, reserveId);
            ps.executeUpdate();
            Log log = new Log(reserveUser,reserveUser.getUser().getUserId(),LocalDateTime.now(),"CANCELAR","Reserva cancelada");
            this.logDao.insertLog(log);
            return true;
        } catch(SQLException ex){
            System.out.println("Error: "+ex.getMessage());
            return false;
        }
    }
}