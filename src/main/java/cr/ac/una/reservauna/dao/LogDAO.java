/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.reservauna.dao;

import cr.ac.una.reservauna.conexion.Conexion;
import cr.ac.una.reservauna.model.Log;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andre_3e6xvb2
 */
public class LogDAO implements LogInterface {
    private Connection connection;
    
    public LogDAO(){
        this.connection=Conexion.getConnection();
    }

    @Override
    public boolean insertLog(Log log) {
       String sqlLog = "INSERT INTO LOG_TABLE (reserve_id,user_id,log_date,detail,action) VALUES (?,?,?,?,?)";
       try{
           PreparedStatement ps = connection.prepareStatement(sqlLog);
           ps.setInt(1, log.getLogReserve().getReserveId());
           ps.setInt(2, log.getUserLog());
           ps.setTimestamp(3,Timestamp.valueOf(log.getDate()));
           ps.setString(4, log.getDetail());
           ps.setString(5, log.getActionPerformed());
           ps.executeUpdate();
           return true;
       } catch (SQLException ex) {
           System.out.println("Error: "+ex.getMessage()); return false;
       }
    }
   
    @Override
    public Log findLogById(int id) {
        ReserveDao reserve = new ReserveDao();
        UserDAO user = new UserDAO();
        String sqlLog = "SELECT * FROM LOG_TABLE WHERE log_id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sqlLog);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Log(rs.getInt("log_id"),reserve.findReserveById(rs.getInt("reserve_id")),
                        rs.getInt("user_id"),rs.getTimestamp("log_date").toLocalDateTime(),
                        rs.getString("action"),rs.getString("detail"));
            }
        } catch (SQLException ex) {
            System.out.println("Error: "+ex.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public List<Log> getAllLogs() {
        List<Log> logs = new ArrayList<>();
        ReserveDao reserve = new ReserveDao();
        UserDAO user = new UserDAO();
        String sqlLog = "SELECT * FROM LOG_TABLE";
        try{
            PreparedStatement ps = connection.prepareStatement(sqlLog);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Log log = new Log(rs.getInt("log_id"),reserve.findReserveById(rs.getInt("reserve_id")),
                        rs.getInt("user_id"),rs.getTimestamp("log_date").toLocalDateTime(),
                        rs.getString("action"),rs.getString("detail"));
                logs.add(log);
            }
        } catch(SQLException ex){
            System.out.println("Error: "+ex.getMessage());
            return null;
        }
        return logs;
    }   
}
