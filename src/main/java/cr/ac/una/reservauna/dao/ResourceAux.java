/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.reservauna.dao;

import cr.ac.una.reservauna.model.Resource;

/**
 *
 * @author andre_3e6xvb2
 */
public class ResourceAux {
    private EquipmentDAO equipment = new EquipmentDAO();
    private PlaceDAO place = new PlaceDAO();
    
    public ResourceAux(){
        
    }
    public Resource findResourceById(int id){
        Resource resource = equipment.findEquipmentById(id);
        if(resource==null){
            resource = place.findPlaceById(id);
        }
        return resource;
    }
}
