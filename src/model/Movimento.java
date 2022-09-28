/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import model.os.Pessoa;

/**
 *
 * @author guilherme.oliveira
 */
public class Movimento {

    private int id;
    private Pessoa responsavel;
    private LocalDeEstoque localDeEstoqueOrigem;
    private LocalDeEstoque localDeEstoqueDestino;
    private LocalDate dataEntrega;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pessoa getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Pessoa responsavel) {
        this.responsavel = responsavel;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public LocalDeEstoque getLocalDeEstoqueOrigem() {
        return localDeEstoqueOrigem;
    }

    public void setLocalDeEstoqueOrigem(LocalDeEstoque localDeEstoqueOrigem) {
        this.localDeEstoqueOrigem = localDeEstoqueOrigem;
    }

    public LocalDeEstoque getLocalDeEstoqueDestino() {
        return localDeEstoqueDestino;
    }

    public void setLocalDeEstoqueDestino(LocalDeEstoque localDeEstoqueDestino) {
        this.localDeEstoqueDestino = localDeEstoqueDestino;
    }

}
