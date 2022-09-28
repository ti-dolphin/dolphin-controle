package model;

import java.time.LocalDateTime;

/**
 *
 * @author guilherme.oliveira
 */
public class EpiFuncionario {

    private int codRegistro;
    private short codColigada;
    private String chapa;
    private String codEpi;
    private LocalDateTime dataRetirada;
    private LocalDateTime dataDevolucao;
    private String ca;
    private boolean emailEnviado;
    private Funcionario funcionario;
    private double latitude;
    private double longitude;
    private Epi epi;
    private int codTkt;
    private int tktEntrega;
    private int tktDevolucao;
    private Ticket ticket;
    private FichaEpi fichaEpi;
    private String createdBy;
    private String modifiedBy;
    private String motivo;
    private boolean descontar;
    private boolean descontado;
    private double preco;

    public EpiFuncionario() {
    
    }

    public EpiFuncionario(FichaEpi fichaEpi) {
        this.fichaEpi = fichaEpi;
    }
    
    public int getCodTkt() {
        return codTkt;
    }

    public void setCodTkt(int codTkt) {
        this.codTkt = codTkt;
    }
    
    public int getTktEntrega() {
        return tktEntrega;
    }

    public void setTktEntrega(int tktEntrega) {
        this.tktEntrega = tktEntrega;
    }

    public int getTktDevolucao() {
        return tktDevolucao;
    }

    public void setTktDevolucao(int tktDevolucao) {
        this.tktDevolucao = tktDevolucao;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getCodRegistro() {
        return codRegistro;
    }

    public void setCodRegistro(int codRegistro) {
        this.codRegistro = codRegistro;
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

    public String getCodEpi() {
        return codEpi;
    }

    public void setCodEpi(String codEpi) {
        this.codEpi = codEpi;
    }

    public LocalDateTime getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(LocalDateTime dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public LocalDateTime getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDateTime dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public boolean isEmailEnviado() {
        return emailEnviado;
    }

    public void setEmailEnviado(boolean emailEnviado) {
        this.emailEnviado = emailEnviado;
    }
    
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Epi getEpi() {
        return epi;
    }

    public void setEpi(Epi epi) {
        this.epi = epi;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public FichaEpi getFichaEpi() {
        return fichaEpi;
    }

    public void setFichaEpi(FichaEpi fichaEpi) {
        this.fichaEpi = fichaEpi;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean isDescontar() {
        return descontar;
    }

    public void setDescontar(boolean descontar) {
        this.descontar = descontar;
    }

    public boolean isDescontado() {
        return descontado;
    }

    public void setDescontado(boolean descontado) {
        this.descontado = descontado;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "\nCódigo do registro: " + codRegistro
                + "\nCódigo coligada: " + codColigada
                + "\nChapa: " + chapa + "Código de EPI: " + codEpi
                + "\nData da retirada: " + dataRetirada
                + "\nData da devolucao: " + dataDevolucao
                + "\nCA: " + ca
                + "\nEpi: " + epi.getCodEpi()
                + "\nTicket Ticket: " + ticket.getTkt()
                + "\nCreated By: " + createdBy
                + "\nModified By: " + modifiedBy;
    }
}
