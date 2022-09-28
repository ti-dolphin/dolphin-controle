/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.apontamento;

/**
 *
 * @author guilherme.oliveira
 */
public class StatusApont {
    private String codStatusApont;
    private String descricao;

    public StatusApont() {
    }

    public StatusApont(String codStatusApont, String descricao) {
        this.codStatusApont = codStatusApont;
        this.descricao = descricao;
    }

    public String getCodStatusApont() {
        return codStatusApont;
    }

    public void setCodStatusApont(String codStatusApont) {
        this.codStatusApont = codStatusApont;
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
