package view;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;

import Exception.UsuarioNaoEncontradoException;
import model.DAO.UsuarioDAO;
import model.bean.Usuario;

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
		frame.setTitle("Farmácia Express - Login");
		try {
			frame.setIconImage(Toolkit.getDefaultToolkit().getImage("imagens/farmacia-icone.png"));
        } catch (NullPointerException ex) {
        	System.out.println("nao encontrou o icone");
        }
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		JLabel lblLogin = new JLabel("login");
		lblLogin.setBounds(253, 26, 46, 14);
		frame.getContentPane().add(lblLogin);
		
		txtLogin = new JTextField();
		txtLogin.setBounds(253, 51, 171, 26);
		frame.getContentPane().add(txtLogin);
		txtLogin.setColumns(10);
		
		JLabel labelSenha = new JLabel("senha");
		labelSenha.setBounds(253, 86, 46, 14);
		frame.getContentPane().add(labelSenha);
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				Usuario usuario = new Usuario();
				try {
					usuario =  usuarioDAO.confereLogin(txtLogin.getText(), new String(txtPassword.getPassword()));
					new TelaPrincipal(usuario);
					frame.dispose();
				} catch (UsuarioNaoEncontradoException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Falha no Login", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnEntrar.setBounds(288, 178, 96, 30);
		frame.getContentPane().add(btnEntrar);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(253, 108, 171, 26);
		frame.getContentPane().add(txtPassword);
		
		ImageIcon imagem = new ImageIcon(Toolkit.getDefaultToolkit().getImage("imagens/farmaciaImagem.png"));
		JLabel lblImagemFarmacia = new JLabel(imagem);
		
		lblImagemFarmacia.setBounds(10, 11, 225, 225);
		frame.getContentPane().add(lblImagemFarmacia);
	}
}
