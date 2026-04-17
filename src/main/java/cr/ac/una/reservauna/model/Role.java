/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package cr.ac.una.reservauna.model;

/**
 *
 * @author User
 */
public enum Role {
    ADMINISTRADOR(1,5),
    ENCARGADO(2,4),
    PROFESOR(3,3),
    ESTUDIANTE(4,2);
    
    private final int roleId;
    private final int maxReserves;
    
    Role(int roleId,int maxReserves){
        this.roleId=roleId;
        this.maxReserves=maxReserves;
    }
    //Getters
    public int getRoleId(){
        return this.roleId;
    }
    public int getMaxReserves(){
        return this.maxReserves;
    }
}
