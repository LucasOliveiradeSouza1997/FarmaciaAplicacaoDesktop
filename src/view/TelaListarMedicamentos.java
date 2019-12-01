package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.DAO.ClienteDAO;
import model.DAO.MedicamentoDAO;
import model.bean.Cliente;
import model.bean.Medicamento;

public class TelaListarMedicamentos extends JInternalFrame {

	private static final long serialVersionUID = 3231497305651624071L;
	private JTable table;

	public TelaListarMedicamentos() {
		setBounds(0, 0, 794, 550);
		setClosable(true);
		setTitle("Farmácia Express - Listar medicamentos");
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
		atualizarTabelaMedicamentos();
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() != -1) {
					try {
						Medicamento m = new Medicamento();
						m.setIdMedicamento(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()));
						DefaultTableModel dtmModelMedicamento = (DefaultTableModel) table.getModel();
						MedicamentoDAO medicamentoDao = new MedicamentoDAO();
						medicamentoDao.delete(m);
						dtmModelMedicamento.removeRow(table.getSelectedRow());
						JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Erro ao excluir: " + e.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um medicamento para Excluir");
				}
			}
		});
		btnExcluir.setBounds(72, 460, 90, 30);
		getContentPane().add(btnExcluir);
	}
	
	private void atualizarTabelaMedicamentos() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.getDataVector().removeAllElements();
		modelo.fireTableDataChanged();
		
		MedicamentoDAO medicamentoDao = new MedicamentoDAO();
		for (Medicamento m : medicamentoDao.read(true)) {

			modelo.addRow(new Object[] { m.getIdMedicamento(),m.getNomeMedicamento(),m.getDescricaoMedicamento(),
					"R$ "+ m.getPrecoMedicamento(),m.getValidadeMedicamentoToString()});
		}
	}
}
