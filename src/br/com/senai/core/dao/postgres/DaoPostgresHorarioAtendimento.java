package br.com.senai.core.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import br.com.senai.core.dao.DaoHorarioAtendimento;
import br.com.senai.core.dao.ManagerDb;
import br.com.senai.core.domain.Categoria;
import br.com.senai.core.domain.DiaDaSemana;
import br.com.senai.core.domain.Endereco;
import br.com.senai.core.domain.HorarioAtendimento;
import br.com.senai.core.domain.Restaurante;

public class DaoPostgresHorarioAtendimento implements DaoHorarioAtendimento{
	
	
	private final String INSERT = "INSERT INTO horarios_atendimento("
			+ "dia_semana, hora_abertura, hora_fechamento, id_restaurante) "
			+ "VALUES(?, ?, ?, ?);";
	
	private final String UPDATE = "UPDATE horarios_atendimento SET "
			+ "dia_semana = ?, hora_abertura = ?, hora_fechamento = ?, id_restaurante = ? "
			+ "WHERE id = ?";
	
	private final String DELETE = "DELETE FROM horarios_atendimento WHERE id = ?";
	
	private final String CONSULTA = "SELECT h.id id, "
			+ "h.dia_semana dia_semana, "
			+ "h.hora_abertura abertura, "
			+ "h.hora_fechamento fechamento, "
			+ "r.id id_restaurante, "
			+ "r.nome nome_restaurante, "
			+ "r.descricao descricao_restaurante, "
			+ "r.cidade cidade_restaurante, "
			+ "r.logradouro logradouro_restaurante, "
			+ "r.bairro bairro_restaurante, "
			+ "r.complemento complemento_restaurante, "
			+ "c.id id_categoria,"
			+ "c.nome nome_categoria "
			+ "FROM horarios_atendimento h	INNER JOIN restaurantes r ON h.id_restaurante = r.id "
			+ "INNER JOIN categorias c ON r.id_categoria = c.id ";
	
	private final String FIND_BY_ID = CONSULTA + "WHERE h.id = ?";
	
	private Connection conexao;
	
	public DaoPostgresHorarioAtendimento() {
		this.conexao = ManagerDb.getInstance().getConexao();
	}

	@Override
	public void inserir(HorarioAtendimento horarioAtendimento) {
		PreparedStatement ps = null;
		try {
			ps = conexao.prepareStatement(INSERT);
			ps.setString(1, horarioAtendimento.getDiaDaSemana().toString());
			ps.setTime(2, Time.valueOf(horarioAtendimento.getHoraAbertura()));
			ps.setTime(3, Time.valueOf(horarioAtendimento.getHoraFechamento()));
			ps.setInt(4, horarioAtendimento.getRestaurante().getId());
			ps.execute();
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao inserir horario de atendimento. "
					+ "Motivo: " + e.getMessage());
		}finally {
			ManagerDb.getInstance().fechar(ps);
		}
	}

	@Override
	public void alterar(HorarioAtendimento horarioAtendimento) {
		PreparedStatement ps = null;
		try {
			ManagerDb.getInstance().configurarAutocommitDa(conexao, false);
			ps = conexao.prepareStatement(UPDATE);
			ps.setString(1, horarioAtendimento.getDiaDaSemana().toString());
			ps.setTime(2, Time.valueOf(horarioAtendimento.getHoraAbertura()));
			ps.setTime(3, Time.valueOf(horarioAtendimento.getHoraFechamento()));
			ps.setInt(4, horarioAtendimento.getRestaurante().getId());
			ps.setInt(5, horarioAtendimento.getId());
			
			boolean isAlteracaoOK = ps.executeUpdate() == 1;
			if (isAlteracaoOK) {
				this.conexao.commit();
			}else {
				this.conexao.rollback();
			}
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao alterar horario de atendimento. "
					+ "Motivo: " + e.getMessage());
		}finally {
			ManagerDb.getInstance().configurarAutocommitDa(conexao, true);
			ManagerDb.getInstance().fechar(ps);
		}
		
	}

	@Override
	public void excluirPor(int id) {
		PreparedStatement ps = null;
		try {
			ManagerDb.getInstance().configurarAutocommitDa(conexao, false);
			ps = conexao.prepareStatement(DELETE);
			ps.setInt(1, id);
			
			boolean isAlteracaoOK = ps.executeUpdate() == 1;
			if (isAlteracaoOK) {
				this.conexao.commit();
			}else {
				this.conexao.rollback();
			}
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao excluir horario de atendimento. "
					+ "Motivo: " + e.getMessage());
		}finally {
			ManagerDb.getInstance().configurarAutocommitDa(conexao, true);
			ManagerDb.getInstance().fechar(ps);
		}
	}

	@Override
	public HorarioAtendimento buscarPor(int id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conexao.prepareStatement(FIND_BY_ID);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				return extrairDo(rs);
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao buscar horario de atendimento por id. "
					+ "Motivo: " + e.getMessage());
		}finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
		}
	}
	
	@Override
	public HorarioAtendimento buscarUltimoCadastrado() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conexao.prepareStatement(CONSULTA + "ORDER BY id DESC LIMIT 1");
			rs = ps.executeQuery();
			if (rs.next()) {
				return extrairDo(rs);
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao buscar o ultimo horario de atendimento. "
					+ "Motivo: " + e.getMessage());
		}finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
		}
	}
	
	@Override
	public List<HorarioAtendimento> listarPor(Restaurante restaurante) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<HorarioAtendimento> horariosAtendimento = new ArrayList<>();
		try {
			StringBuilder filtro = new StringBuilder(CONSULTA + " WHERE r.id = ? ORDER BY h.dia_semana, h.hora_abertura, h.hora_fechamento ");

			ps = conexao.prepareStatement(filtro.toString());
			ps.setInt(1, restaurante.getId());
		
			rs = ps.executeQuery();
			while (rs.next()) {
				horariosAtendimento.add(extrairDo(rs));
			}
			return horariosAtendimento;
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao listar horarios de atendimento. "
					+ "Motivo: " + e.getMessage());
		}finally {
			ManagerDb.getInstance().fechar(ps);
			ManagerDb.getInstance().fechar(rs);
		}
	}
	
	private HorarioAtendimento extrairDo(ResultSet rs) {
		try {
			
			int id = rs.getInt("id");
			String diaDaSemana = rs.getString("dia_semana");
			
			Categoria categoria = new Categoria(rs.getInt("id_categoria"), rs.getString("nome_categoria"));
			
			Endereco endereco = new Endereco(
					rs.getString("cidade_restaurante"), 
					rs.getString("logradouro_restaurante"), 
					rs.getString("bairro_restaurante"), 
					rs.getString("complemento_restaurante")
					);

			Restaurante restaurante = new Restaurante(
					rs.getInt("id_restaurante"),
					rs.getString("nome_restaurante"), 
					rs.getString("descricao_restaurante"), 
					endereco, 
					categoria
					);
			LocalTime abertura = Time.valueOf(rs.getString("abertura")).toLocalTime();
			LocalTime fechamento = Time.valueOf(rs.getString("fechamento")).toLocalTime();
			return new HorarioAtendimento(id, DiaDaSemana.valueOf(diaDaSemana), abertura, fechamento, restaurante);
			
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao extrair do horario de atendimento. "
					+ "Motivo: " + e.getMessage()); 
		}
	}
	
}
