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
public class LoadAFD {

    private static StringBuilder data;
   
    
    public void setText(StringBuilder data) {
            this.data = data;      
    }

    public static StringBuilder getText() {
        return data;
    }
}
