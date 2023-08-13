package br.com.senai.view.componentes;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import helper.ManipuladorEmail;
import helper.ManipuladorSms;
import helper.SendEmail;
import helper.SendSms;


public class ViewConfiguracaoEmail extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	

	public ViewConfiguracaoEmail(Window owner) {
		super(owner);
		setModal(true);
		setTitle("Configuração de Notificações");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		setFocusable(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JPanel panelSuperio = new JPanel();
		
		JPanel painelBotoes = new JPanel(); 
		
		JPanel panel_3 = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
						.addComponent(painelBotoes, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
						.addComponent(panelSuperio, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panelSuperio, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addGap(141)
					.addComponent(painelBotoes, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JLabel lblAtivarNotificaesopcional = new JLabel("Ativar notificações? (opcional)");
		panel_3.add(lblAtivarNotificaesopcional);
		
		
		JCheckBox chxEmail = new JCheckBox("Email");
		panelSuperio.add(chxEmail);
		chxEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		chxEmail.setSelected(SendEmail.isEmailAtivado());
		
		JCheckBox chxWhatsApp = new JCheckBox("SMS");
		chxWhatsApp.setHorizontalAlignment(SwingConstants.TRAILING);
		chxWhatsApp.setSelected(SendSms.isSmsAtivado());
		panelSuperio.add(chxWhatsApp);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int op = JOptionPane.showConfirmDialog(contentPane, "Deseja realmente sair? Qualquer alteração não salva será perdida");
				if (op == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
		});
		painelBotoes.add(btnVoltar);
		
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					ManipuladorSms.setIsAtivo(chxWhatsApp.isSelected());
					ManipuladorEmail.setIsAtivo(chxEmail.isSelected());
					JOptionPane.showMessageDialog(contentPane, "Alterações gravadas");
			}
				
	 	});
		painelBotoes.add(btnSalvar);
	
		contentPane.setLayout(gl_contentPane);
	}
	
	
	
}
