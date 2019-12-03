package view;

import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Auxiliares.JNumberFormatField;
import Auxiliares.ValidaData;
import Exception.AberturaCaixaException;
import Exception.DataDdMmYyyyInvalida;
import Exception.DataDigitadaInvalidaException;
import model.DAO.CaixaDAO;
import model.DAO.CaixaDisponivelDAO;
import model.bean.Caixa;
import model.bean.CaixaDisponivel;
import model.bean.Usuario;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;

public class TelaAberturaDeCaixa extends JInternalFrame {

	private static final long serialVersionUID = -4909143403816955815L;

	public TelaAberturaDeCaixa(Usuario usuario) {
		setBounds(0, 0, 794, 550);
		setClosable(true);
		setTitle("Farmácia Express - Abertura de Caixa");
		getContentPane().setLayout(null);
		
		JLabel lblFuncionario = new JLabel("Funcionário: ");
		lblFuncionario.setBounds(10, 23, 150, 30);
		getContentPane().add(lblFuncionario);
		
		JLabel txtFuncionario = new JLabel();
		txtFuncionario.setBounds(160, 23, 400, 30);
		txtFuncionario.setText(usuario.getNomeUsuario());
		getContentPane().add(txtFuncionario);
		
		
		JLabel lblDataAbertura = new JLabel("Data de abertura");
		lblDataAbertura.setBounds(10, 61, 150, 30);
		getContentPane().add(lblDataAbertura);

		JFormattedTextField txtDataAbertura = new JFormattedTextField();
		txtDataAbertura.setBounds(160, 61, 150, 30);
		try {
			txtDataAbertura.setFormatterFactory(
					new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
		} catch (java.text.ParseException ex) {
			System.out.println("Erro na Mascara da data de Abertura: " + ex);
		}
		getContentPane().add(txtDataAbertura);
		
		JLabel lblTotalCaixa = new JLabel("Total do Caixa");
		lblTotalCaixa.setBounds(10, 105, 150, 30);
		getContentPane().add(lblTotalCaixa);

		JNumberFormatField txtTotalCaixa = new JNumberFormatField();
		txtTotalCaixa.setLimit(6);
		txtTotalCaixa.setBounds(160, 105, 150, 30);
		getContentPane().add(txtTotalCaixa);
		
		JLabel lblNumeroCaixa = new JLabel("Numero do Caixa");
		lblNumeroCaixa.setBounds(10, 146, 150, 30);
		getContentPane().add(lblNumeroCaixa);
		
		JComboBox<Integer> comboBox = new JComboBox<Integer>();
		comboBox.setBounds(160, 151, 150, 20);
		getContentPane().add(comboBox);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String totalCaixa = txtTotalCaixa.getText().replace("R$", "").replaceAll("[.]", "").replaceAll(",", ".").replaceAll(" ", "");
				String dataDeABertura = txtDataAbertura.getText();
				BigDecimal caixa = new BigDecimal(totalCaixa);
				int numeroCaixa ;
				try {
					numeroCaixa = Integer.parseInt(comboBox.getSelectedItem().toString());
					ValidaData.validaDataComExcecao(dataDeABertura.split("/")[0],dataDeABertura.split("/")[1],dataDeABertura.split("/")[2]);
					Caixa c = new Caixa();
					CaixaDisponivel caixaDisponivel = new CaixaDisponivel();
					caixaDisponivel.setUtilizando(true);
					caixaDisponivel.setIdCaixaDisponivel(numeroCaixa);
					c.setCaixaDisponivel(caixaDisponivel);
					c.setDataCaixa(dataDeABertura);
					c.setValorInicial(caixa);
					c.setStatus(true);
					CaixaDAO caixaDao = new CaixaDAO();
					caixaDao.create(c);
					dispose();
					JOptionPane.showMessageDialog(null, "Caixa "+ c.getCaixaDisponivel().getIdCaixaDisponivel()+ " aberto com sucesso!");
				} catch (DataDdMmYyyyInvalida | DataDigitadaInvalidaException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro na abertura do Caixa",
							JOptionPane.ERROR_MESSAGE);
				} catch (NullPointerException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Numero do Caixa Nao Selecionado",
							"Erro na abertura do Caixa", JOptionPane.ERROR_MESSAGE);
				} catch (AberturaCaixaException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro na Abertura do Caixa",
							JOptionPane.ERROR_MESSAGE);		
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Erro na abertura do Caixa",
							"Erro na abertura do Caixa", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnConfirmar.setBounds(10, 207, 89, 23);
		getContentPane().add(btnConfirmar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(126, 207, 89, 23);
		getContentPane().add(btnCancelar);
		
		CaixaDisponivelDAO caixaDisponivelDAO = new CaixaDisponivelDAO();
		
		for (CaixaDisponivel c : caixaDisponivelDAO.read()) {
			comboBox.addItem(c.getIdCaixaDisponivel());
		}
		
	}
}
