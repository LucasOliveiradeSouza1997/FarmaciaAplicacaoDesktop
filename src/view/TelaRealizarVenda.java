package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
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

import Auxiliares.GerarNotaFiscal;
import Auxiliares.JNumberFormatField;
import Exception.ClienteNaoEncontradoException;
import Exception.DinheiroCaixaException;
import Exception.DinheiroClienteException;
import Exception.FormaDePagamentoException;
import Exception.MedicamentoInvalidoException;
import Exception.QuantidadeInvalidaEstoqueException;
import Exception.QuantidadeInvalidaMedicamentoEstoqueException;
import model.DAO.CaixaDAO;
import model.DAO.CaixaDisponivelDAO;
import model.DAO.ClienteDAO;
import model.DAO.MedicamentoDAO;
import model.DAO.VendaDAO;
import model.bean.Caixa;
import model.bean.CaixaDisponivel;
import model.bean.Cliente;
import model.bean.Medicamento;
import model.bean.Usuario;
import model.bean.Venda;
import model.bean.VendaExibicao;

public class TelaRealizarVenda extends JInternalFrame {

	private static final long serialVersionUID = 5060553779977942399L;
	JComboBox<Integer> comboBox;
	JComboBox<String> comboBoxCliente;
	JComboBox<String> comboBoxMedicamento ;
	private JTable table;
	private JDialog dialog = null;
	List<VendaExibicao> vendaExibicao;

