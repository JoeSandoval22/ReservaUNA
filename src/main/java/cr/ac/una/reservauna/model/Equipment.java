/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.reservauna.model;

/**
 *
 * @author User
 */
public class Equipment extends Resource{
    private String brand;
    private String model;
    private String series;

    //Constructor de consulta
    public Equipment(int id, String name, String description, String state, String brand, String model, String series) {
        super(id, name, description, state);
        this.brand=brand;
        this.model=model;
        this.series=series;
    }
    //Constructor de creación
    public Equipment(String name, String description, String state, String brand, String model, String series){
        super(name,description,state);
        this.brand=brand;
        this.model=model;
        this.series=series;
    }

    @Override
    public void addDescription() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    //Setters
    public void setBrand(String brand){
        this.brand=brand;
    }
    public void setModel(String model){
        this.model=model;
    }
    public void setSeries(String series){
        this.series=series;
    }
    //Getters
    public String getBrand(){
        return this.brand;
    }
    public String getModel(){
        return this.model;
    }
    public String getSeries(){
        return this.series;
    }
}
