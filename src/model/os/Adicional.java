/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.os;

/**
 *
 * @author guilherme.oliveira
 */
public class Adicional {
    
    private int id;
    private int numero;
    private Projeto projeto;

    public Adicional() {
    }
    
    public Adicional(int numero, Projeto projeto) {
        this.numero = numero;
        this.projeto = projeto;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    @Override
    public String toString() {
        return String.valueOf(numero);
    }
}
