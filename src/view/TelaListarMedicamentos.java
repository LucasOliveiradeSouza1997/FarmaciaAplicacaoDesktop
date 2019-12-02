package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.DAO.MedicamentoDAO;
import model.bean.Medicamento;

public class TelaListarMedicamentos extends JInternalFrame {

	private static final long serialVersionUID = 3231497305651624071L;
	private JTable table;
	private JDialog dialog = null;
	
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
			boolean[] canEdit = new boolean[] { false, false, false, false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		atualizarTabelaMedicamentos();
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() != -1) {
					try {
						Medicamento m = new Medicamento();
						m.setIdMedicamento(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()));
						m.setNomeMedicamento(table.getValueAt(table.getSelectedRow(), 1).toString());
						m.setDescricaoMedicamento(table.getValueAt(table.getSelectedRow(), 2).toString());
						m.setPrecoMedicamento(new BigDecimal(table.getValueAt(table.getSelectedRow(), 3).toString().replace("R$", "").replace("[.]", "").replace(",", ".").replaceAll(" ", "")));
						m.setValidadeMedicamento(table.getValueAt(table.getSelectedRow(), 4).toString());
												
				        if ((dialog == null) || (!(dialog.isVisible()))) {
				            dialog = new JDialog();
				            inicializaComponentesAlterar(dialog,m);   
				        }
				        atualizarTabelaMedicamentos();
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + e.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um medicamento para Atualizar");
				}
			}		
		});
		btnAtualizar.setBounds(42, 460, 90, 30);
		getContentPane().add(btnAtualizar);
		
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
		btnExcluir.setBounds(160, 460, 90, 30);
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
	
	private void inicializaComponentesAlterar(JDialog dialog, Medicamento m) {
		dialog.setTitle("Atualizar Medicamento");
        dialog.setPreferredSize(new Dimension(800, 350));
        dialog.pack();
        dialog.setModal(true);
        dialog.setFocusable(true);
        try {
			dialog.setIconImage(Toolkit.getDefaultToolkit().getImage("imagens/farmacia-icone.png"));
        } catch (NullPointerException ex) {
        	System.out.println("nao encontrou o icone");
        }
        
        JPanel buttonPane = new JPanel();
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MedicamentoDAO medicamentoDao = new MedicamentoDAO();
				medicamentoDao.update(m);
				JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
				dialog.dispose();
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		buttonPane.add(okButton);
		buttonPane.add(cancelButton);
		dialog.add(buttonPane, BorderLayout.SOUTH);
		
        //Centralizando a dialog no centro da tela
        java.awt.Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation((int) (d.getWidth() - dialog.getWidth()) / 2, (int) (d.getHeight() - dialog.getHeight()) / 2);
        dialog.setVisible(true);
	}
}
