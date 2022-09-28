package model;

/**
 * 
 * @author guilherme.oliveira
 */
public class Funcao {
	private short codColigada;
	private String codigo;
	private String nome;
	
	public Funcao(){
		
	}
	
	public Funcao(short codColigada, String codigo, String nome) {
		this.codColigada = codColigada;
		this.codigo = codigo;
		this.nome = nome;
	}

	public short getCodColigada() {
		return codColigada;
	}

	public void setCodColigada(short codColigada) {
		this.codColigada = codColigada;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "C�digo da coligada: " + codColigada 
				+ "C�digo da fun��o: " + codigo
				+ "Nome: " + nome;
	}
	
}
