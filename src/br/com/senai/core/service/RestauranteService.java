package br.com.senai.core.service;

import java.util.List;

import br.com.senai.core.dao.DaoRestaurante;
import br.com.senai.core.dao.FactoryDao;
import br.com.senai.core.domain.Categoria;
import br.com.senai.core.domain.Restaurante;

public class RestauranteService {
	
	private DaoRestaurante dao;
	
	public RestauranteService() {
		this.dao = FactoryDao.getInstance().getDaoRestaurante();
	}
	
	public void salvar(Restaurante restaurante) {
		this.validar(restaurante); 
		if(restaurante.getId() > 0) {
			this.dao.alterar(restaurante);
		}else {
			this.dao.inserir(restaurante);
		}
	}
	
	public void removerPor(int id) {
		if (id > 0) {
			if (this.dao.listarRestaurantesVinculados(id).size() == 0) {
				this.dao.excluirPor(id);
			}else {
				throw new IllegalArgumentException("O restaurante não pode ser removido pois está vinculado a um horário.");
			}
		}else {
			throw new IllegalArgumentException("O id do restaurante deve ser maior que 0");
		}
	}
	
	public List<Restaurante> listarTodos(){
		return this.dao.listarTodos();
	}
	
	public Restaurante buscarPor(int id) {
		if (id > 0) {
			Restaurante restauranteEncontrado = this.dao.buscarPor(id);
			if (restauranteEncontrado == null) {
				throw new IllegalArgumentException("Não foi encontrado nenhum restaurante para o id selecionado");
			}
			return restauranteEncontrado;
			
		}else {
			throw new IllegalArgumentException("O id do restaurante deve ser maior que 0");
		}
	}
	
	public List<Restaurante> listarPor(String nome, Categoria categoria){
		
		boolean isCategoriaInformada = categoria != null && categoria.getId() > 0;
		boolean isFiltroInformado = nome != null && !nome.isBlank();
		
		if (!isCategoriaInformada && !isFiltroInformado) {
			throw new IllegalArgumentException("Selecione ao menos um filtro para prosseguir");
		}
		
		String filtroNome = "";
		if (isCategoriaInformada) {
			filtroNome = nome + "%";
		}else {
			filtroNome = "%" + nome + "%";
		}
		List<Restaurante> restaurantes = this.dao.listarPor(filtroNome, categoria);
		if (restaurantes.size() <= 0) {
			throw new IllegalArgumentException("Não encontrado nenhum restaurante com os filtros informados");
		}
		
		return restaurantes;
		
	}
	
	private void validar(Restaurante restaurante) {
		
		if (restaurante == null) {
			throw new NullPointerException("O restaurante não pode ser nulo");
		}
		
		boolean isNomeInvalido = restaurante.getNome().isBlank() 
				|| restaurante.getNome().length() > 250;
				
		if (isNomeInvalido) {
			throw new IllegalArgumentException("O nome é obrigatório e deve conter no máximo "
					+ "250 caracteres");
		}
		
		if (restaurante.getCategoria() == null || restaurante.getCategoria().getId() == 0) {
			throw new IllegalArgumentException("A categoria é obrigatória");
		}
				
		boolean isDescricaoInvalida = restaurante.getDescricao().isBlank();
		
		if (isDescricaoInvalida) {
			throw new IllegalArgumentException("A descrição é obrigatória");
		}

		boolean isLogradouroInvalido = restaurante.getEndereco().getLogradouro().isBlank()
				|| restaurante.getEndereco().getLogradouro().length() > 200;
				
		if (isLogradouroInvalido) {
			throw new IllegalArgumentException("O logradouro é obrigatório e deve conter no máximo "
					+ "200 caracteres");
		}
		
		boolean isCidadeInvalida = restaurante.getEndereco().getCidade().isBlank()
				|| restaurante.getEndereco().getCidade().length() > 80;
				
		if (isCidadeInvalida) {
			throw new IllegalArgumentException("A cidade é obrigatória e deve conter no máximo "
					+ "80 caracteres");
		}
		
		boolean isBairroInvalido = restaurante.getEndereco().getBairro().isBlank()
				|| restaurante.getEndereco().getBairro().length() > 50;
				
		if (isBairroInvalido) {
			throw new IllegalArgumentException("O bairro é obrigatório e deve conter no máximo "
					+ "50 caracteres");
		}
	}

}
