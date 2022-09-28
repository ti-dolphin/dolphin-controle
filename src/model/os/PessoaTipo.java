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
public class PessoaTipo {
    
    private Pessoa pessoa;
    private TipoOs tipo;

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public TipoOs getTipo() {
        return tipo;
    }

    public void setTipo(TipoOs tipo) {
        this.tipo = tipo;
    }
}
