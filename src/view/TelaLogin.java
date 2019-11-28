package view;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;

import model.DAO.UsuarioDAO;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class TelaLogin {

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
					TelaLogin window = new TelaLogin();
					window.frame.setTitle("Login");
					try {
						window.frame.setIconImage(Toolkit.getDefaultToolkit().getImage("imagens/farmacia-icone.png"));
			        } catch (NullPointerException ex) {
			        	System.out.println("nao encontrou o icone");
			        }
					window.frame.setResizable(false);
					window.frame.setLocationRelativeTo(null);
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
	public TelaLogin() {
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
		txtLogin.setBounds(140, 104, 223, 23);
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
					TelaPrincipal telaPrinc = new TelaPrincipal();
					telaPrinc.setTitle("Login");
					telaPrinc.setResizable(false);
					telaPrinc.setLocationRelativeTo(null);
					telaPrinc.setVisible(true);
					frame.dispose();
				}else{
					JOptionPane.showMessageDialog(null, "Dados inválidos");
				}
			}
		});
		btnEntrar.setBounds(161, 212, 89, 23);
		frame.getContentPane().add(btnEntrar);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(128, 143, 223, 23);
		frame.getContentPane().add(txtPassword);
		
		ImageIcon imagem = new ImageIcon(Toolkit.getDefaultToolkit().getImage("imagens/palavraFarmacia.png"));
		JLabel lblImagemFarmacia = new JLabel(imagem);
		
		lblImagemFarmacia.setBounds(128, 22, 212, 52);
		frame.getContentPane().add(lblImagemFarmacia);
	}
}
