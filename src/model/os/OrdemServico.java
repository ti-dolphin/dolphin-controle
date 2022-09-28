/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.os;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import model.Contato;
import model.Criterio;
import model.Seguidor;
import model.apontamento.Apontamento;
import model.apontamento.Comentario;

/**
 *
 * @author guilherme.oliveira
 */
public class OrdemServico {

    private int codOs;
    private TipoOs tipoOs;
    private CentroCusto centroCusto;
    private String obra;
    private LocalDate dataSolicitacao;
    private LocalDate dataNecessidade;
    private LocalDate dataInicio;
    private LocalDate dataPrevEntrega;
    private LocalDate dataEntrega;
    private Status status;
    private String nome;
    private int prioridade;
    private Pessoa solicitante;
    private Pessoa responsavel;
    private Pessoa gerente;
    private Cliente cliente;
    private Venda venda;
    private Segmento segmento;
    private Cidade cidade;
    private ArrayList<Seguidor> seguidores;
    private ArrayList<Apontamento> apontamentos;
    private ArrayList<Comentario> comentarios;
    private Projeto projeto;
    private Adicional adicional;
    private LocalDate dataInteracao;
    private ArrayList<Criterio> criterios;
    private ArrayList<Contato> contatos;
    private boolean principal;
    private MotivoPerda motivo;
    private String observacoes;
    private LocalDate hoje = LocalDate.now();
    private String siglaInicioFechamento;

    public OrdemServico() {
        this.criterios = new ArrayList<>();
    }

    public int getCodOs() {
        return codOs;
    }

    public void setCodOs(int codOs) {
        this.codOs = codOs;
    }

    public TipoOs getTipoOs() {
        return tipoOs;
    }

    public void setTipoOs(TipoOs tipoOs) {
        this.tipoOs = tipoOs;
    }

    public CentroCusto getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(CentroCusto centroCusto) {
        this.centroCusto = centroCusto;
    }

    public String getObra() {
        return obra;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public LocalDate getDataNecessidade() {
        return dataNecessidade;
    }

    public void setDataNecessidade(LocalDate dataNecessidade) {
        this.dataNecessidade = dataNecessidade;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataPrevEntrega() {
        return dataPrevEntrega;
    }

    public void setDataPrevEntrega(LocalDate dataPrevEntrega) {
        this.dataPrevEntrega = dataPrevEntrega;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public Pessoa getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Pessoa solicitante) {
        this.solicitante = solicitante;
    }

    public Pessoa getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Pessoa responsavel) {
        this.responsavel = responsavel;
    }

    public Pessoa getGerente() {
        return gerente;
    }

    public void setGerente(Pessoa gerente) {
        this.gerente = gerente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Segmento getSegmento() {
        return segmento;
    }

    public void setSegmento(Segmento segmento) {
        this.segmento = segmento;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public ArrayList<Seguidor> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(ArrayList<Seguidor> seguidores) {
        this.seguidores = seguidores;
    }

    public ArrayList<Apontamento> getApontamentos() {
        return apontamentos;
    }

    public void setApontamentos(ArrayList<Apontamento> apontamentos) {
        this.apontamentos = apontamentos;
    }

    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public Adicional getAdicional() {
        return adicional;
    }

    public void setAdicional(Adicional adicional) {
        this.adicional = adicional;
    }

    public LocalDate getDataInteracao() {
        return dataInteracao;
    }

    public void setDataInteracao(LocalDate dataInteracao) {
        this.dataInteracao = dataInteracao;
    }

    public ArrayList<Criterio> getCriterios() {
        return criterios;
    }

    public void setCriterios(ArrayList<Criterio> criterios) {
        this.criterios = criterios;
    }

    public ArrayList<Contato> getContatos() {
        return contatos;
    }

    public void setContatos(ArrayList<Contato> contatos) {
        this.contatos = contatos;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public MotivoPerda getMotivo() {
        return motivo;
    }

    public void setMotivo(MotivoPerda motivo) {
        this.motivo = motivo;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public boolean dataInteracaoEmDia() {
        
        return dataInteracao.isAfter(hoje);
    }

    public String getSiglaInicioFechamento() {
        return siglaInicioFechamento;
    }

    public void setSiglaInicioFechamento(String siglaInicioFechamento) {
        this.siglaInicioFechamento = siglaInicioFechamento;
    }
    
    public boolean dataInteracaoAVencer() {
        return hoje.equals(dataInteracao.minus(3, ChronoUnit.DAYS)) 
                        || hoje.equals(dataInteracao.minus(2, ChronoUnit.DAYS)) 
                        || hoje.equals(dataInteracao.minus(1, ChronoUnit.DAYS))
                        || hoje.equals(dataInteracao);
    }
    
    public boolean dataInteracaoVencida() {
        return hoje.isAfter(dataInteracao);
    }
}
