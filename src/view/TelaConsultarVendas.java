package view;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.DAO.ClienteDAO;
import model.DAO.VendaDAO;
import model.bean.Cliente;
import model.bean.Venda;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaConsultarVendas extends JInternalFrame {

	private static final long serialVersionUID = 2682277685220510392L;
	private JTable table;
	private JDialog dialog = null;

	public TelaConsultarVendas() {
		ButtonGroup buttonGroup = new ButtonGroup();
		setBounds(0, 0, 794, 550);
		setClosable(true);
		setTitle("Farmácia Express - Consultar Vendas");
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 48, 778, 379);
		getContentPane().add(scrollPane);
		
		JLabel lblAposentado = new JLabel("Consultar Por:");
		lblAposentado.setBounds(10, 7, 102, 30);
		getContentPane().add(lblAposentado);

		JRadioButton btnTodas = new JRadioButton("Todas");
		buttonGroup.add(btnTodas);
		btnTodas.setBounds(132, 7, 75, 30);
		getContentPane().add(btnTodas);

		JRadioButton btnCartao = new JRadioButton("Apenas Cartão");
		buttonGroup.add(btnCartao);
		btnCartao.setBounds(209, 7, 116, 30);
		getContentPane().add(btnCartao);
		
		JRadioButton btnDinheiro = new JRadioButton("Apenas Dinheiro");
		buttonGroup.add(btnDinheiro);
		btnDinheiro.setBounds(327, 7, 128, 30);
		getContentPane().add(btnDinheiro);
		

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "id Venda", "Cpf Cliente", "Valor Total", "Tipo Pagamento"}) {
			private static final long serialVersionUID = 7549926424366818036L;
			boolean[] canEdit = new boolean[] { false, false, false, false};

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tipoConsulta="";
				if (btnTodas.isSelected()) {
					tipoConsulta="TODAS";
				} else if (btnCartao.isSelected()) {
					tipoConsulta="CARTAO";
				} else if (btnDinheiro.isSelected()) {
					tipoConsulta="DINHEIRO";
				}else {
					JOptionPane.showMessageDialog(null, "Selecione uma Opção", "Erro na Consulta das Vendas",
							JOptionPane.ERROR_MESSAGE);
				}
				DefaultTableModel modelo = (DefaultTableModel) table.getModel();
				modelo.getDataVector().removeAllElements();
				modelo.fireTableDataChanged();
				String tipoPagamento="";				
				VendaDAO vendaDao = new VendaDAO();
				for (Venda v : vendaDao.read(tipoConsulta)) {
					if(v.getTipoPagamento().equals("C")) {
						tipoPagamento="Cartão";
					}else if(v.getTipoPagamento().equals("D")) {
						tipoPagamento="Dinheiro";
					}
					modelo.addRow(new Object[] { 
							v.getIdVenda(),v.getCliente().getCpfCliente(),v.getValorTotal(),tipoPagamento
					});
				}
				
			}
		});
		btnConsultar.setBounds(458, 7, 90, 30);
		getContentPane().add(btnConsultar);
	}
}