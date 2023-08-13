package br.com.senai.core.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ManagerDb {
	
	private static ManagerDb manager;
	
	private Connection conexao;
	
	private ManagerDb() {		
		try {
			Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
			this.conexao = DriverManager.getConnection(
					"jdbc:postgresql://containers-us-west-102.railway.app:5809/railway?currentSchema=senai", 
					"senai", "senai@db");
		}catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro de conexão "
					+ "com o banco de dados. Motivo: " + e.getMessage());
		}
	}
	
	public Connection getConexao() {
		return conexao;
	}
	
	public void configurarAutocommitDa(Connection conexao, boolean isHabilitado) {
		try {
			if (conexao != null) {
				conexao.setAutoCommit(isHabilitado);
			}
		}catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro na ativação do "
					+ "autocommit. Motivo: " + e.getMessage());
		}
	}
	
	public void fechar(PreparedStatement ps) {		
		try {
			if (ps != null) {
				ps.close();
			}
		}catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro no fechamento do "
					+ "PreparedStatement. Motivo: " + e.getMessage());
		}
	}
	
	public void fechar(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		}catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro no fechamento do "
					+ "ResultSet. Motivo: " + e.getMessage());
		}
	}	
	
	public static ManagerDb getInstance() {
		if (manager == null) {
			manager = new ManagerDb();
		}
		return manager;
	}
	
}
