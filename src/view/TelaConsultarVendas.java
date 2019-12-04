package view;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Auxiliares.JNumberFormatField;
import Auxiliares.ValidaData;
import Exception.DataDdMmYyyyInvalida;
import Exception.DataDigitadaInvalidaException;
import model.DAO.ClienteDAO;
import model.DAO.MedicamentoDAO;
import model.DAO.VendaDAO;
import model.bean.Cliente;
import model.bean.Medicamento;
import model.bean.Venda;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

public class TelaConsultarVendas extends JInternalFrame {

	private static final long serialVersionUID = 2682277685220510392L;
	private JTable table;
	private JDialog dialog = null;
	JRadioButton btnTodas ;
	JRadioButton btnCartao ;
	JRadioButton btnDinheiro ;

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

		btnTodas = new JRadioButton("Todas");
		buttonGroup.add(btnTodas);
		btnTodas.setBounds(132, 7, 75, 30);
		getContentPane().add(btnTodas);

		btnCartao = new JRadioButton("Apenas Cartão");
		buttonGroup.add(btnCartao);
		btnCartao.setBounds(209, 7, 116, 30);
		getContentPane().add(btnCartao);
		
		btnDinheiro = new JRadioButton("Apenas Dinheiro");
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
				atualizarTabela();	
			}
		});
		btnConsultar.setBounds(458, 7, 90, 30);
		getContentPane().add(btnConsultar);
		
		JButton btnExpandir = new JButton("Expandir Itens Comprados");
		btnExpandir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() != -1) {
					try {
						Venda venda = new Venda();
						venda.setIdVenda(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()));
				        if ((dialog == null) || (!(dialog.isVisible()))) {
				            dialog = new JDialog();
				            inicializaComponentesExpandir(dialog,venda);   
				        }
				        atualizarTabela();
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Erro ao selecionar para visualizar os Produtos: " + e.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione uma venda para Visualizar os Produtos");
				}
			}
		});
		btnExpandir.setBounds(42, 460, 189, 30);
		getContentPane().add(btnExpandir);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() != -1) {
					try {
						Venda venda = new Venda();
						venda.setIdVenda(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()));
						DefaultTableModel dtmModelCliente = (DefaultTableModel) table.getModel();
						VendaDAO vendaDao = new VendaDAO();
						vendaDao.delete(venda);
						dtmModelCliente.removeRow(table.getSelectedRow());
						JOptionPane.showMessageDialog(null, "Cancelado com sucesso!");
						atualizarTabela();
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Erro ao cancelar venda:" + e.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione uma venda para Cancelar");
				}
			}
		});
		btnCancelar.setBounds(259, 460, 102, 30);
		getContentPane().add(btnCancelar);
	}
	
	private void atualizarTabela() {
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
	private void inicializaComponentesExpandir(JDialog dialog, Venda venda) {
				
		dialog.setTitle("Listando Medicamento da Venda");
        dialog.setPreferredSize(new Dimension(800, 350));
        dialog.pack();
        dialog.setModal(true);
        dialog.setFocusable(true);
		dialog.setLayout(null);
        try {
			dialog.setIconImage(Toolkit.getDefaultToolkit().getImage("imagens/farmacia-icone.png"));
        } catch (NullPointerException ex) {
        	System.out.println("nao encontrou o icone");
        }
        
        JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPane.setBounds(100,100, 100, 100);
        JPanel jPanelListar = new JPanel();
        jPanelListar.setLayout(new BorderLayout());
        jPanelListar.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        jPanelListar.setBounds(0, 0, dialog.getWidth(), dialog.getHeight() - 30);
         
        JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 48, 778, 180);
		jPanelListar.add(scrollPane);
		
		JTable table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "id Medicamento", "Nome medicamento", "Descrição Medicamento", "Preço Medicamento", "Quantidade"}) {
			private static final long serialVersionUID = 7549926424366818036L;
			boolean[] canEdit = new boolean[] { false, false, false, false,false};

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.getDataVector().removeAllElements();
		modelo.fireTableDataChanged();
		
		MedicamentoDAO medicamentoDao = new MedicamentoDAO();
		for (Medicamento m : medicamentoDao.read(venda)) {

			modelo.addRow(new Object[] {
					m.getIdMedicamento(),m.getNomeMedicamento(),m.getDescricaoMedicamento(), "R$ " + m.getPrecoMedicamento(),
					m.getEstoque().getQuantidade()
			});
		}
        
		JLabel lblListando = new JLabel("Listando Medicamentos");
		lblListando.setBounds(0, 120, 150, 30);
		jPanelListar.add(lblListando);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		
		buttonPane.add(cancelButton);
		jPanelListar.add(buttonPane, BorderLayout.SOUTH);
		dialog.add(jPanelListar,BorderLayout.CENTER);
		
        //Centralizando a dialog no centro da tela
        java.awt.Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation((int) (d.getWidth() - dialog.getWidth()) / 2, (int) (d.getHeight() - dialog.getHeight()) / 2);  
        dialog.setVisible(true);
	}	
}