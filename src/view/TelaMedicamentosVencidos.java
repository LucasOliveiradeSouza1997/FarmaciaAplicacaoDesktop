package view;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.DAO.MedicamentoDAO;
import model.bean.Medicamento;

public class TelaMedicamentosVencidos extends JInternalFrame {

	private static final long serialVersionUID = 1078027748660686854L;
	private JTable table;

	public TelaMedicamentosVencidos() {
		setBounds(0, 0, 794, 550);
		setClosable(true);
		setTitle("Farmácia Express - Relatorio de Medicamentos Vencidos");
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 778, 427);
		getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "id","Nome", "Descrição", "Preço", "Validade" }) {
			private static final long serialVersionUID = 7549926424366818036L;
			boolean[] canEdit = new boolean[] { false, true, true, true, true };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		atualizarTabelaMedicamentosVencidos();
	}
	
	private void atualizarTabelaMedicamentosVencidos() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.getDataVector().removeAllElements();
		modelo.fireTableDataChanged();
		
		MedicamentoDAO medicamentoDao = new MedicamentoDAO();
		for (Medicamento m : medicamentoDao.read(false)) {

			modelo.addRow(new Object[] { m.getIdMedicamento(),m.getNomeMedicamento(),m.getDescricaoMedicamento(),
					"R$ "+ m.getPrecoMedicamento(),m.getValidadeMedicamentoToString()});
		}
	}
}
