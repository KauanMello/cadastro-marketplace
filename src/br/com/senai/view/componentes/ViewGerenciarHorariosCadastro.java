package br.com.senai.view.componentes;

import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel; 
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import br.com.senai.core.domain.DiaDaSemana;
import br.com.senai.core.domain.HorarioAtendimento;
import br.com.senai.core.domain.Restaurante;
import br.com.senai.core.service.HorarioAtendimentoService;
import br.com.senai.core.service.RestauranteService;
import br.com.senai.view.componentes.table.HorarioAtendimentoTableModel;
import helper.SendEmail;
import helper.SendSms;
import helper.SwingWorkerHelper;
import helper.ViewHelper;

public class ViewGerenciarHorariosCadastro extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFormattedTextField txtAbertura ;
	private JFormattedTextField txtFechamento;
	private JTable tabelaHorariosAtendimento;
	private JComboBox<Restaurante> cbxRestaurante;
	private JComboBox<DiaDaSemana> cbxDiaDaSemana;
	private RestauranteService restauranteService;
	private HorarioAtendimentoService horarioAtendimentoService;
	private HorarioAtendimento horarioAtendimento;
	private boolean isEditar;
	
	public ViewGerenciarHorariosCadastro(Window owner) {
		super(owner);
		
		HorarioAtendimentoTableModel model = new HorarioAtendimentoTableModel(new ArrayList<HorarioAtendimento>());
		this.tabelaHorariosAtendimento = new JTable(model);
		tabelaHorariosAtendimento.setForeground(Color.BLACK);
		tabelaHorariosAtendimento.setFocusable(true);
		 
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 871, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRestaurante = new JLabel("Restaurante");
		lblRestaurante.setBounds(12, 12, 184, 15);
		contentPane.add(lblRestaurante);
		
		cbxRestaurante = new JComboBox<Restaurante>();
		cbxRestaurante.setForeground(Color.BLACK);
		cbxRestaurante.setEnabled(false);
		cbxRestaurante.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					carregarTabela();
					limparCampos();
				}
			}
		});
		cbxRestaurante.setBounds(112, 7, 734, 24);
		contentPane.add(cbxRestaurante);
		
		JLabel lblDiaDaSemana = new JLabel("Dia da Semana");
		lblDiaDaSemana.setBounds(12, 49, 153, 15);
		contentPane.add(lblDiaDaSemana);
		
		cbxDiaDaSemana = new JComboBox<>();
		cbxDiaDaSemana.setForeground(Color.BLACK);
		cbxDiaDaSemana.addItem(null);
		for (DiaDaSemana dia : DiaDaSemana.values()) {
			cbxDiaDaSemana.addItem(dia);
		}
		cbxDiaDaSemana.setBounds(112, 44, 151, 24);
		contentPane.add(cbxDiaDaSemana);
		
		JLabel lblAbertura = new JLabel("Abertura");
		lblAbertura.setBounds(294, 49, 70, 15);
		contentPane.add(lblAbertura);
		
		txtAbertura = new JFormattedTextField();
		txtAbertura .setBounds(356, 47, 114, 19);
		contentPane.add(txtAbertura );
		try {
			MaskFormatter mascara = new MaskFormatter("##:##");
			mascara.install(txtAbertura);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JLabel lblFechamento = new JLabel("Fechamento");
		lblFechamento.setBounds(508, 49, 108, 15);
		contentPane.add(lblFechamento);
		
		txtFechamento = new JFormattedTextField();
		txtFechamento.setBounds(591, 46, 114, 19);
		contentPane.add(txtFechamento);
		try {
			MaskFormatter mascara = new MaskFormatter("##:##");
			mascara.install(txtFechamento);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JLabel lblHorario = new JLabel("Horarios");
		lblHorario.setBounds(12, 87, 70, 15);
		contentPane.add(lblHorario);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(new Color(0, 0, 0));
		separator_1.setBounds(12, 113, 503, 2);
		contentPane.add(separator_1);
		
		JScrollPane scrollPane = new JScrollPane(tabelaHorariosAtendimento);
		scrollPane.setBounds(12, 126, 502, 219);
		contentPane.add(scrollPane);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(Color.BLACK);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setIsEditando(false);
				limparCampos();
			}
		});
		btnCancelar.setBounds(728, 356, 117, 25);
		contentPane.add(btnCancelar);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "A\u00E7\u00F5es", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(566, 145, 280, 113);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setForeground(Color.BLACK);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getIsEditando()) {
					JOptionPane.showMessageDialog(contentPane, "Saia do modo de edição ou cancele para poder excluir esse cadastro.");
				}else {
					int linhaSelecionada = tabelaHorariosAtendimento.getSelectedRow();
					if (linhaSelecionada != -1) {
						int op = JOptionPane.showConfirmDialog(contentPane, "Deseja realmente excluir o horario? ");
						if (op == JOptionPane.YES_OPTION) {
							HorarioAtendimentoTableModel model = (HorarioAtendimentoTableModel) tabelaHorariosAtendimento.getModel();
							HorarioAtendimento horarioEscolhido = model.getPor(linhaSelecionada);
							try {
								horarioAtendimentoService.removerPor(horarioEscolhido.getId());
								model.removerPor(linhaSelecionada);
								carregarTabela();
								limparCampos();
								JOptionPane.showMessageDialog(contentPane, "Excluido com sucesso");
							} catch (Exception e2) {
								JOptionPane.showMessageDialog(contentPane, e2.getMessage(), "Erro ", JOptionPane.ERROR_MESSAGE);
							}
							tabelaHorariosAtendimento.clearSelection();
						}
					}else {
						JOptionPane.showMessageDialog(contentPane, "Selecione uma linha", "Erro ", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnExcluir.setBounds(34, 65, 179, 25);
		panel.add(btnExcluir);
		
		JButton btnAdicionar = new JButton("Salvar");
		btnAdicionar.setForeground(Color.BLACK);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvarHorarioDeAtendimento();
			}
		});
		btnAdicionar.setBounds(729, 44, 117, 25);
		contentPane.add(btnAdicionar);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.setForeground(Color.BLACK);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tabelaHorariosAtendimento.getSelectedRow();
				if (linhaSelecionada != -1) {
					setIsEditando(true);
					HorarioAtendimentoTableModel model =  (HorarioAtendimentoTableModel) tabelaHorariosAtendimento.getModel();
					HorarioAtendimento horarioSelecionado = model.getPor(linhaSelecionada);
					setarHorariosAtendimentoNosCampos(horarioSelecionado);
				}else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma linha", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnEditar.setBounds(34, 28, 179, 25);
		panel.add(btnEditar);
		
		SwingWorkerHelper.workerUtilitario(() -> {
			horarioAtendimentoService = new HorarioAtendimentoService();
			restauranteService = new RestauranteService();
			return restauranteService.listarTodos();
		}, (List<Restaurante> restaurantes) -> {
			carregarComboDe(restaurantes);
			cbxRestaurante.setEnabled(true);
		});
		
		
	}
	
	public void setIsEditando(boolean b) {
		isEditar = b;
	}
	public boolean getIsEditando() {
		return isEditar;
	}
	
	public void sendEmail(String tabelaAnterior, String tabelaNova) {
		SwingWorkerHelper.workerUtilitario(() -> {
			SendEmail.enviarEmail("Alteração na tabela de horários", 
					"<center> <br> O restaurante: <b>" + cbxRestaurante.getSelectedItem() 
					+ "</b> foi alterado de: <br><br>" + tabelaAnterior 
					+ "<br> Para: <br><br>" + tabelaNova + "</center>");
			return null;
		}, null);
	}
	
	public String montarTabelaPersonalizada(int id, String colorLine) {
		
		Restaurante restaurante = (Restaurante) cbxRestaurante.getSelectedItem();
		List<HorarioAtendimento> horariosEncontrados = horarioAtendimentoService.listarPor(restaurante);
		StringBuilder html = new StringBuilder();
		
		html.append("<table style='width: 30%; border-collapse: collapse;'>"
				+ "<thead>"
				+ "<tr style='background-color: darkblue; color: white;'>"
				+ "<th style='border: 1px solid gray; font-weight: bold; padding: 10px; text-align: center;'>Dia da semana</th>"
				+ "<th style='border: 1px solid gray; font-weight: bold; padding: 10px; text-align: center;'>Horario abertura</th>"
				+ "<th style='border: 1px solid gray; font-weight: bold; padding: 10px; text-align: center;'>Horario fechamento</th>"
				+ "</tr>"
				+ "</thead>"
				+ "<tbody style='border: 1px solid gray;'>");
		
		for (HorarioAtendimento horarioAtendimento : horariosEncontrados) {
			
			if (horarioAtendimento.getId() == id) {
				html.append("<tr style='padding: 10px;'>"
						+ "<td style='border: 1px solid gray; background-color:" + colorLine + "; text-align: center;'>" + horarioAtendimento.getDiaDaSemana() + "</td>"
						+ "<td style='border: 1px solid gray; background-color:" + colorLine + "; text-align: center;'>" + horarioAtendimento.getHoraAbertura() + "</td>"
						+ "<td style='border: 1px solid gray; background-color:" + colorLine + "; text-align: center;'>" + horarioAtendimento.getHoraFechamento() + "</td>"
						+ "</tr>");
			}else {
				html.append("<tr style='padding: 10px;'>"
						+ "<td style='border: 1px solid gray; text-align: center;'>" + horarioAtendimento.getDiaDaSemana() + "</td>"
						+ "<td style='border: 1px solid gray; text-align: center;'>" + horarioAtendimento.getHoraAbertura() + "</td>"
						+ "<td style='border: 1px solid gray; text-align: center;'>" + horarioAtendimento.getHoraFechamento() + "</td>"
						+ "</tr>");
			}
		}
		html.append("</tbody>"
				+ "</table>");
		
		return html.toString();
	}
	
	public void sendSms(String mensagem) {
		SwingWorkerHelper.workerUtilitario(() -> {
			SendSms.enviarSms(mensagem);
			return null;
		}, null);
	}
	
	public void setarHorariosAtendimentoNosCampos(HorarioAtendimento horarioAtendimentoSelecionado) {
		this.horarioAtendimento = horarioAtendimentoSelecionado;
		this.cbxRestaurante.setSelectedItem(horarioAtendimentoSelecionado.getRestaurante());
		this.cbxDiaDaSemana.setSelectedItem(horarioAtendimentoSelecionado.getDiaDaSemana());
		this.txtAbertura.setText(horarioAtendimentoSelecionado.getHoraAbertura().toString());
		this.txtFechamento.setText(horarioAtendimentoSelecionado.getHoraFechamento().toString());
		
	}
	
	private void carregarTabela() {
		Restaurante restaurante = (Restaurante) cbxRestaurante.getSelectedItem();
		List<HorarioAtendimento> horariosEncontrados = horarioAtendimentoService.listarPor(restaurante);
		HorarioAtendimentoTableModel model = new HorarioAtendimentoTableModel(horariosEncontrados);
		tabelaHorariosAtendimento.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaHorariosAtendimento.setModel(model);
	}
	
	private void carregarComboDe(List<Restaurante> restaurantes) {
		cbxRestaurante.addItem(null);
		for (Restaurante restaurante : restaurantes) {
			cbxRestaurante.addItem(restaurante);
		}
	}
	
	private void salvarHorarioDeAtendimento() {
		try {
			
			Restaurante restaurante = (Restaurante) cbxRestaurante.getSelectedItem();
			DiaDaSemana diaDaSemana = (DiaDaSemana) cbxDiaDaSemana.getSelectedItem();
			
			LocalTime abertura = ViewHelper.extrairHoraDo(txtAbertura);
			LocalTime fechamento = ViewHelper.extrairHoraDo(txtFechamento);
			
			
			String tabelaAnterior = "";
			
			if (horarioAtendimento == null || !getIsEditando()) {
				horarioAtendimento = new HorarioAtendimento(diaDaSemana, abertura, fechamento, restaurante);
			}else {
				tabelaAnterior = montarTabelaPersonalizada(horarioAtendimento.getId(), "rgb(245, 145, 145)");
				horarioAtendimento.setRestaurante(restaurante);
				horarioAtendimento.setDiaDaSemana(diaDaSemana);
				horarioAtendimento.setHoraAbertura(abertura);
				horarioAtendimento.setHoraFechamento(fechamento);
			}
			
			if (getIsEditando()) {
				horarioAtendimentoService.salvar(horarioAtendimento);
				JOptionPane.showMessageDialog(contentPane, "Horario foi alterado com sucesso");
				sendEmail(tabelaAnterior, montarTabelaPersonalizada(horarioAtendimento.getId(), "lightgreen"));
				sendSms("Ocorreu uma alteração na tabela de horario. ");
				setIsEditando(false);
			}else {
				horarioAtendimentoService.salvar(horarioAtendimento);
				JOptionPane.showMessageDialog(contentPane, "Novo horário cadastrado com sucesso");
			}
			
			carregarTabela();
			limparCampos();
			contentPane.updateUI();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(contentPane, ex.getMessage(), "Erro: ", JOptionPane.WARNING_MESSAGE);;
		}
		
	}

	private void limparCampos() {
		cbxDiaDaSemana.setSelectedItem(null);
		txtAbertura.setText(null);
		txtFechamento.setText(null);
	}

}
