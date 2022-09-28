/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios.ponto;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Engenharia01
 */
public class SimpleConfigurationResult {
    
    public static String data;
    
    public void setText(String data){
          try {
            JSONObject json = new JSONObject(data); 
            this.data = json.toString(4);
          
        } catch (JSONException ex) {
            Logger.getLogger(LoadAFD.class.getName()).log(Level.SEVERE, null, ex);
           
        }
    }
    public static String getText(){
        return data;
    }
}
