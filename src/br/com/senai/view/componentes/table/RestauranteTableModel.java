package br.com.senai.view.componentes.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.senai.core.domain.Restaurante;

public class RestauranteTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;

	private final int QTDE_COLUNAS = 2;
	
	private List<Restaurante> restaurantes;
	
	public RestauranteTableModel(List<Restaurante> restaurantes) {
		this.restaurantes = restaurantes;
	}
	
	@Override
	public int getColumnCount() {
		return QTDE_COLUNAS;
	}
	
	public String getColumnName(int column) {
		if (column == 0) {
			return "ID";
		}else if (column == 1) {
			return "Nome";
		}
		throw new IllegalArgumentException("Indíce inválido");
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return restaurantes.get(rowIndex).getId();
		}else if (columnIndex == 1) {
			return restaurantes.get(rowIndex).getNome();
		}
		throw new IllegalArgumentException("Índice inválido");
	}
	
	@Override
	public int getRowCount() { 
		return restaurantes.size();
	}
	
	public Restaurante getPor(int rowIndex) {
		return restaurantes.get(rowIndex);
	}
	
	public void removerPor(int rowIndex) {
		this.restaurantes.remove(rowIndex);
	}
	
}
