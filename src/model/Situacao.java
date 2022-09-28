package model;

public class Situacao {
	private char codSituacao;
	private String descricao;
	
	public  Situacao() {
	}
	
	public Situacao(char codSituacao, String descricao){
		this.codSituacao = codSituacao;
		this.descricao = descricao;
	}

	public char getCodSituacao() {
		return codSituacao;
	}

	public void setCodSituacao(char codSituacao) {
		this.codSituacao = codSituacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "Código Situação: " + codSituacao + 
				"\nDescrição: " + descricao;
	}
	
	
	
}
