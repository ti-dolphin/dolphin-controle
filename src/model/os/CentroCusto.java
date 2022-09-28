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
public class CentroCusto {
    private String codCusto;
    private String nome;
    private String campoLivre;
    private boolean ativo;
    private int responsavel;
    private String codReduzido;

    public CentroCusto() {
    }
    
    public String getCodCusto() {
        return codCusto;
    }

    public void setCodCusto(String codCusto) {
        this.codCusto = codCusto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCampoLivre() {
        return campoLivre;
    }

    public void setCampoLivre(String campoLivre) {
        this.campoLivre = campoLivre;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public int getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(int responsavel) {
        this.responsavel = responsavel;
    }

    public String getCodReduzido() {
        return codReduzido;
    }

    public void setCodReduzido(String codReduzido) {
        this.codReduzido = codReduzido;
    }

    @Override
    public String toString() {
        return nome;
    }
}
