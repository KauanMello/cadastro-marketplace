package br.com.senai.view.componentes;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import br.com.senai.core.service.ConnectionService;
import helper.SwingWorkerHelper;

public class ViewPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenu btnConfiguracoes;
	private JMenu btnCadastros;
	
	public ViewPrincipal() { 
		setTitle("Tela Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		btnCadastros = new JMenu("Cadastros");
		menuBar.add(btnCadastros);
		
		JMenuItem btnCategoria = new JMenuItem("Categorias");
		btnCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewGerenciarCategoriaListagem view = new ViewGerenciarCategoriaListagem(ViewPrincipal.this);
        		view.setVisible(true);
			}
		});
		btnCadastros.add(btnCategoria);
		
		JMenuItem btnRestaurante = new JMenuItem("Restaurantes");
		btnRestaurante.addActionListener((ActionEvent e) -> {
			ViewGerenciarRestaurantesListagem view = new ViewGerenciarRestaurantesListagem(this);
			view.setVisible(true);
		});
		btnCadastros.add(btnRestaurante);
		
		btnConfiguracoes = new JMenu("Configurações");
		menuBar.add(btnConfiguracoes);
		
		JMenuItem btnHorariosAtendimento = new JMenuItem("Horarios");
		btnHorariosAtendimento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewGerenciarHorariosCadastro view = new ViewGerenciarHorariosCadastro(ViewPrincipal.this);
				view.setVisible(true);
			}
		});
		btnConfiguracoes.add(btnHorariosAtendimento);
		
		JMenuItem btnNotificacoes = new JMenuItem("Notificações");
		btnNotificacoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ViewConfiguracaoEmail(ViewPrincipal.this).setVisible(true);
			}
		});
		btnConfiguracoes.add(btnNotificacoes);
		
		JMenuItem bntmSair = new JMenuItem("Sair");
		bntmSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		menuBar.add(bntmSair);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null); 

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		contentPane.add(progressBar, BorderLayout.SOUTH);
		
		SwingWorkerHelper.workerUtilitario(() -> {
			habilitarCampos(false);
			return new ConnectionService();
		}, (T) -> {
			habilitarCampos(true);
			contentPane.remove(progressBar);
			contentPane.updateUI();
		});
		
	}
	
	public void habilitarCampos(boolean b) {
		btnCadastros.setEnabled(b);
		btnConfiguracoes.setEnabled(b);
	}
	
}
