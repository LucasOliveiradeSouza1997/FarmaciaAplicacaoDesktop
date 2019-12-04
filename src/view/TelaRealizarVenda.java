package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import Auxiliares.GerarNotaFiscal;
import Auxiliares.JNumberFormatField;
import Exception.ClienteNaoEncontradoException;
import Exception.DinheiroClienteException;
import Exception.FormaDePagamentoException;
import model.DAO.CaixaDAO;
import model.DAO.CaixaDisponivelDAO;
import model.DAO.ClienteDAO;
import model.DAO.VendaDAO;
import model.bean.Caixa;
import model.bean.CaixaDisponivel;
import model.bean.Cliente;
import model.bean.Usuario;
import model.bean.Venda;

public class TelaRealizarVenda extends JInternalFrame {

	private static final long serialVersionUID = 5060553779977942399L;
	JComboBox<Integer> comboBox;
	JComboBox<String> comboBoxCliente;

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
					String total = txtTotal.getText().replace("R$", "").replaceAll("[.]", "").replaceAll(",", ".")
							.replaceAll(" ", "");
					BigDecimal totalVenda = new BigDecimal(total);
					BigDecimal dinheiroCLiente = null;
					// regras do preço

					if (rdbtnCartao.isSelected()) {
						tipoPagamento = "C";
					} else if (rdbtnDinheiro.isSelected()) {
						boolean erroNoDinheiroCliente = false;
						tipoPagamento = "D";
						String dinheiroDoCliente = JOptionPane.showInputDialog("Digite o dinheiro dado pelo cliente:");
						if (dinheiroDoCliente == null || dinheiroDoCliente.equals("")) {
							erroNoDinheiroCliente = true;
						} else {
							dinheiroDoCliente = dinheiroDoCliente.replaceAll("R$", "").replaceAll(" ", "").replaceAll("[.]", "").replaceAll(",", ".");
							try {
								dinheiroCLiente = new BigDecimal(dinheiroDoCliente);
								dinheiroCLiente = dinheiroCLiente.setScale(2, BigDecimal.ROUND_HALF_EVEN);
							} catch (Exception ex) {
								erroNoDinheiroCliente = true;
							}
						}
						if (erroNoDinheiroCliente) {
							throw new DinheiroClienteException("Valor Inválido Para o Dinheiro do Cliente");
						}
					} else {
						throw new FormaDePagamentoException("Não selecionou forma de pagamento");
					}
					Caixa caixa = new Caixa();
					CaixaDAO caixaDao = new CaixaDAO();
					caixa = caixaDao.readCaixaAberto(numeroCaixa);
					Cliente cliente = new Cliente();
					ClienteDAO clienteDao = new ClienteDAO();
					cliente = clienteDao.read(cpfCliente);
					Venda venda = new Venda();
					venda.setCaixa(caixa);
					venda.setCliente(cliente);
					venda.setNumeroNotaFiscal(GerarNotaFiscal.geraNFe());
					venda.setValorTotal(totalVenda);
					venda.setTipoPagamento(tipoPagamento);
					VendaDAO vendaDao = new VendaDAO();
					vendaDao.create(venda);
					dispose();
					JOptionPane.showMessageDialog(null, " Venda realizada com sucesso!");
				} catch (NullPointerException ex) {
					JOptionPane.showMessageDialog(null, "Numero do Caixa Nao Selecionado", "Erro na Venda",
							JOptionPane.ERROR_MESSAGE);
				} catch (FormaDePagamentoException | DinheiroClienteException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro na Venda",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnConfirmar.setBounds(667, 455, 90, 30);
		getContentPane().add(btnConfirmar);
	}
}
