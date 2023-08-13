package br.com.senai.core.dao;

import br.com.senai.core.dao.postgres.DaoPostgresCategoria;
import br.com.senai.core.dao.postgres.DaoPostgresHorarioAtendimento;
import br.com.senai.core.dao.postgres.DaoPostgresRestaurante;
import br.com.senai.core.dao.postgres.DaoPostgressConexao;

public class FactoryDao {
	
	private static FactoryDao instance;
	
	private FactoryDao() {}
	
	public DaoCategoria getDaoCategoria() { 
		return new DaoPostgresCategoria();
	} 
	
	public DaoRestaurante getDaoRestaurante() {
		return new DaoPostgresRestaurante();	
	}
	
	public DaoHorarioAtendimento getDaoHorarioAtendimento() {
		return new DaoPostgresHorarioAtendimento();
	}
	
	public DaoPostgressConexao getDaoConexao() {
		return new DaoPostgressConexao();
	}
	
	public static FactoryDao getInstance() {
		if(instance == null) {
			instance = new FactoryDao();		
		}
		return instance;
	}
}
