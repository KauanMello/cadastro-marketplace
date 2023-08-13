package br.com.senai.core.service;

import java.util.ArrayList;
import java.util.List;

import br.com.senai.core.dao.DaoCategoria;
import br.com.senai.core.dao.FactoryDao;
import br.com.senai.core.domain.Categoria;

public class CategoriaService {
	
	private DaoCategoria dao;
	
	public CategoriaService() {
		this.dao = FactoryDao.getInstance().getDaoCategoria(); 
	}
	
	public void salvar(Categoria categoria) {
		this.validar(categoria);
		boolean isJaInserido = categoria.getId() > 0;
		if (isJaInserido) {
			this.dao.alterar(categoria);
		}else {
			this.dao.inserir(categoria);
		}
	}
	
	public void removerPor(int id) {
		if(id > 0) {
			if (this.dao.listarCategoriasVinculadas(id).size() == 0) {
				this.dao.excluirPor(id);
			}else {
				throw new IllegalArgumentException("Essa categoria não pode ser removida. "
						+ "Pois contém um restaurante vinculado a mesma");
			}
		}else {
			throw new IllegalArgumentException("O id da categoria deve ser maior que zero");
		}
	}
	
	public Categoria buscarPor(int id) {
		if(id > 0) {
			Categoria categoriaEncontrada = this.dao.buscarPor(id);
			if (categoriaEncontrada == null) {
				throw new IllegalArgumentException("Não foi encontrada nenhuma categoria para o código informado");
			}
			return categoriaEncontrada;
		}else {
			throw new IllegalArgumentException("O id da categoria deve ser maior que zero");
		}
	}
	
	public List<Categoria> listarPor(String nome){
		
		if(nome.length() > 2 && !nome.isBlank()) {
			List<Categoria> categorias = new ArrayList<>();
			for (Categoria c : this.dao.listarPor(nome + "%")) {
				categorias.add(c);
			}
			
			if (categorias.size() > 0) {
				return categorias;
			}else {
				throw new IllegalArgumentException("Não foi encontrado nenhuma categoria com esse filtro");
			}
		}
		throw new IllegalArgumentException("O nome deve ser preenchido e deve conter ao menos 3 caracteres");
	}
	
	public List<Categoria> listarPor(int id){
		List<Categoria> categorias = new ArrayList<>();
		for (Categoria c : this.dao.listarCategoriasVinculadas(id)) {
			categorias.add(c);
		}
		return categorias;
	}
	
	public List<Categoria> listarTodos(){
		return dao.listarTodos();
	}
	
	private void validar(Categoria categoria) {
		if(categoria != null) {
			
			boolean isNomeInvalido = categoria.getNome().isBlank() 
					|| categoria.getNome().length() > 100;
					
			if (isNomeInvalido) {
				throw new IllegalArgumentException("O nome da categoria é obrigatório "
						+ "e não deve possuir mais de 100 caracteres");
			}
			
		}else {
			throw new NullPointerException("A categoria não pode ser nula");
		}
	}
	
}
