/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ponto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import model.Funcionario;

/**
 *
 * @author guilherme.oliveira
 */
public class Ponto {
    private int codPonto;
    private String nsr;
    private int tipo;
    private LocalDate data;
    private LocalTime hora;
    private String pis;
    private String crc;
    private String completo;
    private RelogioPonto relogioPonto;
    private Funcionario funcionario;

    public int getCodPonto() {
        return codPonto;
    }

    public void setCodPonto(int codPonto) {
        this.codPonto = codPonto;
    }

    public String getNsr() {
        return nsr;
    }

    public void setNsr(String nsr) {
        this.nsr = nsr;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getPis() {
        return pis;
    }

    public void setPis(String pis) {
        this.pis = pis;
    }

    public String getCrc() {
        return crc;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }

    public String getCompleto() {
        return completo;
    }

    public void setCompleto(String completo) {
        this.completo = completo;
    }

    public RelogioPonto getRelogioPonto() {
        return relogioPonto;
    }

    public void setRelogioPonto(RelogioPonto relogioPonto) {
        this.relogioPonto = relogioPonto;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    
    
}
