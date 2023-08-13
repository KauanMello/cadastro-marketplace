package br.com.senai.core.dao;

import java.util.List;

import br.com.senai.core.domain.Categoria;

public interface DaoCategoria {
	
	public void inserir(Categoria categoria);
	
	public void alterar(Categoria categoria);
	
	public void excluirPor(int id);
	
	public Categoria buscarPor(int id);
	
	public List<Categoria> listarPor(String nome);
	
	public List<Categoria> listarTodos();
	
	public List<Categoria> listarCategoriasVinculadas(int id);
	
}
