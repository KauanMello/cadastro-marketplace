package br.com.senai.core.domain;

public class Endereco {
	
	private String cidade;
	
	private String logradouro;
	
	private String bairro;
	
	private String complemento;

	public Endereco(String cidade, String logradouro,
			String bairro, String complemento) {
		this.cidade = cidade;
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.complemento = complemento;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	@Override
	public String toString() {
		return "Endereco [cidade=" + cidade + ", logradouro=" + logradouro + ", bairro=" + bairro + ", complemento="
				+ complemento + "]";
	}
	
	
	
}
