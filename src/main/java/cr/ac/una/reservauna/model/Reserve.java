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
public class Reserve {
    private int reserveId;
    private User user;
    private Resource resource;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String reason;
    private LocalDateTime createAt;
    private ReserveStatus status;
    
    //Constructor de consulta
    public Reserve(int id, User user, Resource resource, LocalDateTime startDate,LocalDateTime endDate,String reason, LocalDateTime createAt, ReserveStatus status){
        this.reserveId=id;
        this.user=user;
        this.resource=resource;
        this.startDate=startDate;
        this.endDate=endDate;
        this.reason=reason;
        this.createAt=createAt;
        this.status=status;
    }
    //Constructor de creación
    public Reserve(User user, Resource resource, LocalDateTime startDate,LocalDateTime endDate,String reason, LocalDateTime createAt,ReserveStatus status){
        this.user=user;
        this.resource=resource;
        this.startDate=startDate;
        this.endDate=endDate;
        this.reason=reason;
        this.createAt=createAt;
        this.status=status;
    }
    
    public Reserve(){
        
    }
    //Reserve
    public void setReserveId(int id){
        this.reserveId = id;
    }
    public void setUser(User user){
        this.user=user;
    }
    public void setResource(Resource resource){
        this.resource=resource;
    }
    public void setStartDate(LocalDateTime startDate){
        this.startDate=startDate;
    }
    public void setEndDate(LocalDateTime endDate){
        this.endDate=endDate;
    }
    public void setReason(String reason){
        this.reason=reason;
    }
    public void setCreateAt(LocalDateTime createAt){
        this.createAt=createAt;
    }
    public void setStatus(ReserveStatus status){
        this.status=status;
    }
    //Getters
    public int getReserveId(){
        return this.reserveId;
    }
    public User getUser(){
        return this.user;
    }
    public Resource getResource(){
        return this.resource;
    }
    public LocalDateTime getStartDate(){
        return this.startDate;
    }
    public LocalDateTime getEndDate(){
        return this.endDate;
    }
    public String getReason(){
        return this.reason;
    }
    public LocalDateTime getCreateAt(){
        return this.createAt;
    }
    public ReserveStatus getStatus(){
        return this.status;
    }
}