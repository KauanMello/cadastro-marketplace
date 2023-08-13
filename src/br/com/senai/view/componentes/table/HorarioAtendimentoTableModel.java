package br.com.senai.view.componentes.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.senai.core.domain.HorarioAtendimento;

public class HorarioAtendimentoTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	
	private final int QTDE_COLUNAS = 3;
	
	private List<HorarioAtendimento> horariosAtendimento; 
	
	public HorarioAtendimentoTableModel(List<HorarioAtendimento> horariosAtendimento) {
		this.horariosAtendimento = horariosAtendimento;
	}

	@Override
	public int getRowCount() {
		return horariosAtendimento.size();
	}

	@Override
	public int getColumnCount() {
		return QTDE_COLUNAS;
	}
	
	public String getColumnName(int column) {
		if (column == 0) {
			return "Dia da Semana";
		}else if (column == 1) {
			return "Abertura";
		}else if (column == 2) {
			return "Fechamento";
		}
		throw new IllegalArgumentException("Indíce inválido");
	}
	

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return this.horariosAtendimento.get(rowIndex).getDiaDaSemana();
		}else if (columnIndex == 1) {
			return this.horariosAtendimento.get(rowIndex).getHoraAbertura();
		}else if(columnIndex == 2){
			return this.horariosAtendimento.get(rowIndex).getHoraFechamento();
		}
		throw new IllegalArgumentException("Indice inválido");
	}
	
	public HorarioAtendimento getPor(int rowIndex) {
		if (rowIndex >= horariosAtendimento.size()) {
			throw new IndexOutOfBoundsException("O índice é inválido");
		}else {
			return this.horariosAtendimento.get(rowIndex);
		}
	}
	
	public void removerPor(int rowIndex) {
		this.horariosAtendimento.remove(rowIndex);
	}

}
