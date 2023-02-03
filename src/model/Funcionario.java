package model;

import java.time.LocalDate;

/**
 *
 * @author guilherme.oliveira
 */
public class Funcionario {

    private short codColigada;
    private String chapa;
    private String nome;
    private LocalDate dataAdmissao;
    private short codFilial;
    private String cpf;
    private byte[] finger1;
    private byte[] finger2;
    private byte[] finger3;
    private byte[] finger4;
    private byte[] finger5;
    private byte[] finger6;
    private int senha;
    private String email;
    private Situacao situacao;
    private Funcao funcao;
    private double bancoHoras;
    
    public Funcionario() {
    }

    public Funcionario(short codColigada, String chapa, String nome, 
            LocalDate dataAdmissao, short codFilial, String cpf, byte[] finger1, 
            byte[] finger2, byte[] finger3, byte[] finger4, byte[] finger5, 
            byte[] finger6, int senha, String email, Situacao situacao, 
            Funcao funcao) {
        this.codColigada = codColigada;
        this.chapa = chapa;
        this.nome = nome;
        this.dataAdmissao = dataAdmissao;
        this.codFilial = codFilial;
        this.cpf = cpf;
        this.finger1 = finger1;
        this.finger2 = finger2;
        this.finger3 = finger3;
        this.finger4 = finger4;
        this.finger5 = finger5;
        this.finger6 = finger6;
        this.senha = senha;
        this.email = email;
        this.situacao = situacao;
        this.funcao = funcao;
    }

    public short getCodColigada() {
        return codColigada;
    }

    public void setCodColigada(short codColigada) {
        this.codColigada = codColigada;
    }

    public String getChapa() {
        return chapa;
    }

    public void setChapa(String chapa) {
        this.chapa = chapa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public short getCodFilial() {
        return codFilial;
    }

    public void setCodFilial(short codFilial) {
        this.codFilial = codFilial;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public byte[] getFinger1() {
        return finger1;
    }

    public void setFinger1(byte[] finger1) {
        this.finger1 = finger1;
    }

    public byte[] getFinger2() {
        return finger2;
    }

    public void setFinger2(byte[] finger2) {
        this.finger2 = finger2;
    }

    public byte[] getFinger3() {
        return finger3;
    }

    public void setFinger3(byte[] finger3) {
        this.finger3 = finger3;
    }

    public byte[] getFinger4() {
        return finger4;
    }

    public void setFinger4(byte[] finger4) {
        this.finger4 = finger4;
    }

    public byte[] getFinger5() {
        return finger5;
    }

    public void setFinger5(byte[] finger5) {
        this.finger5 = finger5;
    }

    public byte[] getFinger6() {
        return finger6;
    }

    public void setFinger6(byte[] finger6) {
        this.finger6 = finger6;
    }

    public int getSenha() {
        return senha;
    }

    public void setSenha(int senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public double getBancoHoras() {
        return bancoHoras;
    }

    public void setBancoHoras(double bancoHoras) {
        this.bancoHoras = bancoHoras;
    }

    @Override
    public String toString() {
        return nome;
    }
    
}
