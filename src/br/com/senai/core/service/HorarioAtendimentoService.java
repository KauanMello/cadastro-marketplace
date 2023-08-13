package br.com.senai.core.service;

import java.time.LocalTime;
import java.util.List;

import br.com.senai.core.dao.DaoHorarioAtendimento;
import br.com.senai.core.dao.DaoRestaurante;
import br.com.senai.core.dao.FactoryDao;
import br.com.senai.core.domain.DiaDaSemana;
import br.com.senai.core.domain.HorarioAtendimento;
import br.com.senai.core.domain.Restaurante;

public class HorarioAtendimentoService {

	private DaoHorarioAtendimento daoHorarioAtendimento;
	private DaoRestaurante daoRestaurante;
	
	public HorarioAtendimentoService() {
		this.daoHorarioAtendimento = FactoryDao.getInstance().getDaoHorarioAtendimento();
		this.daoRestaurante = FactoryDao.getInstance().getDaoRestaurante();
	}
	
	public void salvar(HorarioAtendimento horarioAtendimento) {
		this.validar(horarioAtendimento);
		if (horarioAtendimento.getId() > 0) {
			this.daoHorarioAtendimento.alterar(horarioAtendimento);
		}else {
			this.daoHorarioAtendimento.inserir(horarioAtendimento);
		}
	}
	
	public void removerPor(int id) {
		if (id > 0) {
			this.daoHorarioAtendimento.excluirPor(id);
		}else {
			throw new IllegalArgumentException("O id deve ser maior que 0");
		}
	}
	
	public HorarioAtendimento buscarPor(int id) { 
		if (id > 0) {
			HorarioAtendimento horarioAtendimentoEncontrado = this.daoHorarioAtendimento.buscarPor(id);
			if (horarioAtendimentoEncontrado == null) {
				throw new IllegalArgumentException("Não foi encontrado nenhum horario de atendimento com esse id"); 
			}else {
				return horarioAtendimentoEncontrado;
			}
		}else {
			throw new IllegalArgumentException("O id deve ser maior que 0");
		}
	}
	
	public HorarioAtendimento buscarUltimoHorarioCadastrado() {
		return this.daoHorarioAtendimento.buscarUltimoCadastrado();
	}
	
	public List<HorarioAtendimento> listarPor(Restaurante restaurante){
		return this.daoHorarioAtendimento.listarPor(restaurante);
	}
	
	public void validar(HorarioAtendimento horarioAtendimento) {
		if (horarioAtendimento == null) {
			throw new IllegalArgumentException("O horario de atendimento é obrigatório");
		}
		
		if (horarioAtendimento.getRestaurante() == null) {
			throw new IllegalArgumentException("O restaurante é obrigatório");
		}

		if (horarioAtendimento.getDiaDaSemana() == null) {
			throw new IllegalArgumentException("O dia da semana é obrigatório");
		}
		
		this.validarHorario(horarioAtendimento);
	
	}
	
	private void validarHorario(HorarioAtendimento horarioAtendimento) {
		
		LocalTime horarioAbertura = horarioAtendimento.getHoraAbertura();
		
		if (!(horarioAbertura != null && horarioAbertura.isAfter(LocalTime.MIN) && horarioAbertura.isBefore(LocalTime.MAX))) {
			throw new IllegalArgumentException("O horario de abertura é invalido");
		}
		
		LocalTime horarioFechamento = horarioAtendimento.getHoraFechamento();
		
		if (!(horarioFechamento != null && horarioFechamento.isAfter(LocalTime.MIN)	&& horarioFechamento.isBefore(LocalTime.MAX))) {
			throw new IllegalArgumentException("O horario de fechamento é invalido");
		} 

		if (horarioAbertura.isAfter(horarioFechamento)) {
			throw new IllegalArgumentException("O horário de fechamento não pode ser antes da abertura");
		
		}
		
		if (horarioFechamento.isBefore(horarioAbertura)) {
			throw new IllegalArgumentException("O horário de abertura não pode ser depois do fechamento");

		}
		
		if (horarioAbertura.equals(horarioFechamento)) {
			throw new IllegalArgumentException("Os horários não podem abrir e fechar no mesmo momento");
		}
		
		DiaDaSemana diaDaSemana = horarioAtendimento.getDiaDaSemana();
		Restaurante restaurante = this.daoRestaurante.buscarPor(horarioAtendimento.getRestaurante().getId());
		List<HorarioAtendimento> horariosEncontrados = this.daoHorarioAtendimento.listarPor(restaurante);
		
		for (HorarioAtendimento horario : horariosEncontrados) {
			if (diaDaSemana.equals(horario.getDiaDaSemana()) && horarioAtendimento.getId() != horario.getId()) {
				boolean isAberturaConflitante = (
						horarioAbertura.isBefore(horario.getHoraFechamento())
						&& horarioAbertura.isAfter(horario.getHoraAbertura()))
						|| horarioAbertura.equals(horario.getHoraFechamento());
				
				boolean isFechamentoConflitante = (
						horarioFechamento.isBefore(horario.getHoraFechamento()) 
						&& horarioFechamento.isAfter(horario.getHoraAbertura()))
						|| horarioFechamento.equals(horario.getHoraAbertura());
				
				if (isAberturaConflitante) {
					throw new IllegalArgumentException("O horário está contido (abertura e fechamento) no horário salvo");
				}
				
				if (isAberturaConflitante) {
					throw new IllegalArgumentException("Horario de abertura está contido no horário salvo");
				} 
				
				if (isFechamentoConflitante) {
					throw new IllegalArgumentException("Horario de fechamento está contido no horário salvo");
				}
				
				boolean isAntesDepois = horarioAbertura.isBefore(horario.getHoraAbertura())
						&& horario.getHoraAbertura().isBefore(horarioFechamento) 
						|| horarioAbertura.isAfter(horario.getHoraFechamento())
						&& horario.getHoraFechamento().isAfter(horarioAbertura);
				
				if (isAntesDepois) {
					throw new IllegalArgumentException("O horário está começando e terminando antes e depois do horário salvo");
				}
			}
		}
	}
}



