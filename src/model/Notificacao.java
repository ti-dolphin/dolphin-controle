/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import model.apontamento.Apontamento;

/**
 *
 * @author ti
 */
public class Notificacao {
    private int id;
    private String descricao;
    private boolean lido;
    private LocalDateTime dataHora;

    public Notificacao() {
    }
    
    public Notificacao(String descricao) {
        this.descricao = descricao;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isLido() {
        return lido;
    }

    public void setLido(boolean lido) {
        this.lido = lido;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    @Override
    public String toString() {
        return "Notificacao{" + "id=" + id + ", descricao=" + descricao + ", lido=" + lido + ", dataHora=" + dataHora + '}';
    }
}
