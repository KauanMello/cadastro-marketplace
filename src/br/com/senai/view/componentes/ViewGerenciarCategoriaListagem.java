package br.com.senai.view.componentes;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
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
import br.com.senai.core.service.CategoriaService;
import br.com.senai.view.componentes.table.CategoriaTableModel;
import helper.SwingWorkerHelper;

public class ViewGerenciarCategoriaListagem extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFiltroDeNome;
	private CategoriaService categoriaService;
	private JTable tabelaCategoria;
	
	public ViewGerenciarCategoriaListagem(Window owner) {
		super(owner);
		setModal(true);
		
		CategoriaTableModel model = new CategoriaTableModel(new ArrayList<Categoria>());
		tabelaCategoria = new JTable(model);

		setTitle("Gerencia Categoria - Listagem");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
			
		JLabel lblFiltros = new JLabel("Filtros");
		lblFiltros.setBounds(12, 21, 70, 15);
		contentPane.add(lblFiltros);
		
		JButton btnNovo = new JButton("Novo");
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				ViewGerenciarCategoriaCadastro gerenciarCategoriaCadastro = new ViewGerenciarCategoriaCadastro(ViewGerenciarCategoriaListagem.this);
				gerenciarCategoriaCadastro.setVisible(true);
				setVisible(true);
			}
		});
		btnNovo.setBounds(300, 16, 117, 25);
		contentPane.add(btnNovo);
		
		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String filtro = txtFiltroDeNome.getText();
					List<Categoria> categoriasEncontradas = categoriaService.listarPor(filtro);
					CategoriaTableModel model = new CategoriaTableModel(categoriasEncontradas);
					tabelaCategoria.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					tabelaCategoria.setModel(model);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(contentPane,e2.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnListar.setBounds(300, 53, 117, 25);
		contentPane.add(btnListar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tabelaCategoria.getSelectedRow() >= 0) {
					int op = JOptionPane.showConfirmDialog(contentPane,"Deseja realmente remover a categoria?","Atenção! ", JOptionPane.YES_NO_OPTION);
					if (op == JOptionPane.YES_OPTION) {
						CategoriaTableModel model = (CategoriaTableModel) tabelaCategoria.getModel();
						Categoria categoriaEscolhida = model.getPor(tabelaCategoria.getSelectedRow());
						try {
							categoriaService.removerPor(categoriaEscolhida.getId());
							model.removerPor(tabelaCategoria.getSelectedRow());
							tabelaCategoria.updateUI();
							JOptionPane.showMessageDialog(contentPane, "Categoria removida com sucesso");
							tabelaCategoria.clearSelection();
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(contentPane, e2.getMessage(), "Erro ", JOptionPane.ERROR_MESSAGE);
						}
					}
				}else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma linha", "Erro ", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnExcluir.setBounds(321, 226, 117, 25);
		contentPane.add(btnExcluir);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tabelaCategoria.getSelectedRow();
				if (linhaSelecionada != -1) {
					CategoriaTableModel model = (CategoriaTableModel) tabelaCategoria.getModel();
					Categoria categoriaEscolhida = model.getPor(linhaSelecionada);
					ViewGerenciarCategoriaCadastro view = new ViewGerenciarCategoriaCadastro(ViewGerenciarCategoriaListagem.this);
					dispose();
					view.setCategoria(categoriaEscolhida);
					view.setVisible(true);
//					dispose();
				} else {
					JOptionPane.showMessageDialog(contentPane, "Escolha uma linha para continuar ", "Aviso! ", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnEditar.setBounds(186, 226, 117, 25);
		contentPane.add(btnEditar);
		
		txtFiltroDeNome = new JTextField(); 
		txtFiltroDeNome.setBounds(76, 56, 212, 19);
		contentPane.add(txtFiltroDeNome);
		txtFiltroDeNome.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome*");
		lblNome.setBounds(24, 58, 70, 15);
		contentPane.add(lblNome);
		
		JScrollPane scrollPane = new JScrollPane(tabelaCategoria);
		scrollPane.setBounds(24, 106, 414, 104);
		contentPane.add(scrollPane);
		
		SwingWorkerHelper.workerUtilitario(() -> {
			btnListar.setText("Carregando..");
			btnListar.setEnabled(false);
			categoriaService = new CategoriaService();
			return categoriaService;
		}, (T) -> {
			btnListar.setText("Listar");
			btnListar.setEnabled(true);
		});
		
	}
}
