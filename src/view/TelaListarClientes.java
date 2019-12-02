package view;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Auxiliares.JNumberFormatField;
import Auxiliares.ValidaData;
import Exception.DataDdMmYyyyInvalida;
import Exception.DataDigitadaInvalidaException;
import model.DAO.ClienteDAO;
import model.DAO.MedicamentoDAO;
import model.bean.Cliente;
import model.bean.Medicamento;

import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;

import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

public class TelaListarClientes extends JInternalFrame {

	private static final long serialVersionUID = -7468082831627721494L;
	private JTable table;
	private JDialog dialog = null;

	public TelaListarClientes() {
		setBounds(0, 0, 794, 550);
		setClosable(true);
		setTitle("Farmácia Express - Listar clientes");
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 778, 427);
		getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Nome", "Cpf", "Rg", "Endereco", "Telefone", "TipoCliente" }) {
			private static final long serialVersionUID = 7549926424366818036L;
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() != -1) {
					try {
						Cliente c = new Cliente();
						c.setCpfCliente(table.getValueAt(table.getSelectedRow(), 1).toString().replaceAll("-", "")
								.replaceAll("[.]", ""));
						c.setTipoCLiente(
								table.getValueAt(table.getSelectedRow(), 5).toString().equals("Cliente Normal") ? "N"
										: "E");

						DefaultTableModel dtmModelCliente = (DefaultTableModel) table.getModel();
						ClienteDAO clienteDao = new ClienteDAO();
						clienteDao.delete(c);
						dtmModelCliente.removeRow(table.getSelectedRow());
						JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Erro ao excluir: " + e.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um cliente para Excluir");
				}
			}
		});
		btnExcluir.setBounds(160, 460, 90, 30);
		getContentPane().add(btnExcluir);

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() != -1) {
					try {
						Cliente c = new Cliente();
						c.setNomeCLiente(table.getValueAt(table.getSelectedRow(), 0).toString());
						c.setCpfCliente(table.getValueAt(table.getSelectedRow(), 1).toString().replaceAll("-", "")
								.replaceAll("[.]", ""));
						c.setRgCLiente(table.getValueAt(table.getSelectedRow(), 2).toString().replaceAll("-", "")
								.replaceAll("[.]", ""));
						c.setEnderecoCliente(table.getValueAt(table.getSelectedRow(), 3).toString());
						c.setTelefoneCLiente(table.getValueAt(table.getSelectedRow(), 4).toString().replaceAll("-", "")
								.replaceAll("[(]", "").replaceAll("[)]", ""));
						c.setTipoCLiente(
								table.getValueAt(table.getSelectedRow(), 5).toString().equals("Cliente Normal") ? "N"
										: "E");						
				        if ((dialog == null) || (!(dialog.isVisible()))) {
				            dialog = new JDialog();
				            inicializaComponentesAlterar(dialog,c);   
				        }
				        atualizarTabelaClientes();
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + e.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um cliente para Atualizar");
				}
			}	
		});
		btnAtualizar.setBounds(42, 460, 90, 30);
		getContentPane().add(btnAtualizar);
		atualizarTabelaClientes();
	}

	private void atualizarTabelaClientes() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.getDataVector().removeAllElements();
		modelo.fireTableDataChanged();
		
		ClienteDAO clienteDao = new ClienteDAO();
		for (Cliente c : clienteDao.read()) {
			String tipoCliente = "-";
			if (c.getTipoCLiente().equals("N")) {
				tipoCliente = "Cliente Normal";
			} else if (c.getTipoCLiente().equals("E")) {
				tipoCliente = "Cliente Aposentado";
			}
			modelo.addRow(new Object[] { c.getNomeCLiente(), maskCpf(c.getCpfCliente()), maskRg(c.getRgCLiente()),
					c.getEnderecoCliente(), maskTelefone(c.getTelefoneCLiente()), tipoCliente });
		}
	}

	public static String maskRg(String rg) {
		StringBuilder sb = new StringBuilder();
		sb.append(rg.substring(0, 2)).append(".").append(rg.substring(2, 5)).append(".").append(rg.substring(5, 8))
				.append("-").append(rg.substring(8, 9));
		return sb.toString();
	}

	public static String maskCpf(String cpf) {
		StringBuilder sb = new StringBuilder();
		sb.append(cpf.substring(0, 3)).append(".").append(cpf.substring(3, 6)).append(".").append(cpf.substring(6, 9))
				.append("-").append(cpf.substring(9, 11));
		return sb.toString();
	}

	public static String maskTelefone(String telefone) {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("(").append(telefone.substring(0, 2)).append(")").append(telefone.substring(2, 7)).append("-")
					.append(telefone.substring(7, 11));
		} catch (StringIndexOutOfBoundsException e) {
			sb.setLength(0);
			sb.append("(").append(telefone.substring(0, 2)).append(")").append(telefone.substring(2, 6)).append("-")
					.append(telefone.substring(6, 10));
		}
		return sb.toString();
	}

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	private void inicializaComponentesAlterar(JDialog dialog, Cliente c) {
		dialog.setTitle("Atualizar Cliente");
        dialog.setPreferredSize(new Dimension(800, 350));
        dialog.pack();
        dialog.setModal(true);
        dialog.setFocusable(true);
		dialog.setLayout(null);
        try {
			dialog.setIconImage(Toolkit.getDefaultToolkit().getImage("imagens/farmacia-icone.png"));
        } catch (NullPointerException ex) {
        	System.out.println("nao encontrou o icone");
        }
        
        JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPane.setBounds(100,100, 100, 100);
        JPanel jpanelAlterar = new JPanel();
        jpanelAlterar.setLayout(new BorderLayout());
        jpanelAlterar.setBorder(new EmptyBorder(5, 5, 5, 5));
        ButtonGroup buttonGroup = new ButtonGroup();
        
        jpanelAlterar.setBounds(0, 0, dialog.getWidth(), dialog.getHeight() - 30);
        
        JLabel lblRg = new JLabel("RG");
		lblRg.setBounds(0, 0, 150, 30);
		jpanelAlterar.add(lblRg);

		JFormattedTextField txtRg = new JFormattedTextField();
		txtRg.setBounds(150, 0, 400, 30);

		try {
			txtRg.setFormatterFactory(
					new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###-#")));
		} catch (java.text.ParseException ex) {
			System.out.println("Erro na Mascara do RG: " + ex);
		}
		txtRg.setText(c.getRgCLiente());
		jpanelAlterar.add(txtRg);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(0, 30, 150, 30);
		jpanelAlterar.add(lblNome);

		JTextField txtNome = new JTextField();
		txtNome.setBounds(150, 30, 400, 30);
		txtNome.setText(c.getNomeCLiente());
		jpanelAlterar.add(txtNome);

		JLabel lblEndereo = new JLabel("Endere\u00E7o");
		lblEndereo.setBounds(0, 60, 150, 30);
		jpanelAlterar.add(lblEndereo);

		JTextField txtEndereco = new JTextField();
		txtEndereco.setBounds(150, 60, 400, 30);
		txtEndereco.setText(c.getEnderecoCliente());
		jpanelAlterar.add(txtEndereco);

		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setBounds(0, 90, 150, 30);
		jpanelAlterar.add(lblTelefone);

		JFormattedTextField txtTelefone = new JFormattedTextField();
		txtTelefone.setBounds(150, 90, 400, 30);
		try {
			txtTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
					new javax.swing.text.MaskFormatter("(##) #####-####")));
		} catch (java.text.ParseException ex) {
			System.out.println("Erro na Mascara do Telefone: " + ex);
		}
		txtTelefone.setText(c.getTelefoneCLiente());
		jpanelAlterar.add(txtTelefone);

		JLabel lblAposentado = new JLabel("Aposentado");
		lblAposentado.setBounds(0, 120, 150, 30);
		jpanelAlterar.add(lblAposentado);

		JRadioButton rdbtnAposentadoSim = new JRadioButton("Sim");
		buttonGroup.add(rdbtnAposentadoSim);
		rdbtnAposentadoSim.setBounds(75, 120, 50, 30);
		jpanelAlterar.add(rdbtnAposentadoSim);

		JRadioButton rdbtnAposentadoNao = new JRadioButton("N\u00E3o");
		buttonGroup.add(rdbtnAposentadoNao);
		rdbtnAposentadoNao.setBounds(125, 120, 50, 30);
		jpanelAlterar.add(rdbtnAposentadoNao);
		
		if (c.getTipoCLiente().equals("E")) {
			rdbtnAposentadoSim.setSelected(true);
		}else {
			rdbtnAposentadoNao.setSelected(true);
		}
		
		rdbtnAposentadoSim.setEnabled(false);
		rdbtnAposentadoNao.setEnabled(false);

		JLabel lblAlteracao = new JLabel("");
		lblAlteracao.setBounds(0, 170, 150, 30);
		jpanelAlterar.add(lblAlteracao);
		
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = txtNome.getText();
				String rg = txtRg.getText().replaceAll("[.]", "").replaceAll("-", "").replaceAll(" ", "");
				String endereco = txtEndereco.getText();
				String telefone = txtTelefone.getText().replaceAll("[(]", "").replaceAll("[)]", "").replaceAll("-", "").replaceAll(" ", "");
				String tipoCliente = "";
				if (rdbtnAposentadoSim.isSelected()) {
					tipoCliente = "E";
				} else if (rdbtnAposentadoNao.isSelected()) {
					tipoCliente = "N";
				}
				if (nome.equals("") || rg.equals("") || endereco.equals("") || telefone.equals("")
						|| tipoCliente.equals("")) {
					JOptionPane.showMessageDialog(null, "Campos não preenchidos", "Erro no Cadastro",
							JOptionPane.ERROR_MESSAGE);
				}else if (telefone.length() < 10 ) {
					JOptionPane.showMessageDialog(null, "Digite um telefone com 8 digitos ou mais", "Erro no Cadastro",
							JOptionPane.ERROR_MESSAGE);
				}else if(rg.length() < 9) {
					JOptionPane.showMessageDialog(null, "Digite um RG valido", "Erro no Cadastro",
							JOptionPane.ERROR_MESSAGE);	
				}else {
					try {
						c.setNomeCLiente(nome);
						c.setRgCLiente(rg);
						c.setEnderecoCliente(endereco);
						c.setTelefoneCLiente(telefone);
						c.setTipoCLiente(tipoCliente);
						ClienteDAO clienteDao = new ClienteDAO();
						clienteDao.update(c);
						dialog.dispose();
						JOptionPane.showMessageDialog(null, "Alterado com sucesso!");
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Erro ao alterar Cliente: ", "Erro na alteração do CLiente",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		
		buttonPane.add(okButton);
		buttonPane.add(cancelButton);
		jpanelAlterar.add(buttonPane, BorderLayout.SOUTH);
		dialog.add(jpanelAlterar,BorderLayout.CENTER);
		
        //Centralizando a dialog no centro da tela
        java.awt.Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation((int) (d.getWidth() - dialog.getWidth()) / 2, (int) (d.getHeight() - dialog.getHeight()) / 2);  
        dialog.setVisible(true);
	}
}