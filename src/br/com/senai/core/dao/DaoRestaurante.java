package br.com.senai.core.dao;

import java.util.List;

import br.com.senai.core.domain.Categoria;
import br.com.senai.core.domain.Restaurante;

public interface DaoRestaurante {
	
	public void inserir(Restaurante restaurante);
	
	public void alterar(Restaurante restaurante);
	
	public void excluirPor(int id);
	
	public Restaurante buscarPor(int id);
	
	public List<Restaurante> listarPor(String nome, Categoria categoria);

	public List<Restaurante> listarTodos();
	
	public List<Restaurante> listarRestaurantesVinculados(int id);	
}
