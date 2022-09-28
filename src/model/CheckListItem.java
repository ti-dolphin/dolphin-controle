/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author guilherme.oliveira
 */
public class CheckListItem {
    
    private int id;
    private String nome;
    private CheckListModelo checkListModelo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CheckListModelo getCheckListModelo() {
        return checkListModelo;
    }

    public void setCheckListModelo(CheckListModelo checkListModelo) {
        this.checkListModelo = checkListModelo;
    }

    
}
