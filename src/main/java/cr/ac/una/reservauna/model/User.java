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
    private UserState userState;
    private String password;
    
    public User(int id,String name,String mail,Role role,UserState state, String password){
        this.userId=id;
        this.userName=name;
        this.userMail=mail;
        this.role=role;
        this.userState=state;
        this.password=password;
    }
    
    public User(String name,String mail,Role role,UserState state, String password){
        this.userName=name;
        this.userMail=mail;
        this.role=role;
        this.userState=state;
        this.password=password;
    }
    
    public User(Role role){
        this.role=role;
    }
    
    public User(){
        
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
    public void setUserState(UserState state){
        this.userState=state;
    }
    public void setUserPassword(String password){
        this.password=password;
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
    public UserState getUserState(){
        return this.userState;
    }
    public String getUserPassword(){
        return this.password;
    }
    @Override
    public String toString(){
        return userName;
    }
}
