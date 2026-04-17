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
public class ReserveItem {
    private int reserveItemId;
    private Reserve parentReserve;
    private Resource resource;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    
    public ReserveItem(int id, Reserve reserve, Resource resource,LocalDateTime startDate, LocalDateTime endDate){
        this.reserveItemId=id;
        this.parentReserve=reserve;
        this.resource=resource;
        this.startDate=startDate;
        this.endDate=endDate;
    }
    
    public ReserveItem(Reserve reserve, Resource resource,LocalDateTime startDate, LocalDateTime endDate){
        this.parentReserve=reserve;
        this.resource=resource;
        this.startDate=startDate;
        this.endDate=endDate;
    }
    
    public ReserveItem(){
        
    }
    //Setters
    public void setReserveItemId(int id){
        this.reserveItemId=id;
    }
    public void setParentReserve(Reserve reserve){
        this.parentReserve=reserve;
    }
    public void setResourceItem(Resource resource){
        this.resource=resource;
    }
    public void setStartDateItem(LocalDateTime startDate){
        this.startDate=startDate;
    }
    public void setEndDateItem(LocalDateTime endDate){
        this.endDate=endDate;
    }
    //Getters
    public int getReserveItemId(){
        return this.reserveItemId;
    }
    public Reserve getParentReserve(){
        return this.parentReserve;
    }
    public Resource getResourceItem(){
        return this.resource;
    }
    public LocalDateTime getStartDateItem(){
        return this.startDate;
    }
    public LocalDateTime getEndDateItem(){
        return this.endDate;
    }
}
