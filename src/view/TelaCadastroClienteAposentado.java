package view;

import javax.swing.JInternalFrame;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TelaCadastroClienteAposentado extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2339528519848930755L;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Create the frame.
	 */
	public TelaCadastroClienteAposentado() {
		setBounds(0, 0, 794, 550);
		setClosable(true);
		setTitle("Farmácia Express - Cadastro de Cliente");
		getContentPane().setLayout(null);

		JFormattedTextField frmtdtxtfldCpf = new JFormattedTextField();
		frmtdtxtfldCpf.setBounds(95, 64, 200, 30);
		try {
			frmtdtxtfldCpf.setFormatterFactory(
					new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
		} catch (java.text.ParseException ex) {
			System.out.println("Erro na Mascara do CPF: " + ex);
		}
		getContentPane().add(frmtdtxtfldCpf);

		JLabel lblCpf = new JLabel("CPF");
		lblCpf.setBounds(10, 64, 75, 30);
		getContentPane().add(lblCpf);

		JLabel lblRg = new JLabel("RG");
		lblRg.setBounds(10, 106, 75, 30);

		getContentPane().add(lblRg);

		JFormattedTextField formattedTextFieldRg = new JFormattedTextField();
		formattedTextFieldRg.setBounds(95, 106, 200, 30);
		
		try {
			formattedTextFieldRg.setFormatterFactory(
					new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###-#")));
		} catch (java.text.ParseException ex) {
			System.out.println("Erro na Mascara do RG: " + ex);
		}
		getContentPane().add(formattedTextFieldRg);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 23, 75, 30);
		getContentPane().add(lblNome);
		
		textField = new JTextField();
		textField.setBounds(95, 23, 400, 30);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblEndereo = new JLabel("Endere\u00E7o");
		lblEndereo.setBounds(10, 147, 75, 30);
		getContentPane().add(lblEndereo);
		
		textField_1 = new JTextField();
		textField_1.setBounds(95, 152, 400, 30);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setBounds(10, 188, 75, 30);
		getContentPane().add(lblTelefone);
		
		JFormattedTextField formattedTextFieldTelefone = new JFormattedTextField();
		formattedTextFieldTelefone.setBounds(95, 193, 200, 30);
		try {
			formattedTextFieldTelefone.setFormatterFactory(
					new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-####")));
		} catch (java.text.ParseException ex) {
			System.out.println("Erro na Mascara do Telefone: " + ex);
		}
		
		getContentPane().add(formattedTextFieldTelefone);
	}
}