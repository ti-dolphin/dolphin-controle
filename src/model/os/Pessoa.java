/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.os;

/**
 *
 * @author guilherme.oliveira
 */
public class Pessoa {
    private int codPessoa;
    private String nome;
    private String login;
    private String senha;
    private boolean solicitante;
    private boolean responsavel;
    private boolean lider;
    private boolean permLogin;
    private boolean permEpi;
    private boolean permAutenticacao;
    private boolean permOS;
    private boolean permTipo;
    private boolean permStatus;
    private boolean permApont;
    private boolean permStatusApont;
    private boolean permPessoas;
    private boolean permComentOs;
    private boolean permComentApont;
    private boolean permPonto;
    private boolean permVenda;
    private boolean permCadEpi;
    private boolean permCadCheckList;
    private boolean permDescontado;
    private boolean permControleRecesso;
    private boolean permGestaoPessoas;
    private boolean permCustoMO;
    private boolean permFerramentas;
    private boolean permProspeccao;
    private boolean ativo;
    private String email;
    private boolean permApontamentoPonto;
    private boolean permApontamentoPontoJustificativa;
    private boolean permBancoHoras;
    private boolean permFolga;

    public Pessoa() {
        this.codPessoa = 1;
    }
    
    public int getCodPessoa() {
        return codPessoa;
    }

    public void setCodPessoa(int codPessoa) {
        this.codPessoa = codPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isSolicitante() {
        return solicitante;
    }

    public void setSolicitante(boolean solicitante) {
        this.solicitante = solicitante;
    }

    public boolean isResponsavel() {
        return responsavel;
    }

    public void setResponsavel(boolean responsavel) {
        this.responsavel = responsavel;
    }

    public boolean isLider() {
        return lider;
    }

    public void setLider(boolean lider) {
        this.lider = lider;
    }

    public boolean isPermLogin() {
        return permLogin;
    }

    public void setPermLogin(boolean permLogin) {
        this.permLogin = permLogin;
    }

    public boolean isPermEpi() {
        return permEpi;
    }

    public void setPermEpi(boolean permEpi) {
        this.permEpi = permEpi;
    }

    public boolean isPermAutenticacao() {
        return permAutenticacao;
    }

    public void setPermAutenticacao(boolean permAutenticacao) {
        this.permAutenticacao = permAutenticacao;
    }

    public boolean isPermOS() {
        return permOS;
    }

    public void setPermOS(boolean permOS) {
        this.permOS = permOS;
    }

    public boolean isPermTipo() {
        return permTipo;
    }

    public void setPermTipo(boolean permTipo) {
        this.permTipo = permTipo;
    }

    public boolean isPermStatus() {
        return permStatus;
    }

    public void setPermStatus(boolean permStatus) {
        this.permStatus = permStatus;
    }

    public boolean isPermApont() {
        return permApont;
    }

    public void setPermApont(boolean permApont) {
        this.permApont = permApont;
    }

    public boolean isPermStatusApont() {
        return permStatusApont;
    }

    public void setPermStatusApont(boolean permStatusApont) {
        this.permStatusApont = permStatusApont;
    }

    public boolean isPermPessoas() {
        return permPessoas;
    }

    public void setPermPessoas(boolean permPessoas) {
        this.permPessoas = permPessoas;
    }

    public boolean isPermComentOs() {
        return permComentOs;
    }

    public void setPermComentOs(boolean permComentOs) {
        this.permComentOs = permComentOs;
    }

    public boolean isPermComentApont() {
        return permComentApont;
    }

    public void setPermComentApont(boolean permComentApont) {
        this.permComentApont = permComentApont;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPermPonto() {
        return permPonto;
    }

    public void setPermPonto(boolean permPonto) {
        this.permPonto = permPonto;
    }

    public boolean isPermVenda() {
        return permVenda;
    }

    public void setPermVenda(boolean permVenda) {
        this.permVenda = permVenda;
    }

    public boolean isPermCadEpi() {
        return permCadEpi;
    }

    public void setPermCadEpi(boolean permCadEpi) {
        this.permCadEpi = permCadEpi;
    }

    public boolean isPermCadCheckList() {
        return permCadCheckList;
    }

    public void setPermCadCheckList(boolean permCadCheckList) {
        this.permCadCheckList = permCadCheckList;
    }

    public boolean isPermDescontado() {
        return permDescontado;
    }

    public void setPermDescontado(boolean permDescontado) {
        this.permDescontado = permDescontado;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean isPermControleRecesso() {
        return permControleRecesso;
    }

    public void setPermControleRecesso(boolean permControleRecesso) {
        this.permControleRecesso = permControleRecesso;
    }

    public boolean isPermGestaoPessoas() {
        return permGestaoPessoas;
    }

    public void setPermGestaoPessoas(boolean permGestaoPessoas) {
        this.permGestaoPessoas = permGestaoPessoas;
    }

    public boolean isPermCustoMO() {
        return permCustoMO;
    }

    public void setPermCustoMO(boolean permCustoMO) {
        this.permCustoMO = permCustoMO;
    }

    public boolean isPermFerramentas() {
        return permFerramentas;
    }

    public void setPermFerramentas(boolean permFerramentas) {
        this.permFerramentas = permFerramentas;
    }

    public boolean isPermProspeccao() {
        return permProspeccao;
    }

    public void setPermProspeccao(boolean permProspeccao) {
        this.permProspeccao = permProspeccao;
    }

    public boolean isPermApontamentoPonto() {
        return permApontamentoPonto;
    }

    public void setPermApontamentoPonto(boolean permApontamentoPonto) {
        this.permApontamentoPonto = permApontamentoPonto;
    }

    public boolean isPermApontamentoPontoJustificativa() {
        return permApontamentoPontoJustificativa;
    }

    public void setPermApontamentoPontoJustificativa(boolean permApontamentoPontoMotivo) {
        this.permApontamentoPontoJustificativa = permApontamentoPontoMotivo;
    }

    public boolean isPermBancoHoras() {
        return permBancoHoras;
    }

    public void setPermBancoHoras(boolean permBancoHoras) {
        this.permBancoHoras = permBancoHoras;
    }
    
    public boolean isPermFolga() {
        return permFolga;
    }

    public void setPermFolga(boolean permFolga) {
        this.permFolga = permFolga;
    }
    
    @Override
    public String toString() {
        return nome;
    }
}
