package br.com.senai.view.componentes;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.com.senai.core.domain.Categoria;
import br.com.senai.core.service.CategoriaService;
import helper.SwingWorkerHelper;

public class ViewGerenciarCategoriaCadastro extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome;
	private CategoriaService categoriaService;
	private Categoria categoria;
	public ViewGerenciarCategoriaCadastro(Window owner) {
		super(owner);
		
		setModal(true);
		setTitle("Gerenciar Categoria - Cadastro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 440, 168);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));  
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome: ");
		lblNome.setBounds(12, 61, 70, 17);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(69, 60, 349, 19);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JButton btnListar = new JButton("Pesquisar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnListar.setBounds(301, 12, 117, 25);
		contentPane.add(btnListar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtNome.setText(null);
			}
		});
		btnCancelar.setBounds(301, 91, 117, 25);
		contentPane.add(btnCancelar);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					String nomeCapturado = txtNome.getText();
					if(categoria == null) {
						categoria = new Categoria(nomeCapturado);
					}else {
						categoria.setNome(nomeCapturado);
					}
					categoriaService.salvar(categoria);
					JOptionPane.showMessageDialog(contentPane, "Categoria salva com sucesso!");
					dispose();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}
			}
		});
		btnSalvar.setBounds(172, 91, 117, 25);
		contentPane.add(btnSalvar);
		
		SwingWorkerHelper.workerUtilitario(() -> {
			btnSalvar.setEnabled(false);
			btnSalvar.setText("Carregando...");
			return categoriaService = new CategoriaService();
		}, (T) -> {
			btnSalvar.setText("Salvar");
			btnSalvar.setEnabled(true);
		});
	}

	public void setCategoria(Categoria categoriaEscolhida) {
		this.categoria = categoriaEscolhida;
		this.txtNome.setText(categoriaEscolhida.getNome());
	}
	
}
