/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.reservauna.controller;

import java.util.Stack;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *
 * @author User
 */
public class NavegationController {
    private final Scene mainScene;
    private static final Stack<Parent> historyStack = new Stack<>();
    private Parent currentWindow;
    
    
    public NavegationController(Scene mainScene){
        this.mainScene=mainScene;
        if(currentWindow==null){
            this.currentWindow=mainScene.getRoot();
        }
    }
    
    public void goingTo(Parent fxmlFile){
        if(currentWindow!=null){
            historyStack.push(currentWindow);
        }
        currentWindow=fxmlFile;
        mainScene.setRoot(fxmlFile);
    }
    
    public void goBackTo(){
        if(!historyStack.isEmpty()){
            Parent previousWindow = historyStack.pop();
            currentWindow = previousWindow;
            mainScene.setRoot(previousWindow);
        }else{
            System.out.println("Fin del historial.");
        }
    }

    public void cleanHistory() {
        historyStack.clear();
    }
}
