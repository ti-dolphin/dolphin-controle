package model.epi;

/**
 *
 * @author guilherme.oliveira
 */
public class Epi {

    private String codEpi;
    private String nome;
    private String descricao;
    private boolean inativa;
    private int periodicidade;
    private double preco;
    

    public Epi() {
    }

    public Epi(String codEpi, String nome, String descricao, boolean inativa) {
        this.codEpi = codEpi;
        this.nome = nome;
        this.descricao = descricao;
        this.inativa = inativa;
    }

    public String getCodEpi() {
        return codEpi;
    }

    public void setCodEpi(String codEpi) {
        this.codEpi = codEpi;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isInativa() {
        return inativa;
    }

    public void setInativa(boolean inativa) {
        this.inativa = inativa;
    }

    public int getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(int periodicidade) {
        this.periodicidade = periodicidade;
    }
    
    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Código EPI: " + codEpi
                + "\nNome: " + nome
                + "\nDescrição: " + descricao
                + "\nNativa: " + inativa;
    }

}
