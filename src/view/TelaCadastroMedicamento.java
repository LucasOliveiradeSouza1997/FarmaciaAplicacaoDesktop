package view;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Auxiliares.NumeroDoLoteMedicamento;
import Exception.DataDdMmYyyyInvalida;
import model.DAO.ClienteDAO;
import model.DAO.EstoqueDAO;
import model.DAO.MedicamentoDAO;
import model.bean.Cliente;
import model.bean.Estoque;
import model.bean.Medicamento;

import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;
import java.awt.event.ActionEvent;

public class TelaCadastroMedicamento extends JInternalFrame {

	private static final long serialVersionUID = -8902186477882282382L;
	private JTextField txtNomeMedicamento;
	private JTextField txtDescricaoMedicamento;
	private JTextField txtDistribuidor;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastroMedicamento frame = new TelaCadastroMedicamento();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaCadastroMedicamento() {
		setBounds(0, 0, 794, 550);
		setClosable(true);
		setTitle("Farmácia Express - Cadastro de Medicamento");
		getContentPane().setLayout(null);

		JLabel lblNomeMedicamento = new JLabel("Nome do Medicamento");
		lblNomeMedicamento.setBounds(10, 23, 150, 30);
		getContentPane().add(lblNomeMedicamento);

		txtNomeMedicamento = new JTextField();
		txtNomeMedicamento.setBounds(160, 23, 400, 30);
		getContentPane().add(txtNomeMedicamento);
		txtNomeMedicamento.setColumns(10);

		JLabel lblDescricaoMedicamento = new JLabel("Descri\u00E7\u00E3o do Medicamento");
		lblDescricaoMedicamento.setBounds(10, 64, 150, 30);
		getContentPane().add(lblDescricaoMedicamento);

		txtDescricaoMedicamento = new JTextField();
		txtDescricaoMedicamento.setBounds(160, 64, 400, 30);
		getContentPane().add(txtDescricaoMedicamento);
		txtDescricaoMedicamento.setColumns(10);

		JLabel lblPreco = new JLabel("Pre\u00E7o do Medicamento");
		lblPreco.setBounds(10, 105, 150, 30);
		getContentPane().add(lblPreco);

		JFormattedTextField txtPreco = new JFormattedTextField();
		txtPreco.setBounds(160, 105, 150, 30);
		try {
			txtPreco.setFormatterFactory(
					new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("R$ ###,##")));
		} catch (java.text.ParseException ex) {
			System.out.println("Erro na Mascara do Valor do Medicamento: " + ex);
		}
		getContentPane().add(txtPreco);

		JLabel lblValidade = new JLabel("Validade do Medicamento");
		lblValidade.setBounds(10, 146, 150, 30);
		getContentPane().add(lblValidade);

		JFormattedTextField txtValidade = new JFormattedTextField();
		txtValidade.setBounds(160, 146, 150, 30);

		try {
			txtValidade.setFormatterFactory(
					new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
		} catch (java.text.ParseException ex) {
			System.out.println("Erro na Mascara da data de Validade: " + ex);
		}
		getContentPane().add(txtValidade);

		JFormattedTextField txtQuantidade = new JFormattedTextField();
		txtQuantidade.setBounds(160, 187, 100, 30);
		try {
			txtQuantidade.setFormatterFactory(
					new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###")));
		} catch (java.text.ParseException ex) {
			System.out.println("Erro na Mascara da data de Validade: " + ex);
		}
		getContentPane().add(txtQuantidade);

		JLabel lblQuantidade = new JLabel("Quantidade ");
		lblQuantidade.setBounds(10, 187, 150, 30);
		getContentPane().add(lblQuantidade);

		txtDistribuidor = new JTextField();
		txtDistribuidor.setBounds(160, 228, 400, 30);
		getContentPane().add(txtDistribuidor);
		txtDistribuidor.setColumns(10);

		JLabel lblDistribuidor = new JLabel("Distribuidor");
		lblDistribuidor.setBounds(10, 228, 150, 30);
		getContentPane().add(lblDistribuidor);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nomeMedicamento = txtNomeMedicamento.getText();
				String descricaoMedicamento = txtDescricaoMedicamento.getText();
				String distribuidor = txtDistribuidor.getText();

				String precoMedicamento = txtPreco.getText().replace("R$", "".replace(",", "."));
				String validadeMedicamento = txtValidade.getText();
				int quantidade = Integer.parseInt(txtQuantidade.getText());

				BigDecimal preco = new BigDecimal(precoMedicamento);

				if (nomeMedicamento.equals("") || descricaoMedicamento.equals("") || distribuidor.equals("")
						|| precoMedicamento.equals("") || validadeMedicamento.equals("")) {
					JOptionPane.showMessageDialog(null, "Campos não preenchidos", "Erro no Cadastro do Medicamento",
							JOptionPane.ERROR_MESSAGE);
				} else if (preco.compareTo(new BigDecimal("0")) != 1) {
					JOptionPane.showMessageDialog(null, "Valor Nulo para o Preço", "Erro no Cadastro do Medicamento",
							JOptionPane.ERROR_MESSAGE);
				} else if (quantidade <= 0) {
					JOptionPane.showMessageDialog(null, "Quantidade Invalida para o Medicamento",
							"Erro no Cadastro do Medicamento", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						int lote = NumeroDoLoteMedicamento.geraNumero();
						Estoque estoque = new Estoque();
						estoque.setLote(lote);
						estoque.setDistribuidor(distribuidor);
						estoque.setQuantidade(quantidade);
						Medicamento medicamento = new Medicamento();
						medicamento.setLote(lote);
						medicamento.setNomeMedicamento(nomeMedicamento);
						medicamento.setDescricaoMedicamento(descricaoMedicamento);
						medicamento.setPrecoMedicamento(preco);
						medicamento.setValidadeMedicamento(validadeMedicamento);
						medicamento.setStatusMedicamento(true);
						EstoqueDAO estoqueDao = new EstoqueDAO();
						estoqueDao.create(estoque);
						MedicamentoDAO medicamentoDao = new MedicamentoDAO();
						medicamentoDao.create(medicamento);
						dispose();
						JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
					} catch (DataDdMmYyyyInvalida e) {
						JOptionPane.showMessageDialog(null, e.getMessage(), "Erro no Cadastro do Medicamento",
								JOptionPane.ERROR_MESSAGE);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Erro ao cadastrar Cliente: ",
								"Erro no Cadastro do Medicamento", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnCadastrar.setBounds(10, 290, 90, 30);
		getContentPane().add(btnCadastrar);
	}
}
