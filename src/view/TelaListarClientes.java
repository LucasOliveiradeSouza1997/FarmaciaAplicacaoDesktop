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
			boolean[] canEdit = new boolean[] { false, false, false,false,false,false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        if(table.getSelectedRow() != -1){
		        	try {
		        		Cliente c = new Cliente();
		        		c.setCpfCliente(table.getValueAt(table.getSelectedRow(), 1).toString().replaceAll("-", "").replaceAll("[.]", ""));
		        		c.setTipoCLiente(table.getValueAt(table.getSelectedRow(), 5).toString().equals("Cliente Normal") ? "N":"E");
		        		
		        		DefaultTableModel dtmProdutos = (DefaultTableModel) table.getModel();
		        		ClienteDAO clienteDao = new ClienteDAO();
		        		clienteDao.delete(c);
			            dtmProdutos.removeRow(table.getSelectedRow());
			            JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Erro ao excluir: " + e.getMessage());
					}
		        }else{
		            JOptionPane.showMessageDialog(null,"Selecione um cliente para Excluir");
		        }
			}
		});
		btnExcluir.setBounds(72, 460, 90, 30);
		getContentPane().add(btnExcluir);

		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		ClienteDAO clienteDao = new ClienteDAO();
		for (Cliente c : clienteDao.read()) {
			String tipoCliente = "-";
			if (c.getTipoCLiente().equals("N")) {
				tipoCliente = "Cliente Normal";
			} else if (c.getTipoCLiente().equals("E")) {
				tipoCliente = "Cliente Aposentado";
			}
			modelo.addRow(new Object[] { c.getNomeCLiente(), maskCpf(c.getCpfCliente()), maskRg(c.getRgCLiente()),
					c.getEnderecoCliente(), maskTelefone(c.getTelefoneCLiente()),tipoCliente });
		}
	}
	
	public static String maskRg(String rg) {
		StringBuilder sb = new StringBuilder();
		sb.append(rg.substring(0, 2)).append(".").append(rg.substring(2, 5)).append(".").append(rg.substring(5, 8)).append("-").append(rg.substring(8, 9));
		return sb.toString();
    }
	
	public static String maskCpf(String cpf) {
		StringBuilder sb = new StringBuilder();
		sb.append(cpf.substring(0, 3)).append(".").append(cpf.substring(3, 6)).append(".").append(cpf.substring(6, 9)).append("-").append(cpf.substring(9, 11));
		return sb.toString();
    }
	
	public static String maskTelefone(String telefone) {
		StringBuilder sb = new StringBuilder();
		sb.append("(").append(telefone.substring(0, 2)).append(")").append(telefone.substring(2, 7)).append("-").append(telefone.substring(7, 11));
		return sb.toString();
    }
}