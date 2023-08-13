package br.com.senai.core.domain;

import java.time.LocalTime;
import java.util.Objects;

public class HorarioAtendimento {
	
	private int id;
	
	private DiaDaSemana diaDaSemana;
	
	private LocalTime horaAbertura;
	
	private LocalTime horaFechamento;
	
	private Restaurante restaurante;

	public HorarioAtendimento(DiaDaSemana diaDaSemana, LocalTime horaAbertura, LocalTime horaFechamento, Restaurante restaurante) {
		this.diaDaSemana = diaDaSemana;
		this.horaAbertura = horaAbertura;
		this.horaFechamento = horaFechamento;
		this.restaurante = restaurante;
	}

	public HorarioAtendimento(int id, DiaDaSemana diaDaSemana, LocalTime horaAbertura, LocalTime horaFechamento,
			Restaurante restaurante) {
		this(diaDaSemana, horaAbertura, horaFechamento, restaurante);
		this.id = id;
	}

	public DiaDaSemana getDiaDaSemana() {
		return diaDaSemana;
	}

	public void setDiaDaSemana(DiaDaSemana diaDaSemana) {
		this.diaDaSemana = diaDaSemana;
	}

	public LocalTime getHoraAbertura() {
		return horaAbertura;
	}

	public void setHoraAbertura(LocalTime horaAbertura) {
		this.horaAbertura = horaAbertura;
	}

	public LocalTime getHoraFechamento() {
		return horaFechamento;
	}

	public void setHoraFechamento(LocalTime horaFechamento) {
		this.horaFechamento = horaFechamento;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HorarioAtendimento other = (HorarioAtendimento) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return " id " + id 
				+ "\n diaDaSemana " + diaDaSemana 
				+ "\n abertura " + horaAbertura
				+ "\n fechamento " + horaFechamento
				+ "\n";
	}
		
}
