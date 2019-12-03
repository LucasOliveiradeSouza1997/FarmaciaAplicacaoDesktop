package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JComboBox;
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

public class TelaFechamentoDeCaixa extends JInternalFrame {

	private static final long serialVersionUID = -797226049952510024L;
	private JTable table;
	JNumberFormatField txtCaixaInicial;
	JComboBox<Integer> comboBox ;
	JLabel txtIdCaixa;

	public TelaFechamentoDeCaixa() {
		setVisible(true);
		setBounds(0, 0, 794, 550);
		setClosable(true);
		setTitle("Farmácia Express - Fechamento de Caixa");
		getContentPane().setLayout(null);
		
		JLabel lblNumeroCaixa = new JLabel("Numero do Caixa");
		lblNumeroCaixa.setBounds(10, 22, 150, 30);
		getContentPane().add(lblNumeroCaixa);
		
		txtIdCaixa = new JLabel("");
		txtIdCaixa.setBounds(438, 22, 46, 30);
		getContentPane().add(txtIdCaixa);
		
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
		
		txtCaixaInicial = new JNumberFormatField();
		txtCaixaInicial.setLimit(6);
		txtCaixaInicial.setBounds(170, 155, 150, 30);
		txtCaixaInicial.setEnabled(false);
		getContentPane().add(txtCaixaInicial);
		
		atualizarTabelaCaixa();
		
		JLabel lblTotalCaixa = new JLabel("Total do Caixa");
		lblTotalCaixa.setBounds(10, 182, 150, 30);
		getContentPane().add(lblTotalCaixa);

		JNumberFormatField txtTotalCaixa = new JNumberFormatField();
		txtTotalCaixa.setLimit(6);
		txtTotalCaixa.setBounds(170, 182, 150, 30);
		getContentPane().add(txtTotalCaixa);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dinheiro = table.getModel().getValueAt(0,1).toString().replace("R$", "").replaceAll(",", ".").replaceAll(" ", "");
				String cartao = table.getModel().getValueAt(1,1).toString().replace("R$", "").replaceAll(",", ".").replaceAll(" ", "");
				String caixaAtualUsuario = txtTotalCaixa.getText().replace("R$", "").replaceAll("[.]", "").replaceAll(",", ".").replaceAll(" ", "");
				String caixaInicio = txtCaixaInicial.getText().replace("R$", "").replaceAll("[.]", "").replaceAll(",", ".").replaceAll(" ", "");
				int numeroCaixa = Integer.parseInt(comboBox.getSelectedItem().toString());
				int idCaixa = Integer.parseInt(txtIdCaixa.getText().toString());
				BigDecimal soma = new BigDecimal("0.0");
				BigDecimal dinheiroCaixa = new BigDecimal(dinheiro);
				BigDecimal cartaoCaixa = new BigDecimal(cartao);
				BigDecimal caixaAtual = new BigDecimal(caixaAtualUsuario);
				BigDecimal caixaInicial = new BigDecimal(caixaInicio);
				
				soma = soma.add(dinheiroCaixa).add(cartaoCaixa).add(caixaInicial);
				try {
					if (caixaAtual.compareTo(soma)<0) { //caixa Atual com menos dinheiro que o caixa antes de abrir + valores das vendas(cartoes e dinheiro)
						throw new FechamentoCaixaException("Caixa Atual com Valor Menor que o Correto! Valor Correto seria: RS " + soma.toString());
					}
					Caixa caixa = new Caixa();
					CaixaDisponivel caixaDisponivel = new CaixaDisponivel();
					caixaDisponivel.setIdCaixaDisponivel(numeroCaixa);
					caixaDisponivel.setUtilizando(false);
					caixa.setIdCaixa(idCaixa);
					caixa.setCaixaDisponivel(caixaDisponivel);
					caixa.setValorCaixaFechado(soma);
					caixa.setStatus(false);
					CaixaDAO caixaDao = new CaixaDAO();
					caixaDao.updateCaixaFechamento(caixa);
					dispose();
					JOptionPane.showMessageDialog(null, "Caixa "+ caixa.getCaixaDisponivel().getIdCaixaDisponivel()+ " fechado com sucesso!");
				} catch (FechamentoCaixaException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro no Fechamento do Caixa",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Erro no Fechamento do Caixa",
							"Erro no Fechamento do Caixa", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnConfirmar.setBounds(10, 236, 90, 30);
		getContentPane().add(btnConfirmar);
		
		JLabel lblCaixaInicial = new JLabel("Caixa Inicial");
		lblCaixaInicial.setBounds(10, 155, 150, 30);
		getContentPane().add(lblCaixaInicial);
		
		JLabel lblIdCaixa = new JLabel("Id Caixa");
		lblIdCaixa.setBounds(382, 22, 46, 30);
		getContentPane().add(lblIdCaixa);
		
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
		try {
			txtCaixaInicial.setText(c.getValorInicial().toString());
			txtIdCaixa.setText(Integer.toString(c.getIdCaixa()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
