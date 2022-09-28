/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.apontamento;

import java.time.LocalDateTime;
import model.os.OrdemServico;

/**
 *
 * @author guilherme.oliveira
 */
public class Comentario {
    
    private int codComentario;
    private String descricao;
    private LocalDateTime createdOn;
    private String createdBy;
    private Apontamento apontamento;
    private OrdemServico ordemServico;
    private String lider;
    private boolean emailEnviado;

    public int getCodComentario() {
        return codComentario;
    }

    public void setCodComentario(int codComentario) {
        this.codComentario = codComentario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Apontamento getApontamento() {
        return apontamento;
    }

    public void setApontamento(Apontamento apontamento) {
        this.apontamento = apontamento;
    }

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }

    public String getLider() {
        return lider;
    }

    public void setLider(String lider) {
        this.lider = lider;
    }

    public boolean isEmailEnviado() {
        return emailEnviado;
    }

    public void setEmailEnviado(boolean emailEnviado) {
        this.emailEnviado = emailEnviado;
    }
}
