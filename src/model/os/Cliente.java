/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.os;

import java.time.LocalDate;
import java.util.List;
import model.ClienteComentario;

/**
 *
 * @author guilherme.oliveira
 */
public class Cliente {
    private int codColigada;
    private String codCliente;
    private String nomeFantasia;
    private String nome;
    private LocalDate dataInteracao;
    private boolean prospectar;
    private Classificacao classificacao;
    private List<ClienteComentario> comentarios;
    private boolean interacaoMsg;
    private boolean interacaoFone;
    private boolean interacaoReuniao;
    private Pessoa responsavel;
    private String cnpj;

    public int getCodColigada() {
        return codColigada;
    }

    public void setCodColigada(int codColigada) {
        this.codColigada = codColigada;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataInteracao() {
        return dataInteracao;
    }

    public void setDataInteracao(LocalDate dataInteracao) {
        this.dataInteracao = dataInteracao;
    }

    public boolean isProspectar() {
        return prospectar;
    }

    public void setProspectar(boolean prospectar) {
        this.prospectar = prospectar;
    }
    
    public Classificacao getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(Classificacao classificacao) {
        this.classificacao = classificacao;
    }

    public List<ClienteComentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ClienteComentario> comentarios) {
        this.comentarios = comentarios;
    }

    public boolean isInteracaoMsg() {
        return interacaoMsg;
    }

    public void setInteracaoMsg(boolean interacaoMsg) {
        this.interacaoMsg = interacaoMsg;
    }

    public boolean isInteracaoFone() {
        return interacaoFone;
    }

    public void setInteracaoFone(boolean interacaoFone) {
        this.interacaoFone = interacaoFone;
    }

    public boolean isInteracaoReuniao() {
        return interacaoReuniao;
    }

    public void setInteracaoReuniao(boolean interacaoReuniao) {
        this.interacaoReuniao = interacaoReuniao;
    }

    public Pessoa getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Pessoa responsavel) {
        this.responsavel = responsavel;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    @Override
    public String toString() {
        return nomeFantasia;
    }
}
