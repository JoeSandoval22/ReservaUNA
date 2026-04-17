/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.reservauna.model;

/**
 *
 * @author User
 */
public abstract class Resource {
    private int resourceId;
    private String resourceName;
    private String description;
    private String resourceState;
    
    //Constructor de consultas
    public Resource(int id, String name, String description, String state){
        this.resourceId=id;
        this.resourceName=name;
        this.description=description;
        this.resourceState=state;
    }
    //Constructor de creación
    public Resource(String name, String description, String state){
        this.resourceName=name;
        this.description=description;
        this.resourceState=state;
    }
    
    public abstract void addDescription();
    //Setters
    public void setResourceId(int id){
        this.resourceId=id;
    }
    public void setResourceName(String name){
        this.resourceName=name;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public void setResourceState(String state){
        this.resourceState=state;
    }
    //Getters
    public int getRosourceId(){
        return this.resourceId;
    }
    public String getResourceName(){
        return this.resourceName;
    }
    public String getDescription(){
        return this.description;
    }
    public String getResourceState(){
        return this.resourceState;
    }
}
