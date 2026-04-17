/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package cr.ac.una.reservauna.model;

/**
 *
 * @author User
 */
public enum ReserveStatus {
    PENDIENTE("Pendiente"),
    APROBADA("Aprobada"),
    RECHAZADA("Rechazada"), 
    CANCELADA("Cancelada");
    
    private final String status;
    
    private ReserveStatus(String status){
        this.status=status;
    }
    
    public String getStatus(){
        return this.status;
    }
}
