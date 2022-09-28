/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author guilherme.oliveira
 */
public class Sistema {
    
    private int codSistema;
    private String versao;
    private LocalDateTime dataSincronizacao;

    public int getCodSistema() {
        return codSistema;
    }

    public void setCodSistema(int codSistema) {
        this.codSistema = codSistema;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public LocalDateTime getDataSincronizacao() {
        return dataSincronizacao;
    }

    public void setDataSincronizacao(LocalDateTime dataSincronizacao) {
        this.dataSincronizacao = dataSincronizacao;
    }
}
