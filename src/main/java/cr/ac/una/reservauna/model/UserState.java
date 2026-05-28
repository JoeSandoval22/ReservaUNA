/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package cr.ac.una.reservauna.model;

/**
 *
 * @author User
 */
public enum UserState {
    ACTIVO("ACTIVO"),
    INACTIVO("INACTIVO");
    
    private final String userState;
    
    UserState(String state){
        this.userState=state;
    }
    
    public String getUserState(){
        return this.userState;
    }
}
