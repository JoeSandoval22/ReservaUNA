/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.reservauna.model;

import java.time.LocalDateTime;

/**
 *
 * @author User
 */
public class Log {
    private int logId;
    private Reserve logReserve;
    private int userLog;
    private LocalDateTime date;
    private String detail;
    private String actionPerformed;
    
    //Constructor de consulta
    public Log(int id, Reserve reserve, int user, LocalDateTime date, String action,String detail){
        this.logId=id;
        this.logReserve=reserve;
        this.userLog=user;
        this.date=date;
        this.actionPerformed=action;
        this.detail=detail;
    }
    //Constructor de creación
    public Log(Reserve reserve, int user,LocalDateTime date, String action ,String detail){
        this.logReserve=reserve;
        this.userLog=user;
        this.date=date;
        this.actionPerformed=action;
        this.detail=detail;
    }
    
    public Log(){
        
    }
    
    //Setters
    public void setLogId(int id){
        this.logId=id;
    }
    public void setLogReserve(Reserve reserve){
        this.logReserve=reserve;
    }
    public void setUserLog(int user){
        this.userLog=user;
    }
    public void serActionPerformed(String action){
        this.actionPerformed=action;
    }
    public void setDate(LocalDateTime date){
        this.date=date;
    }
    public void setDetail(String detail){
        this.detail=detail;
    }
    //Getters
    public int getLogId(){
        return this.logId;
    }
    public Reserve getLogReserve(){
        return this.logReserve;
    }
    public int getUserLog(){
        return this.userLog;
    }
    public String getActionPerformed(){
        return this.actionPerformed;
    }
    public LocalDateTime getDate(){
        return this.date;
    }
    public String getDetail(){
        return this.detail;
    }
}
