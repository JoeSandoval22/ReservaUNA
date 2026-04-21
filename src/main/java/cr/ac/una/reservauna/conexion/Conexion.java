package cr.ac.una.reservauna.conexion;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {
    private static Connection instance;
    
    private Conexion(){
        
    }
    
    public static Connection getConnection(){
        if(instance == null){
            try{
                String url = "jdbc:oracle:thin:@localhost:1521:xe";
                String user = "C##Reserva_dev";
                String password = "Joe1234";
                instance = DriverManager.getConnection(url, user, password);
            }catch(SQLException e){
                System.out.println("Error de conexión: " + e.getMessage());
            }
        }
        return instance;
    }
}
