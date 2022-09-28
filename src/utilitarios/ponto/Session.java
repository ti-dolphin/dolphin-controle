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
public class Session {

    private SendJSON send = new SendJSON();
    private JSONObject parameters = new JSONObject();
    private Data_to_session dts;
    //Gerar o Token da sessão.
    public String Check_Login(String user, String password, String ip, String port){
        try {
            parameters.put("login", user);
            parameters.put("password", password);
        } catch (JSONException ex) {
            System.err.print("There was a problem while adding a JSON parameters " + ex);
        }
        SendJSON.ip = ip;
        SendJSON.port = port;
      /** this.dts.ipSetText(ip);
       this.dts.portSetText(port);
        **/
        
        return send.RequestJSON("login", parameters);
    }
    public String Session_is_valid() {
        return send.RequestJSON("session_is_valid", parameters);
    }
    public String Change_web_login(String user, String password){
        try {
            parameters.put("login", user);
            parameters.put("password", password);
        } catch (JSONException ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
        return send.RequestJSON("change_login", parameters);
    }
    public String Logout(){
        return send.RequestJSON("logout", parameters);
    }
}
