/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios.ponto;

/**
 *
 * @author Engenharia01
 */
public class Data_to_session {
    
    private static String ip;
    private static String port;
    private static String user;
    private static String password;
    
    
    public void ipSetText(String ip){
        this.ip=ip;
    }
    
    public static String ipGetText(){
        return ip;
    }
    
    public void portSetText(String port){
        this.port=ip;
    }
    
    public static String portGetText(){
        return port;
    }
    
    public void userSetText(String user){
        this.user=user;
    }
    
    public static String userGetText(){
        return user;
    }
    
    public void passwordSetText(String password){
        this.password=password;
    }
    
    public static String passwordGetText(){
        return ip;
    }
}
