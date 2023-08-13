package br.com.senai.view.componentes;

import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import br.com.senai.core.domain.Categoria;
import br.com.senai.core.domain.Endereco;
import br.com.senai.core.domain.Restaurante;
import br.com.senai.core.service.CategoriaService;
import br.com.senai.core.service.RestauranteService;
import helper.SwingWorkerHelper;

public class ViewGerenciarRestaurantesCadastro extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome;
	private JLabel lblCategoria;
	private JButton btnPesquisar;
	private JLabel lblDescrio;
	private JLabel lblLogradouro;
	private JTextField txtLogradouro;
	private JLabel lblCidade;
	private JTextField txtCidade;
	private JLabel lblBairro;
	private JTextField txtBairro;
	private JLabel lblComplemento;
	private JTextField txtComplemento;
	private JButton btnCancelar;
	private JButton btnSalvar;
	private Restaurante restaurante;
	private Endereco endereco;
	private JTextArea textAreaDescricao;
	
	private RestauranteService restauranteService;
	private CategoriaService categoriaService;
	private JComboBox<Categoria> cbxCategorias;

	
	public ViewGerenciarRestaurantesCadastro(Window owner) {
		super(owner);
		setModal(true);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 819, 419);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(12, 55, 70, 15);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(100, 52, 419, 19);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		lblCategoria = new JLabel("Categoria");
		lblCategoria.setBounds(525, 55, 70, 15);
		contentPane.add(lblCategoria);
		
		cbxCategorias = new JComboBox<Categoria>();
		cbxCategorias.setBounds(603, 49, 174, 24);
		contentPane.add(cbxCategorias);
		
		btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ViewGerenciarRestaurantesListagem view = new ViewGerenciarRestaurantesListagem(ViewGerenciarRestaurantesCadastro.this);
				view.setVisible(true);
			}
		});
		btnPesquisar.setBounds(660, 11, 117, 25);
		contentPane.add(btnPesquisar);
		
		lblDescrio = new JLabel("Descrição");
		lblDescrio.setBounds(12, 99, 70, 15);
		contentPane.add(lblDescrio);
		
		lblLogradouro = new JLabel("Logradouro");
		lblLogradouro.setBounds(12, 207, 143, 15);
		contentPane.add(lblLogradouro);
		
		txtLogradouro = new JTextField();
		txtLogradouro.setBounds(100, 206, 677, 19);
		contentPane.add(txtLogradouro);
		txtLogradouro.setColumns(10);
		
		lblCidade = new JLabel("Cidade");
		lblCidade.setBounds(12, 234, 70, 15);
		contentPane.add(lblCidade);
		
		txtCidade = new JTextField();
		txtCidade.setBounds(100, 237, 316, 19);
		contentPane.add(txtCidade);
		txtCidade.setColumns(10);
		
		lblBairro = new JLabel("Bairro");
		lblBairro.setBounds(434, 239, 70, 15);
		contentPane.add(lblBairro);
		
		txtBairro = new JTextField();
		txtBairro.setBounds(496, 237, 281, 19);
		contentPane.add(txtBairro);
		txtBairro.setColumns(10);
		
		lblComplemento = new JLabel("Complemento");
		lblComplemento.setBounds(0, 266, 126, 15);
		contentPane.add(lblComplemento);
		
		txtComplemento = new JTextField();
		txtComplemento.setBounds(100, 264, 677, 19);
		contentPane.add(txtComplemento);
		txtComplemento.setColumns(10);
		
		textAreaDescricao = new JTextArea();
		textAreaDescricao.setBounds(100, 94, 677, 102);
		contentPane.add(textAreaDescricao);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "A\u00E7\u00F5es", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(506, 295, 281, 74);
		contentPane.add(panel);
		panel.setLayout(null);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(154, 25, 117, 25);
		panel.add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int op = JOptionPane.showConfirmDialog(contentPane, "Deseja realmente cancelar?");
				if (op == JOptionPane.YES_OPTION) {
					limparDadosDaTela(restaurante);
				}
			}
		});
		
		btnSalvar = new JButton();
		btnSalvar.setBounds(10, 25, 132, 25);
		panel.add(btnSalvar);
		
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			try {
				String nome = txtNome.getText();
				Categoria categoria = (Categoria) cbxCategorias.getSelectedItem();
				String descricao = textAreaDescricao.getText();
				String logradouro = txtLogradouro.getText();
				String cidade = txtCidade.getText();
				String bairro = txtBairro.getText();
				String complemento = txtBairro.getText();

				endereco = new Endereco(cidade, logradouro, bairro, complemento);
				if (restaurante == null) {
					restaurante = new Restaurante(nome, descricao, endereco, categoria);
				}else {
					restaurante.setNome(nome);
					restaurante.setDescricao(descricao);
					restaurante.setEndereco(endereco);
					restaurante.setCategoria(categoria);
				}
				restauranteService.salvar(restaurante);
				JOptionPane.showMessageDialog(contentPane, "Restaurante salvo com sucesso");
				limparDadosDaTela(restaurante);
			}catch (Exception e2) {
				JOptionPane.showMessageDialog(contentPane, e2.getMessage(), "Erro ", JOptionPane.ERROR_MESSAGE);
			}
		  }				
		});
		
		SwingWorkerHelper.workerUtilitario(() -> {
			cbxCategorias.setEnabled(false);
			categoriaService = new CategoriaService();
			return categoriaService.listarTodos();
		}, (List<Categoria> categorias) -> {
			cbxCategorias.setEnabled(true);
			carregarComboCategoria(categorias);
		});
		
		SwingWorkerHelper.workerUtilitario(() -> {
			btnSalvar.setText("Carregando...");
			btnSalvar.setEnabled(false);
			return restauranteService = new RestauranteService();
		}, (T) -> {
			btnSalvar.setText("Salvar");
			btnSalvar.setEnabled(true);
		});
		
	}
	
	
	public void carregarComboCategoria(List<Categoria> categorias) {
		cbxCategorias.addItem(null);
		for (Categoria categoria : categorias) {
			cbxCategorias.addItem(categoria);
		}
	}
	
	public void setRestaurante(Restaurante restauranteEscolhido) {
		this.restaurante = restauranteEscolhido;
		this.txtNome.setText(restauranteEscolhido.getNome());
		this.cbxCategorias.setSelectedItem(restauranteEscolhido.getCategoria());
		this.textAreaDescricao.setText(restauranteEscolhido.getDescricao());
		this.txtLogradouro.setText(restauranteEscolhido.getEndereco().getLogradouro());
		this.txtCidade.setText(restauranteEscolhido.getEndereco().getCidade());
		this.txtBairro.setText(restauranteEscolhido.getEndereco().getBairro());
		this.txtComplemento.setText(restauranteEscolhido.getEndereco().getComplemento());
	}
	
	private void limparDadosDaTela(Restaurante restaurante) {
		if (restaurante != null) {
			restaurante.setId(0);
		}
		this.txtNome.setText(null);
		this.cbxCategorias.setSelectedItem(null);
		this.textAreaDescricao.setText(null);
		this.txtLogradouro.setText(null);
		this.txtCidade.setText(null);
		this.txtBairro.setText(null);
		this.txtComplemento.setText(null);
	}
}
