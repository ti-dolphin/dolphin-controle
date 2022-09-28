/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios.ponto;

import java.io.IOException;
import java.io.OutputStream;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;

/**
 *
 * @author Engenharia01
 */
public class SendJSON {

    private Util util = new Util();
    public static String ip;
    public static String port;    
    private String $return;
    static String met;
    private Data_to_session dts = new Data_to_session();  
    private HttpsURLConnection conn = null;
    
    public String RequestJSON(String metodo, JSONObject setJson) {    
      
        try {
            util.Ignore_SSL();
        } catch (Exception ex) {
            Logger.getLogger(SendJSON.class.getName()).log(Level.SEVERE, null, ex);
        }
        URL url = null;
        met = metodo;
        try {
            if(metodo == "login"){
                    url = new URL("https://" + ip + ":"+port+"/" + metodo + ".fcgi");           
                    //url = new URL("http://" + dts.ipGetText() + ":"+dts.portGetText()+"/" + metodo + ".fcgi");                       
            }else if(metodo != "login"){
                    url = new URL("https://" + ip+ ":"+port+"/" + metodo + ".fcgi?session=" +SessionResult.setToken());
                   // url = new URL("http://" + dts.ipGetText()+ ":"+dts.portGetText()+"/" + metodo + ".fcgi?session=" +SessionResult.setToken());        
            }     
            conn = util.OpenConnection(url);
            OutputStream os = conn.getOutputStream();
            os.write(setJson.toString().getBytes());
            if (conn.getResponseCode() != 200) {
                System.out.println("This method " + metodo + " returned HTTP Status Code: " + conn.getResponseCode());       
            } else {             
                $return =  util.ReadResult(conn);
            }      
            
        } catch (IOException | RuntimeException e) {
            System.out.println("Exception: " + e);        
        }
        return $return;
    }
}
