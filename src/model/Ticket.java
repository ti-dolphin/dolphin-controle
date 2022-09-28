/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author guilherme.oliveira
 */
public class Ticket {
    private int tkt;
    private LocalDateTime dataEnvio;

    public Ticket() {
        this.dataEnvio = LocalDateTime.now();
    }

    public Ticket(int tkt, LocalDateTime dataEnvio) {
        this.tkt = tkt;
        this.dataEnvio = dataEnvio;
    }

    public int getTkt() {
        return tkt;
    }

    public void setTkt(int tkt) {
        this.tkt = tkt;
    }
   
    
    
    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }    

    @Override
    public String toString() {
        return "TKTENTREGA: " + tkt
                + "\nDATAENVIO: " + dataEnvio;
    }    
}
