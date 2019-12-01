package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Auxiliares.JNumberFormatField;
import Exception.QuantidadeInvalidaEstoqueException;
import model.DAO.ClienteDAO;
import model.DAO.EstoqueDAO;
import model.DAO.MedicamentoDAO;
import model.bean.Cliente;
import model.bean.Estoque;
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
		JNumberFormatField txtQuantidade = new JNumberFormatField(new DecimalFormat("#,##000"));
		txtQuantidade.setLimit(5);
		txtQuantidade.setBounds(24, 460, 100, 30);
		getContentPane().add(txtQuantidade);
		
		JButton btnAdicionarNoEstoque = new JButton("Adicionar");
		btnAdicionarNoEstoque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() != -1) {
					try {
						Estoque e = new Estoque();
						int quantidadeAdicionar = Integer.parseInt(txtQuantidade.getText());
						int quantidadeEstoque = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 3).toString());
						e.setLote(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 1).toString()));
						e.setQuantidade(quantidadeEstoque + quantidadeAdicionar);
						EstoqueDAO estoqueDao = new EstoqueDAO();
						estoqueDao.updateEstoque(e);
						JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");		
						txtQuantidade.setText("");
						atualizarTabelaEstoque();	
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Erro ao Adicionar:" + e.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um medicamento");
				}
			}
		});
		btnAdicionarNoEstoque.setBounds(158, 460, 90, 30);
		getContentPane().add(btnAdicionarNoEstoque);
		
		JButton btnRetirarDoEstoque = new JButton("Retirar");
		btnRetirarDoEstoque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() != -1) {
					try {
						Estoque e = new Estoque();
						int quantidadeRemover = Integer.parseInt(txtQuantidade.getText());
						int quantidadeEstoque = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 3).toString());
						e.setLote(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 1).toString()));
						if (quantidadeRemover> quantidadeEstoque) {
							throw new QuantidadeInvalidaEstoqueException("Quantidade Maior que a Disponivel em Estoque");
						}
						e.setQuantidade(quantidadeEstoque- quantidadeRemover);
						EstoqueDAO estoqueDao = new EstoqueDAO();
						estoqueDao.updateEstoque(e);
						JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");		
						txtQuantidade.setText("");
						atualizarTabelaEstoque();
					} catch (QuantidadeInvalidaEstoqueException e) {
						JOptionPane.showMessageDialog(null,  e.getMessage());	
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Erro ao Retirar:" + e.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um medicamento");
				}
			}
		});
		btnRetirarDoEstoque.setBounds(280, 460, 90, 30);
		getContentPane().add(btnRetirarDoEstoque);
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
