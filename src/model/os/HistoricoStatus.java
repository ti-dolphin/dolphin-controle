/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.os;

import java.time.LocalDateTime;
import model.os.OrdemServico;
import model.os.Pessoa;
import model.os.Status;

/**
 *
 * @author guilherme.oliveira
 */
public class HistoricoStatus {
    
    private int codHistorico;
    private OrdemServico ordemServico;
    private Status statusOld;
    private Status statusNew;
    private LocalDateTime data;
    private Pessoa usuario;

    public int getCodHistorico() {
        return codHistorico;
    }

    public void setCodHistorico(int codHistorico) {
        this.codHistorico = codHistorico;
    }

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }

    public Status getStatusOld() {
        return statusOld;
    }

    public void setStatusOld(Status statusOld) {
        this.statusOld = statusOld;
    }
    
    public Status getStatusNew() {
        return statusNew;
    }

    public void setStatusNew(Status statusNew) {
        this.statusNew = statusNew;
    }
    
    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Pessoa getUsuario() {
        return usuario;
    }

    public void setUsuario(Pessoa usuario) {
        this.usuario = usuario;
    }

}
