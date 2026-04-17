/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.reservauna.model;

/**
 *
 * @author User
 */
public class Place extends Resource {
    private int capacity;
    private String location;
    private String type;

    public Place(int id, String name, String description, String state, int capacity, String location, String type) {
        super(id, name, description, state);
        this.capacity=capacity;
        this.location=location;
        this.type=type;
    }
    
    public Place(String name, String description, String state, int capacity, String location, String type) {
        super(name, description, state);
        this.capacity=capacity;
        this.location=location;
        this.type=type;
    }

    @Override
    public void addDescription() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    //Setters
    public void setCapacity(int capacity){
        this.capacity=capacity;
    }
    public void setLocation(String location){
        this.location=location;
    }
    public void setType(String type){
        this.type=type;
    }
    //Getters
    public int getCapacity(){
        return this.capacity;
    }
    public String getLocation(){
        return this.location;
    }
    public String getType(){
        return this.type;
    }
}
