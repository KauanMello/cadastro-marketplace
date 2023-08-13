package br.com.senai.core.domain;

import java.util.Objects;

public class Restaurante {

	private int id;
	
	private String nome;
	
	private String descricao;

	private Endereco endereco;
	
	private Categoria categoria;

	public Restaurante(String nome, String descricao,
			Endereco endereco, Categoria categoria) {
		this.nome = nome;
		this.descricao = descricao;
		this.endereco = endereco; 
		this.categoria = categoria;
	}

	public Restaurante(int id, String nome, String descricao,
			Endereco endereco, Categoria categoria) {
		this(nome, descricao, endereco, categoria);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Restaurante other = (Restaurante) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return nome.toString();
	}
	
}
