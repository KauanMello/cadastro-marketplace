package br.com.senai.core.dao.postgres;

import br.com.senai.core.dao.ManagerDb;

public class DaoPostgressConexao {
	
	public DaoPostgressConexao() {
		ManagerDb.getInstance().getConexao();
	}

}
