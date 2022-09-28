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
public class MovimentoItemCheckList {
    
    private MovimentoItem movimentoItem;
    private CheckListModelo checkListModelo;
    private CheckListItem checkListItem;
    private boolean checado;
    private String problema;

    public MovimentoItem getMovimentoItem() {
        return movimentoItem;
    }

    public void setMovimentoItem(MovimentoItem movimentoItem) {
        this.movimentoItem = movimentoItem;
    }

    public CheckListModelo getCheckListModelo() {
        return checkListModelo;
    }

    public void setCheckListModelo(CheckListModelo checkListModelo) {
        this.checkListModelo = checkListModelo;
    }


    public CheckListItem getCheckListItem() {
        return checkListItem;
    }

    public void setCheckListItem(CheckListItem checkListItem) {
        this.checkListItem = checkListItem;
    }

    public boolean isChecado() {
        return checado;
    }

    public void setChecado(boolean checado) {
        this.checado = checado;
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }
    
}
