/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.apontamento;

import java.time.LocalDate;
import java.time.LocalDateTime;
import model.Funcionario;
import model.Situacao;
import model.os.CentroCusto;
import model.os.OrdemServico;
import model.os.Pessoa;

/**
 *
 * @author guilherme.oliveira
 */
public class Apontamento {

    private int codApont;
    private Funcionario funcionario;
    private LocalDate data;
    private StatusApont statusApont;
    private CentroCusto centroCusto;
    private OrdemServico ordemServico;
    private Pessoa lider;
    private Pessoa gerente;
    private String atividade;
    private Situacao situacao;
    private boolean comentado;
    private boolean integra;
    private double custoMaoDeObra;
    private int quantidadeDias;
    private double porcentagemDeDiasTrabalhados;
    private String modificadoPor;
    private boolean verificado;
    private boolean problema;
    private String motivo;
    private String justificativa;
    private int competencia;
    private boolean assiduidade;

    public Apontamento() {
    }

    public boolean isComentado() {
        return comentado;
    }

    public void setComentado(boolean comentado) {
        this.comentado = comentado;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public StatusApont getStatusApont() {
        return statusApont;
    }

    public void setStatusApont(StatusApont statusApont) {
        this.statusApont = statusApont;
    }

    public int getCodApont() {
        return codApont;
    }

    public void setCodApont(int codApont) {
        this.codApont = codApont;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public CentroCusto getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(CentroCusto centroCusto) {
        this.centroCusto = centroCusto;
    }

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }

    public Pessoa getLider() {
        return lider;
    }

    public void setLider(Pessoa lider) {
        this.lider = lider;
    }

    public Pessoa getGerente() {
        return gerente;
    }

    public void setGerente(Pessoa gerente) {
        this.gerente = gerente;
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public boolean isIntegra() {
        return integra;
    }

    public void setIntegra(boolean integra) {
        this.integra = integra;
    }

    public double getCustoMaoDeObra() {
        return custoMaoDeObra;
    }

    public void setCustoMaoDeObra(double custoMaoDeObra) {
        this.custoMaoDeObra = custoMaoDeObra;
    }

    public int getQuantidadeDias() {
        return quantidadeDias;
    }

    public void setQuantidadeDias(int quantidadeDias) {
        this.quantidadeDias = quantidadeDias;
    }

    public double getPorcentagemDeDiasTrabalhados() {
        return porcentagemDeDiasTrabalhados;
    }

    public void setPorcentagemDeDiasTrabalhados(double porcentagemDeDiasTrabalhados) {
        this.porcentagemDeDiasTrabalhados = porcentagemDeDiasTrabalhados;
    }

    public String getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(String modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    public boolean isProblema() {
        return problema;
    }

    public void setProblema(boolean problema) {
        this.problema = problema;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public int getCompetencia() {
        return competencia;
    }

    public void setCompetencia(int competencia) {
        this.competencia = competencia;
    }

    public boolean isAssiduidade() {
        return assiduidade;
    }

    public void setAssiduidade(boolean assiduidade) {
        this.assiduidade = assiduidade;
    }
}
