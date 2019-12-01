package view;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.DAO.EstoqueDAO;
import model.DAO.MedicamentoDAO;
import model.bean.Medicamento;

public class TelaGerenciarEstoque extends JInternalFrame {

	private static final long serialVersionUID = 5567552142367967847L;
	private JTable table;

	public TelaGerenciarEstoque() {
		setBounds(0, 0, 794, 550);
		setClosable(true);
		setTitle("Farmácia Express - Gerenciar estoque");
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 778, 427);
		getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "id Medicamento","lote","Nome Medicamento" ,"Quantidade","Distribuidor"  }) {
			private static final long serialVersionUID = 7549926424366818036L;
			boolean[] canEdit = new boolean[] { false, false, false, false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		atualizarTabelaEstoque();
	}
	
	private void atualizarTabelaEstoque() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.getDataVector().removeAllElements();
		modelo.fireTableDataChanged();
		
		MedicamentoDAO medicamentoDao = new MedicamentoDAO();
	
		for (Medicamento m : medicamentoDao.readMedicamentoXEstoque()) {

			modelo.addRow(new Object[] { m.getIdMedicamento(),m.getLote(),m.getNomeMedicamento(),
					m.getEstoque().getQuantidade(),m.getEstoque().getDistribuidor()});
		}
	}
}
