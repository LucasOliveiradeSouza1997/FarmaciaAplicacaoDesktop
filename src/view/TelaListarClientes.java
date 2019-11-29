package view;

import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.DAO.ClienteDAO;
import model.bean.Cliente;
import javax.swing.JScrollPane;

public class TelaListarClientes extends JInternalFrame {

	private static final long serialVersionUID = -7468082831627721494L;
	private JTable table;

	public TelaListarClientes() {
		setBounds(0, 0, 794, 550);
		setClosable(true);
		setTitle("Farmácia Express - Listar clientes");
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 175, 758, 293);
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

		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		ClienteDAO clienteDao = new ClienteDAO();
		for (Cliente c : clienteDao.read()) {
			String tipoCliente = "-";
			if (c.getTipoCLiente().equals("N")) {
				tipoCliente = "Cliente Normal";
			} else if (c.getTipoCLiente().equals("E")) {
				tipoCliente = "Cliente Aposentado";
			}
			modelo.addRow(new Object[] { c.getNomeCLiente(), c.getCpfCliente(), c.getRgCLiente(),
					c.getEnderecoCliente(), tipoCliente, c.getTelefoneCLiente(), });
		}
	}
}