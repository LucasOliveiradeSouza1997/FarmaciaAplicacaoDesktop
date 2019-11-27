package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;

import model.DAO.UsuarioDAO;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class viewTelaLogin {

	private JFrame frame;
	private JTextField txtLogin;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.println(ex);
        }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viewTelaLogin window = new viewTelaLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public viewTelaLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblLogin = new JLabel("login");
		lblLogin.setBounds(24, 38, 46, 14);
		frame.getContentPane().add(lblLogin);
		
		txtLogin = new JTextField();
		txtLogin.setBounds(93, 32, 223, 23);
		frame.getContentPane().add(txtLogin);
		txtLogin.setColumns(10);
		
		JLabel labelSenha = new JLabel("senha");
		labelSenha.setBounds(24, 78, 46, 14);
		frame.getContentPane().add(labelSenha);
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				if (usuarioDAO.confereLogin(txtLogin.getText(), new String(txtPassword.getPassword()))) {
//					JOptionPane.showMessageDialog(null, "Logou");
					telaPrincipal telaPrinc = new telaPrincipal();
					telaPrinc.setVisible(true);
					frame.dispose();
				}else{
					JOptionPane.showMessageDialog(null, "Dados inválidos");
				}
			}
		});
		btnEntrar.setBounds(138, 140, 89, 23);
		frame.getContentPane().add(btnEntrar);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(93, 75, 223, 23);
		frame.getContentPane().add(txtPassword);
	}
}
