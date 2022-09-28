/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ponto;

import java.time.LocalDate;

/**
 *
 * @author guilherme.oliveira
 */
public class RelogioPonto {
    private int codRep;
    private String nome;
    private String ip;
    private String patrimonio;
    private String numeroSerie;
    private boolean ativo;
    private LocalDate dataSinc;

    public int getCodRep() {
        return codRep;
    }

    public void setCodRep(int codRep) {
        this.codRep = codRep;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
    }
    
    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDate getDataSinc() {
        return dataSinc;
    }

    public void setDataSinc(LocalDate dataSinc) {
        this.dataSinc = dataSinc;
    }
    
}
