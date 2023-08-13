package br.com.senai.core.service;

import br.com.senai.core.dao.FactoryDao;
import br.com.senai.core.dao.postgres.DaoPostgressConexao;

public class ConnectionService {
	
	DaoPostgressConexao dao;
	
	public ConnectionService() {
		dao = FactoryDao.getInstance().getDaoConexao();
	}

}
