package br.com.senai.core.dao;

import java.util.List;

import br.com.senai.core.domain.HorarioAtendimento;
import br.com.senai.core.domain.Restaurante;

public interface DaoHorarioAtendimento {
	
	public void inserir(HorarioAtendimento horarioAtendimento);
	
	public void alterar(HorarioAtendimento horarioAtendimento);
	
	public void excluirPor(int id);
	
	public HorarioAtendimento buscarPor(int id);
	
	public List<HorarioAtendimento> listarPor(Restaurante Restaurante);
	
	public HorarioAtendimento buscarUltimoCadastrado();
	
}
