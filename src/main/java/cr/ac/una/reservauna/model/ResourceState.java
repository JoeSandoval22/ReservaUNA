/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package cr.ac.una.reservauna.model;

/**
 *
 * @author User
 */
public enum ResourceState {
    DISPONIBLE("DISPONIBLE"),
    NO_DISPONIBLE("NO_DISPONIBLE");
    
    private final String resourceState;
    
    ResourceState(String resourceState){
        this.resourceState=resourceState;
    }
           
    public String getResourceState(){
        return this.resourceState;
    }
}
