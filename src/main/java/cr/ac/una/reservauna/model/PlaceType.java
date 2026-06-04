/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package cr.ac.una.reservauna.model;

/**
 *
 * @author User
 */
public enum PlaceType {
    LABORATORIO("LABORATORIO"),
    AULA("AULA");
    
    private final String placeType;
    
    PlaceType(String placeType){
        this.placeType=placeType;
    }
    
    public String getPlaceType(){
        return this.placeType;
    }
}
