package view;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.DAO.ClienteDAO;
import model.bean.Cliente;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaListarClientes extends JInternalFrame {

	private static final long serialVersionUID = -7468082831627721494L;
	private JTable table;

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
			boolean[] canEdit = new boolean[] { true, false, true, true, true, false };

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
		btnExcluir.setBounds(72, 460, 90, 30);
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
						ClienteDAO clienteDao = new ClienteDAO();
						if (c.getTelefoneCLiente().length() < 10 || c.getTelefoneCLiente().length() > 11
								|| !isNumeric(c.getTelefoneCLiente())) {
							JOptionPane.showMessageDialog(null, "Digite um telefone válido", "Erro no Cadastro",
									JOptionPane.ERROR_MESSAGE);
						} else if (c.getRgCLiente().length() != 9 || !isNumeric(c.getRgCLiente())) {
							JOptionPane.showMessageDialog(null, "Digite um RG valido", "Erro no Cadastro",
									JOptionPane.ERROR_MESSAGE);
						} else {
							clienteDao.update(c);
							JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");			
						}
						atualizarTabelaClientes();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + e.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um cliente para Atualizar");
				}
			}
		});
		btnAtualizar.setBounds(218, 460, 90, 30);
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
}