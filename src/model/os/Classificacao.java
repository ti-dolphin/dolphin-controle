/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.os;

/**
 *
 * @author ti
 */
public class Classificacao {
    
    private int id;
    private char abreviacao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getAbreviacao() {
        return abreviacao;
    }

    public void setAbreviacao(char abreviacao) {
        this.abreviacao = abreviacao;
    }

    @Override
    public String toString() {
        return String.valueOf(abreviacao);
    }
}
