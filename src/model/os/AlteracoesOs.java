/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.os;

import java.time.LocalDateTime;

/**
 *
 * @author guilherme.oliveira
 */
public class AlteracoesOs {
    
    private int codAlteracoesOs;
    private OrdemServico ordemServico;
    private String descricao;
    private LocalDateTime data;
    private String usuario;
    
    public int getCodAlteracoesOs() {
        return codAlteracoesOs;
    }

    public void setCodAlteracoesOs(int codAlteracoesOs) {
        this.codAlteracoesOs = codAlteracoesOs;
    }

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
