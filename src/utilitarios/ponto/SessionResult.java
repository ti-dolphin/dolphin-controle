/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios.ponto;

public class SessionResult {
    private static String session,met;     
    public static String getToken(String sess){
        String gt = sess;
        gt = gt.substring(12, 37);
        session = gt.substring(0, gt.length() - 1);
        return session;
    }
    public static String setToken(){
        return session;
    }
}
