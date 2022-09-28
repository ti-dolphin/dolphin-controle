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
public class TipoOs {
    private int codTipoOs;
    private String descricao;
    private Pessoa donoDoTipo;

    public TipoOs() {
    }

    public TipoOs(int codTipoOs, String descricao, Pessoa donoDoTipo) {
        this.codTipoOs = codTipoOs;
        this.descricao = descricao;
    }

    public int getCodTipoOs() {
        return codTipoOs;
    }

    public void setCodTipoOs(int codTipoOs) {
        this.codTipoOs = codTipoOs;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
