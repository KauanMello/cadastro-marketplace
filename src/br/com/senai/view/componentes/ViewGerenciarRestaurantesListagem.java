package br.com.senai.view.componentes; 

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import br.com.senai.core.domain.Categoria;
import br.com.senai.core.domain.Restaurante;
import br.com.senai.core.service.CategoriaService;
import br.com.senai.core.service.RestauranteService;
import br.com.senai.view.componentes.table.RestauranteTableModel;
import helper.SwingWorkerHelper;

public class ViewGerenciarRestaurantesListagem extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome;
	private JTable tabelaRestaurante;
	private RestauranteService restauranteService;
	private CategoriaService categoriaService;
	private JComboBox<Categoria> cbxCategorias;
	
	public void carregarComboCategoria(List<Categoria> categorias) {
		cbxCategorias.addItem(null);
		for (Categoria categoria : categorias) {
			cbxCategorias.addItem(categoria);
		}
	}

	public ViewGerenciarRestaurantesListagem(Window owner) {
		super(owner);
		setModal(true);
		
		setTitle("Gerenciar Restaurante - Listagem");
		
		RestauranteTableModel model = new RestauranteTableModel(new ArrayList<Restaurante>());
		this.tabelaRestaurante = new JTable(model);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 580, 314);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setFocusable(true);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFiltros = new JLabel("Filtros:");
		lblFiltros.setBounds(12, 12, 70, 15);
		contentPane.add(lblFiltros);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(12, 51, 70, 15);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(61, 49, 152, 19);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setBounds(219, 51, 70, 15);
		contentPane.add(lblCategoria);
		
		cbxCategorias = new JComboBox<Categoria>();
		cbxCategorias.setBounds(297, 46, 117, 24);
		contentPane.add(this.cbxCategorias);
		
		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String filtroDeNome = txtNome.getText();
					Categoria categoria = (Categoria) cbxCategorias.getSelectedItem();
					if (categoria == null  && filtroDeNome.isBlank()) {
						JOptionPane.showMessageDialog(contentPane, "Selecione ao menos um filtro para prosseguir.", "Aviso!", JOptionPane.WARNING_MESSAGE);
					} else {
						List<Restaurante> restaurantesEncontrados = restauranteService.listarPor(filtroDeNome, categoria); 
						RestauranteTableModel model = new RestauranteTableModel(restaurantesEncontrados);
						tabelaRestaurante.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tabelaRestaurante.setModel(model);
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(contentPane, e2.getMessage(), "Erro ", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnListar.setBounds(429, 46, 117, 25);
		contentPane.add(btnListar);
		
		JButton btnNovo = new JButton("Novo");
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				ViewGerenciarRestaurantesCadastro view = new ViewGerenciarRestaurantesCadastro(ViewGerenciarRestaurantesListagem.this);
				view.setVisible(true);
				setVisible(true);
			}
		});
		btnNovo.setBounds(429, 7, 117, 25);
		contentPane.add(btnNovo);
		
		JLabel lblRestaurantesEncontrado = new JLabel("Restaurantes encontrado");
		lblRestaurantesEncontrado.setBounds(22, 97, 524, 15);
		contentPane.add(lblRestaurantesEncontrado);
		
		JScrollPane scrollPane = new JScrollPane(tabelaRestaurante);
		scrollPane.setBounds(22, 121, 534, 102);
		contentPane.add(scrollPane);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tabelaRestaurante.getSelectedRow();
				if (linhaSelecionada >= 0 && linhaSelecionada < tabelaRestaurante.getRowCount() ) {
					int op = JOptionPane.showConfirmDialog(contentPane,"Deseja realmente remover o restaurante?","Atenção! ", JOptionPane.YES_NO_OPTION);
					if (op == JOptionPane.YES_OPTION) {
						RestauranteTableModel model = (RestauranteTableModel) tabelaRestaurante.getModel();
						Restaurante restauranteEscolhido = model.getPor(linhaSelecionada);
						try {
							restauranteService.removerPor(restauranteEscolhido.getId());
							model.removerPor(linhaSelecionada);
							tabelaRestaurante.updateUI();
							JOptionPane.showMessageDialog(contentPane, "Restaurante removido com sucesso");
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(contentPane, e2.getMessage(), "Erro ", JOptionPane.ERROR_MESSAGE);
						}
					}
				}else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma linha", "Erro ", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnExcluir.setBounds(439, 238, 117, 25);
		contentPane.add(btnExcluir);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tabelaRestaurante.getSelectedRow();
				if (linhaSelecionada != -1) {
					RestauranteTableModel model = (RestauranteTableModel) tabelaRestaurante.getModel();
					Restaurante restauranteEscolhida = model.getPor(linhaSelecionada);
					ViewGerenciarRestaurantesCadastro view = new ViewGerenciarRestaurantesCadastro(ViewGerenciarRestaurantesListagem.this);
					setVisible(false);
					view.setRestaurante(restauranteEscolhida);
					view.setVisible(true);
					setVisible(true);
				}else {
					JOptionPane.showMessageDialog(contentPane, "Selecione um restaurante para prosseguir. ", "Aviso!", JOptionPane.WARNING_MESSAGE);
				}
				 
			}
		});
		btnEditar.setBounds(297, 238, 117, 25);
		contentPane.add(btnEditar);
				
		SwingWorkerHelper.workerUtilitario(() ->{
			btnListar.setText("Carregando...");
			btnListar.setEnabled(false);
			return restauranteService = new RestauranteService();
		}, (T) -> {
			btnListar.setText("Listar");
			btnListar.setEnabled(true);
		});
		
		SwingWorkerHelper.workerUtilitario(() -> {
			cbxCategorias.setEnabled(false);
			categoriaService = new CategoriaService();
			return categoriaService.listarTodos();
		}, (List<Categoria> categoria) -> {
			cbxCategorias.setEnabled(true);
			carregarComboCategoria(categoria);
		});
		
	}
	
}
