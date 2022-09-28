
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author guilherme.oliveira
 */
public class Patrimonio {
    
    private int id;
    private short codColigada;
    private String codPatrimonio;
    private String descricao;
    private String observacao;
    private CheckListModelo checkListModelo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public short getCodColigada() {
        return codColigada;
    }

    public void setCodColigada(short codColigada) {
        this.codColigada = codColigada;
    }

    public String getCodPatrimonio() {
        return codPatrimonio;
    }

    public void setCodPatrimonio(String codPatrimonio) {
        this.codPatrimonio = codPatrimonio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public CheckListModelo getCheckListModelo() {
        return checkListModelo;
    }

    public void setCheckListModelo(CheckListModelo checkListModelo) {
        this.checkListModelo = checkListModelo;
    }

}
