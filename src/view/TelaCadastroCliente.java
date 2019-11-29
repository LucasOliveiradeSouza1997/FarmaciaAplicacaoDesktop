package view;

import javax.swing.JInternalFrame;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.DAO.ClienteDAO;
import model.bean.Cliente;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaCadastroCliente extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2339528519848930755L;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Create the frame.
	 */
	public TelaCadastroCliente() {
		setBounds(0, 0, 794, 550);
		setClosable(true);
		setTitle("Farmácia Express - Cadastro de Cliente");
		getContentPane().setLayout(null);

		JFormattedTextField txtCpf = new JFormattedTextField();
		txtCpf.setBounds(95, 64, 200, 30);
		try {
			txtCpf.setFormatterFactory(
					new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
		} catch (java.text.ParseException ex) {
			System.out.println("Erro na Mascara do CPF: " + ex);
		}
		getContentPane().add(txtCpf);

		JLabel lblCpf = new JLabel("CPF");
		lblCpf.setBounds(10, 64, 75, 30);
		getContentPane().add(lblCpf);

		JLabel lblRg = new JLabel("RG");
		lblRg.setBounds(10, 106, 75, 30);

		getContentPane().add(lblRg);

		JFormattedTextField txtRg = new JFormattedTextField();
		txtRg.setBounds(95, 106, 200, 30);

		try {
			txtRg.setFormatterFactory(
					new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###-#")));
		} catch (java.text.ParseException ex) {
			System.out.println("Erro na Mascara do RG: " + ex);
		}
		getContentPane().add(txtRg);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 23, 75, 30);
		getContentPane().add(lblNome);

		JTextField txtNome = new JTextField();
		txtNome.setBounds(95, 23, 400, 30);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);

		JLabel lblEndereo = new JLabel("Endere\u00E7o");
		lblEndereo.setBounds(10, 147, 75, 30);
		getContentPane().add(lblEndereo);

		JTextField txtEndereco = new JTextField();
		txtEndereco.setBounds(95, 152, 400, 30);
		getContentPane().add(txtEndereco);
		txtEndereco.setColumns(10);

		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setBounds(10, 188, 75, 30);
		getContentPane().add(lblTelefone);

		JFormattedTextField txtTelefone = new JFormattedTextField();
		txtTelefone.setBounds(95, 193, 200, 30);
		try {
			txtTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
					new javax.swing.text.MaskFormatter("(##) #####-####")));
		} catch (java.text.ParseException ex) {
			System.out.println("Erro na Mascara do Telefone: " + ex);
		}

		getContentPane().add(txtTelefone);

		JLabel lblAposentado = new JLabel("Aposentado");
		lblAposentado.setBounds(10, 229, 75, 30);
		getContentPane().add(lblAposentado);

		JRadioButton rdbtnAposentadoSim = new JRadioButton("Sim");
		buttonGroup.add(rdbtnAposentadoSim);
		rdbtnAposentadoSim.setBounds(95, 230, 52, 30);
		getContentPane().add(rdbtnAposentadoSim);

		JRadioButton rdbtnAposentadoNao = new JRadioButton("N\u00E3o");
		buttonGroup.add(rdbtnAposentadoNao);
		rdbtnAposentadoNao.setBounds(165, 230, 52, 30);
		getContentPane().add(rdbtnAposentadoNao);

		JButton btnCadastrarCliente = new JButton("Salvar");
		btnCadastrarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String cpf = txtCpf.getText();
				String nome = txtNome.getText();
				String rg = txtRg.getText();
				String endereco = txtEndereco.getText();
				String telefone = txtTelefone.getText();
				String tipoCliente = "";
				if (rdbtnAposentadoSim.isSelected()) {
					tipoCliente = "E";
				} else if (rdbtnAposentadoNao.isSelected()) {
					tipoCliente = "N";
				}
				if (cpf.equals("") || nome.equals("") || rg.equals("") || endereco.equals("") || telefone.equals("")
						|| tipoCliente.equals("")) {
					JOptionPane.showMessageDialog(null, "Campos não preenchidos", "Erro no Cadastro",
							JOptionPane.ERROR_MESSAGE);
				}else if (telefone.length() < 10 ) {
					JOptionPane.showMessageDialog(null, "Digite um telefone com 8 digitos ou mais", "Erro no Cadastro",
							JOptionPane.ERROR_MESSAGE);
				}else if(rg.length() != 9) {
					JOptionPane.showMessageDialog(null, "Digite um RG valido", "Erro no Cadastro",
							JOptionPane.ERROR_MESSAGE);
				}else if(cpf.length() != 11) {
					JOptionPane.showMessageDialog(null, "Digite um CPF valido", "Erro no Cadastro",
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					try {
						Cliente cliente = new Cliente();
						ClienteDAO clienteDao = new ClienteDAO();
						cliente.setCpfCliente(cpf.replaceAll("[.]", "").replaceAll("-", ""));
						cliente.setNomeCLiente(nome);
						cliente.setRgCLiente(rg.replaceAll("[.]", "").replaceAll("-", ""));
						cliente.setEnderecoCliente(endereco);
						cliente.setTelefoneCLiente(telefone.replaceAll("[(]", "").replaceAll("[)]", "").replaceAll("-", "").replaceAll(" ", ""));
						cliente.setTipoCLiente(tipoCliente);
						cliente.setQtdDEsconto(20);
						cliente.setDescontoDinheiro(5);
						clienteDao.create(cliente);
						dispose();
						JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Erro ao cadastrar Cliente: ", "Erro no Cadastro do CLiente",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
			}
		});
		btnCadastrarCliente.setBounds(10, 287, 90, 30);
		getContentPane().add(btnCadastrarCliente);
	}
}