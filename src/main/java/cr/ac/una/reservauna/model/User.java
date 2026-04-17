/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.reservauna.model;

/**
 *
 * @author User
 */
public class User {
    private int userId;
    private String userName;
    private String userMail;
    private Role role;
    private String userState;
    
    public User(int id,String name,String mail,Role role,String state){
        this.userId=id;
        this.userName=name;
        this.userMail=mail;
        this.role=role;
        this.userState=state;
    }
    
    public User(String name,String mail,Role role,String state){
        this.userName=name;
        this.userMail=mail;
        this.role=role;
        this.userState=state;
    }
    //Setters
    public void setUserId(int id){
        this.userId=id;
    }
    public void setUserName(String name){
        this.userName=name;
    }
    public void setUserMail(String mail){
        this.userMail=mail;
    }
    public void setUserRole(Role role){
        this.role=role;
    }
    public void setUserState(String state){
        this.userState=state;
    }
    //Getters
    public int getUserId(){
        return this.userId;
    }
    public String getUserName(){
        return this.userName;
    }
    public String getUserMail(){
        return this.userMail;
    }
    public Role getUserRole(){
        return this.role;
    }
    public String getUserState(){
        return this.userState;
    }
}