	public TelaRealizarVenda(Usuario usuario) {
		setBounds(0, 0, 794, 550);
		setClosable(true);
		setTitle("Farmácia Express - Realizar Venda");
		getContentPane().setLayout(null);

		JLabel lblNumeroCaixa = new JLabel("Numero do Caixa");
		lblNumeroCaixa.setBounds(10, 22, 150, 30);
		getContentPane().add(lblNumeroCaixa);

		JLabel lblCliente = new JLabel("Cliente");
		lblCliente.setBounds(10, 52, 150, 30);
		getContentPane().add(lblCliente);

		comboBox = new JComboBox<Integer>();
		comboBox.setBounds(170, 27, 150, 20);
		getContentPane().add(comboBox);

		comboBoxCliente = new JComboBox<String>();
		comboBoxCliente.setBounds(170, 57, 150, 20);
		getContentPane().add(comboBoxCliente);

		CaixaDisponivelDAO caixaDisponivelDAO = new CaixaDisponivelDAO();

		for (CaixaDisponivel c : caixaDisponivelDAO.readCaixasAbertos()) {
			comboBox.addItem(c.getIdCaixaDisponivel());
		}

		ClienteDAO clienteDao = new ClienteDAO();
		Map<Integer, String> clientes = new HashMap<Integer, String>();
		int i = 0;
		for (Cliente c : clienteDao.read()) {
			comboBoxCliente.addItem(c.getNomeCLiente());
			clientes.put(i++, c.getCpfCliente());
		}
		ButtonGroup buttonGroup = new ButtonGroup();

		JLabel lblFormaPagamento = new JLabel("Forma de Pagamento");
		lblFormaPagamento.setBounds(10, 85, 150, 30);
		getContentPane().add(lblFormaPagamento);

		JRadioButton rdbtnCartao = new JRadioButton("Cart\u00E3o");
		buttonGroup.add(rdbtnCartao);
		rdbtnCartao.setBounds(170, 84, 100, 30);
		getContentPane().add(rdbtnCartao);

		JRadioButton rdbtnDinheiro = new JRadioButton("Dinheiro");
		buttonGroup.add(rdbtnDinheiro);
		rdbtnDinheiro.setBounds(276, 85, 100, 30);
		getContentPane().add(rdbtnDinheiro);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 167, 768, 223);
		getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "id", "lote", "Nome Medicamento", "Descrição", "Quantidade", "Preço" }) {
			private static final long serialVersionUID = 7549926424366818036L;
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		atualizarTabelaMedicamentos();

		JLabel lblTotal = new JLabel("Total");
		lblTotal.setBounds(534, 401, 75, 30);
		getContentPane().add(lblTotal);

		JNumberFormatField txtTotal = new JNumberFormatField();
		txtTotal.setLimit(6);
		txtTotal.setBounds(607, 401, 150, 30);
		txtTotal.setEnabled(false);
		getContentPane().add(txtTotal);

		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numeroCaixa;
				String tipoPagamento = "";
				try {
					numeroCaixa = Integer.parseInt(comboBox.getSelectedItem().toString());
					String cpfCliente = clientes.get(comboBoxCliente.getSelectedIndex());
					cpfCliente = cpfCliente.replaceAll("[.]", "").replaceAll("-", "").replaceAll(" ", "");
					if (cpfCliente.equals("")) {
						throw new ClienteNaoEncontradoException("Não selecionou Cliente");
					}
					if (!(rdbtnCartao.isSelected() || rdbtnDinheiro.isSelected())) {
						throw new FormaDePagamentoException("Não selecionou forma de pagamento");
					}
					String total = txtTotal.getText().replace("R$", "").replaceAll("[.]", "").replaceAll(",", ".")
							.replaceAll(" ", "");
					// BigDecimal totalVenda = new BigDecimal(total);
					BigDecimal totalVenda = new BigDecimal("10");
					BigDecimal dinheiroCLiente = null;
					BigDecimal troco = null;
					Cliente cliente = new Cliente();
					ClienteDAO clienteDao = new ClienteDAO();
					cliente = clienteDao.read(cpfCliente);
					if (rdbtnDinheiro.isSelected() && cliente.getTipoCLiente().equals("N")
							&& usuario.getTipoUsuario().equals("G")) {
						totalVenda = totalVenda.multiply(new BigDecimal("0.95"));
						totalVenda = totalVenda.setScale(2, BigDecimal.ROUND_HALF_EVEN);
						JOptionPane.showMessageDialog(null,
								"Desconto de 5% para clientes Normais com Pagamento Em Dinheiro,"
										+ "valor a ser pago: R$ " + totalVenda.toString().replaceAll("[.]", ","));
					} else if (cliente.getTipoCLiente().equals("E") && usuario.getTipoUsuario().equals("G")) {
						totalVenda = totalVenda.multiply(new BigDecimal("0.80"));
						totalVenda = totalVenda.setScale(2, BigDecimal.ROUND_HALF_EVEN);
						JOptionPane.showMessageDialog(null, "Desconto de 20% para clientes Aposentados,"
								+ "valor a ser pago: R$ " + totalVenda.toString().replaceAll("[.]", ","));
					}
					if (rdbtnCartao.isSelected()) {
						tipoPagamento = "C";
					} else if (rdbtnDinheiro.isSelected()) {
						tipoPagamento = "D";
						String dinheiroDoCliente = JOptionPane.showInputDialog("Digite o dinheiro dado pelo cliente:");
						if (dinheiroDoCliente == null || dinheiroDoCliente.equals("")) {
							throw new DinheiroClienteException("Valor Inválido Para o Dinheiro do Cliente");
						} else {
							dinheiroDoCliente = dinheiroDoCliente.replaceAll("R$", "").replaceAll(" ", "")
									.replaceAll("[.]", "").replaceAll(",", ".");
							try {
								dinheiroCLiente = new BigDecimal(dinheiroDoCliente);
								dinheiroCLiente = dinheiroCLiente.setScale(2, BigDecimal.ROUND_HALF_EVEN);
							} catch (Exception ex) {
								throw new DinheiroClienteException("Valor Inválido Para o Dinheiro do Cliente");
							}
						}
						System.out.println("aa" + dinheiroCLiente);
						if (dinheiroCLiente.compareTo(totalVenda) < 0) {
							throw new DinheiroClienteException("Dinheiro Insuficiente");
						}
						troco = dinheiroCLiente.subtract(totalVenda);
					}
					Caixa caixa = new Caixa();
					CaixaDAO caixaDao = new CaixaDAO();
					caixa = caixaDao.readCaixaAberto(numeroCaixa);

					if (tipoPagamento.equals("D")) {
						BigDecimal valorTotalEmCaixa = caixa.getValorInicial().add(caixa.getValorDinheiro());
						if (valorTotalEmCaixa.compareTo(troco) < 0) {
							throw new DinheiroCaixaException("Dinheiro Insuficiente no Caixa");
						}
					}
					Venda venda = new Venda();
					venda.setCompraAtiva(true);
					venda.setCaixa(caixa);
					venda.setCliente(cliente);
					venda.setNumeroNotaFiscal(GerarNotaFiscal.geraNFe());
					venda.setValorTotal(totalVenda);
					venda.setTipoPagamento(tipoPagamento);
					VendaDAO vendaDao = new VendaDAO();
					vendaDao.create(venda);
					dispose();
					vendaExibicao = null;
					if (troco != null) {
						System.out.println(totalVenda.toString());
						JOptionPane.showMessageDialog(null,
								"Troco do Cliente: R$ " + troco.toString().replaceAll("[.]", ","));
					}
					JOptionPane.showMessageDialog(null, " Venda realizada com sucesso!");
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(null, "Numero do Caixa Nao Selecionado", "Erro na Venda",
							JOptionPane.ERROR_MESSAGE);
				} catch (FormaDePagamentoException | DinheiroClienteException | DinheiroCaixaException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro na Venda", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnConfirmar.setBounds(667, 455, 90, 30);
		getContentPane().add(btnConfirmar);

		JLabel lblDesconto = new JLabel("Desconto");
		lblDesconto.setBounds(10, 126, 150, 30);
		getContentPane().add(lblDesconto);

		JLabel txtDesconto = new JLabel("");
		txtDesconto.setBounds(168, 126, 589, 30);
		getContentPane().add(txtDesconto);

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ((dialog == null) || (!(dialog.isVisible()))) {
					dialog = new JDialog();
					inicializaComponentesAlterar(dialog);
				}
				atualizarTabelaMedicamentos();
			}
		});
		btnAdicionar.setBounds(30, 405, 90, 30);
		getContentPane().add(btnAdicionar);

		JButton btnRemover = new JButton("Remover");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// remover
			}
		});
		btnRemover.setBounds(137, 405, 90, 30);
		getContentPane().add(btnRemover);

		if (usuario.getTipoUsuario().equals("G")) {
			txtDesconto.setText(
					"Desconto de 20% para aposentado e Desconto de 5% para clientes normais com pagamento em dinheiro");
		} else if (usuario.getTipoUsuario().equals("A")) {
			txtDesconto.setText("Os atendentes Não oferecem desconto");
		}
	}

	private void atualizarTabelaMedicamentos() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.getDataVector().removeAllElements();
		modelo.fireTableDataChanged();

		if (vendaExibicao == null) {
			vendaExibicao = new ArrayList<VendaExibicao>();
		}
		for (VendaExibicao ve : vendaExibicao) {

			// modelo.addRow(new Object[] {
			// m.getIdMedicamento(),m.getNomeMedicamento(),m.getDescricaoMedicamento(),
			// "R$ "+ m.getPrecoMedicamento(),m.getValidadeMedicamentoToString()});
		}
	}

	private void inicializaComponentesAlterar(JDialog dialog) {
		dialog.setTitle("Selecionar Medicamento");
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
		buttonPane.setBounds(100, 100, 100, 100);
		JPanel jpanelAlterar = new JPanel();
		jpanelAlterar.setLayout(new BorderLayout());
		jpanelAlterar.setBorder(new EmptyBorder(5, 5, 5, 5));

		jpanelAlterar.setBounds(0, 0, dialog.getWidth(), dialog.getHeight() - 30);

		JLabel lblIdMedicamento = new JLabel("ID do Medicamento");
		lblIdMedicamento.setBounds(0, 0, 150, 30);
		jpanelAlterar.add(lblIdMedicamento);

		JTextField txtIdMedicamento = new JTextField();
		txtIdMedicamento.setBounds(150, 0, 400, 30);
		txtIdMedicamento.setEnabled(false);
		jpanelAlterar.add(txtIdMedicamento);

		JLabel lblSelecioneMedicamento = new JLabel("Selecione Medicamento ");
		lblSelecioneMedicamento.setBounds(0, 30, 150, 30);
		jpanelAlterar.add(lblSelecioneMedicamento);
				
		comboBoxMedicamento = new JComboBox<String>();
		comboBoxMedicamento.setBounds(150, 30, 400, 30);
		jpanelAlterar.add(comboBoxMedicamento);
		
		Map<Integer, Integer> medicamentosMap = new HashMap<Integer, Integer>();
		MedicamentoDAO medicamentoDAO = new MedicamentoDAO();
		int i = 0;
		try {
			for (Medicamento m : medicamentoDAO.readMedicamentosNaoVencidosComEstoque()) {
				comboBoxMedicamento.addItem(m.getNomeMedicamento());
				medicamentosMap.put(i++, m.getIdMedicamento());
			}
			txtIdMedicamento.setText(Integer.toString(medicamentosMap.get(comboBoxCliente.getSelectedIndex())));
		} catch (MedicamentoInvalidoException ex) {
			dialog.dispose();
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro ao Selecionar Medicamento",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		comboBoxMedicamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(txtIdMedicamento!=null) {
						txtIdMedicamento.setText(Integer.toString(medicamentosMap.get(comboBoxCliente.getSelectedIndex())));
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		

		JLabel lblQuantidade = new JLabel("Quantidade ");
		lblQuantidade.setBounds(0, 60, 150, 30);
		jpanelAlterar.add(lblQuantidade);
		
		JNumberFormatField txtQuantidade = new JNumberFormatField(new DecimalFormat("#,##000"));
		txtQuantidade.setLimit(5);
		txtQuantidade.setBounds(150, 60, 150, 30);
		jpanelAlterar.add(txtQuantidade);

		JLabel lblAlteracao = new JLabel("Selecionando Medicamento");
		lblAlteracao.setBounds(0, 90, 150,30);
		jpanelAlterar.add(lblAlteracao);

		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// quantidade invalida em estoque
				try {
					dialog.dispose();
					int id = Integer.parseInt(txtIdMedicamento.getText());
					int quantidadeUsuario = Integer.parseInt(txtQuantidade.getText());
					MedicamentoDAO mDao = new MedicamentoDAO();
					Medicamento medicamento = mDao.readMedicamentosNaoVencidosComEstoque(id);
					System.out.println("id: "+medicamento.getIdMedicamento());
					System.out.println("qnt estoque: "+medicamento.getEstoque().getQuantidade());
					if(medicamento.getEstoque().getQuantidade() < quantidadeUsuario) {
						throw new QuantidadeInvalidaMedicamentoEstoqueException("Não temos essa quantidade informada em estoque");
					}
					
//					vendaExibicao;
				} catch (QuantidadeInvalidaMedicamentoEstoqueException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro ao Selecionar Medicamento",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception ex2) {
					JOptionPane.showMessageDialog(null, "Erro ao selecionar Medicamento:",
							"Erro ao Selecionar Medicamento", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});

		buttonPane.add(okButton);
		buttonPane.add(cancelButton);
		jpanelAlterar.add(buttonPane, BorderLayout.SOUTH);
		dialog.add(jpanelAlterar, BorderLayout.CENTER);

		// Centralizando a dialog no centro da tela
		java.awt.Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((int) (d.getWidth() - dialog.getWidth()) / 2,
				(int) (d.getHeight() - dialog.getHeight()) / 2);
		dialog.setVisible(true);
	}
}
