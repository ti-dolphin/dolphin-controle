package model;

/**
 *
 * @author guilherme.oliveira
 */
public class LocalDeEstoque {
    
    private String codLoc;
    private short codFilial;
    private short codColigada;
    private String nome;
    private boolean inativo;

    public String getCodLoc() {
        return codLoc;
    }

    public void setCodLoc(String codLoc) {
        this.codLoc = codLoc;
    }

    public short getCodFilial() {
        return codFilial;
    }

    public void setCodFilial(short codFilial) {
        this.codFilial = codFilial;
    }

    public short getCodColigada() {
        return codColigada;
    }

    public void setCodColigada(short codColigada) {
        this.codColigada = codColigada;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isInativo() {
        return inativo;
    }

    public void setInativo(boolean inativo) {
        this.inativo = inativo;
    }

    @Override
    public String toString() {
        return nome;
    }
}
