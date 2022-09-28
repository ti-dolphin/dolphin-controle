/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author guilherme.oliveira
 */
public class LastException {
    /**
     * Percorrer a hierarquia da exception encontrada e retorna a ultima ramificação
     * que é a causa da exception
     * @param exception Exception a ser percorrida
     * @return Exception encontrada no final da ramificação
     */
    public Throwable findLastException(Throwable exception) {
        Throwable ee = exception.getCause();
        Throwable th = exception;
        while ((ee != null) && ((ee = ee.getCause()) != null)) {
            th = ee;
        }
        return th;
    }
}
