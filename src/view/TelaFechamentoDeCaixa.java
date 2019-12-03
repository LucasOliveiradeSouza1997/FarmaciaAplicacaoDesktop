package view;

import java.beans.PropertyVetoException;

import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Auxiliares.JNumberFormatField;
import Exception.FechamentoCaixaException;
import model.DAO.CaixaDAO;
import model.DAO.CaixaDisponivelDAO;
import model.bean.Caixa;
import model.bean.CaixaDisponivel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaFechamentoDeCaixa extends JInternalFrame {

	private static final long serialVersionUID = -797226049952510024L;
	private JTable table;
	JComboBox<Integer> comboBox ;

	public TelaFechamentoDeCaixa() {
		setVisible(true);
		setBounds(0, 0, 794, 550);
		setClosable(true);
		setTitle("Farmácia Express - Fechamento de Caixa");
		getContentPane().setLayout(null);
		
		JLabel lblNumeroCaixa = new JLabel("Numero do Caixa");
		lblNumeroCaixa.setBounds(10, 22, 150, 30);
		getContentPane().add(lblNumeroCaixa);
		
		comboBox = new JComboBox<Integer>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(table!=null) {
					atualizarTabelaCaixa();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		comboBox.setBounds(170, 27, 150, 20);
		getContentPane().add(comboBox);
		
		CaixaDisponivelDAO caixaDisponivelDAO = new CaixaDisponivelDAO();
		
		for (CaixaDisponivel c : caixaDisponivelDAO.readCaixasAbertos()) {
			comboBox.addItem(c.getIdCaixaDisponivel());
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 58, 778, 86);
		getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Espécie", "TOTAL"}) {
			private static final long serialVersionUID = 7549926424366818036L;
			boolean[] canEdit = new boolean[] { false, false, false, };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		atualizarTabelaCaixa();
		
		JLabel lblTotalCaixa = new JLabel("Total do Caixa");
		lblTotalCaixa.setBounds(10, 182, 150, 30);
		getContentPane().add(lblTotalCaixa);

		JNumberFormatField txtTotalCaixa = new JNumberFormatField();
		txtTotalCaixa.setLimit(6);
		txtTotalCaixa.setBounds(170, 182, 150, 30);
		getContentPane().add(txtTotalCaixa);
	}
	
	private void atualizarTabelaCaixa() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.getDataVector().removeAllElements();
		modelo.fireTableDataChanged();
		int numeroCaixa;
		try {
			numeroCaixa = Integer.parseInt(comboBox.getSelectedItem().toString());
		} catch (NullPointerException ex) {
			this.dispose();
			throw new FechamentoCaixaException("Nenhum Caixa Aberto");
		}
		CaixaDAO caixaDao = new CaixaDAO();
		Caixa c = caixaDao.readCaixaAberto(numeroCaixa);
		String[] linha1 = {"Dinheiro", "Cartão"};
		String[] linha2 = {"R$ " + c.getValorDinheiro(), "R$" + c.getValorCartao()};
		for(int i=0; i<2;i++) {
			modelo.addRow(new Object[] {linha1[i],linha2[i]});
		}
	}
}
