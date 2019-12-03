package view;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.DAO.CaixaDAO;
import model.bean.Caixa;

public class TelaCaixasAbertos extends JInternalFrame {

	private static final long serialVersionUID = 8707486211747883301L;
	private JTable table;

	public TelaCaixasAbertos() {
		setBounds(0, 0, 794, 550);
		setClosable(true);
		setTitle("Farmácia Express - Caixas Abertos");
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 778, 427);
		getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Número do Caixa","valor Inicial","Valor em Dinheiro", "Valor em Cartão"  }) {
			private static final long serialVersionUID = 7549926424366818036L;
			boolean[] canEdit = new boolean[] { false, false, false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		atualizarTabelaCaixaAbertos();
	}

	
	private void atualizarTabelaCaixaAbertos() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.getDataVector().removeAllElements();
		modelo.fireTableDataChanged();
		
		CaixaDAO caixaDao = new CaixaDAO();
		for(Caixa c : caixaDao.readCaixasAbertos()) {
			modelo.addRow(new Object[] { c.getCaixaDisponivel().getIdCaixaDisponivel(),c.getValorInicial(), 
					c.getValorDinheiro(),c.getValorCartao()
			});
		}
	}
}
