package br.com.senai.core.domain;

import java.util.Objects;

public class Categoria {

	private int id;
	
	private String nome;

	public Categoria(int id, String nome) {
		this(nome);
		this.id = id;
	}

	public Categoria(String nome) {
		this.nome = nome;
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
		Categoria other = (Categoria) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return  nome;
	}

}
