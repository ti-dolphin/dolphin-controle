/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios.ponto;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Engenharia01
 */
public class AdvancedConfigurations {

    private SendJSON send = new SendJSON();
    private JSONObject parameters = new JSONObject();
    private final JSONObject parameters_aux = new JSONObject();

    public String Load_Logs(){
        try {
//          initial_date:{day: 21, month: 10, year: 2018}
            parameters_aux.put("day", LocalDateTime.now().getDayOfMonth());
            parameters_aux.put("month", LocalDateTime.now().getMonthValue());
            parameters_aux.put("year", LocalDateTime.now().getYear());
            parameters.put("initial_date", parameters_aux);
        } catch (JSONException ex) {
            Logger.getLogger(AdvancedConfigurations.class.getName()).log(Level.SEVERE, null, ex);
        }
       return send.RequestJSON("get_afd", parameters);
    }
    public String Get_System_Information(){
        return send.RequestJSON("get_system_information", parameters);
    }
    public String RemoveAdmins(){
        return send.RequestJSON("remove_admins", parameters);
    }
}
