package view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.bean.Usuario;

public class TelaPrincipal extends JFrame {
	
	private static final long serialVersionUID = 5056169976489775135L;
	private Usuario usuario;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public TelaPrincipal(Usuario usuario) {
		this.usuario = usuario;
		setResizable(false);
		setVisible(true);
		setTitle("Farmácia Express - "+ this.usuario.getNomeUsuario());
		try {
			setIconImage(Toolkit.getDefaultToolkit().getImage("imagens/farmacia-icone.png"));
        } catch (NullPointerException ex) {
        	System.out.println("nao encontrou o icone");
        }
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setLocationRelativeTo(null);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnCliente = new JMenu("Cliente");
		menuBar.add(mnCliente);
		
		JMenuItem mntmCadastrarCliente = new JMenuItem("Cadastrar cliente");
		mntmCadastrarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaCadastroCliente cadastroCliente = new TelaCadastroCliente();
				desktopPane.add(cadastroCliente);
				cadastroCliente.setVisible(true);
			}
		});
		mnCliente.add(mntmCadastrarCliente);
		
		JMenuItem mntmListarClientes = new JMenuItem("Listar clientes");
		mntmListarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaListarClientes listarCLientes = new TelaListarClientes();
				desktopPane.add(listarCLientes);
				listarCLientes.setVisible(true);
			}
		});
		mnCliente.add(mntmListarClientes);
		
		JMenu mnMedicamento = new JMenu("Medicamento");
		menuBar.add(mnMedicamento);
		
		JMenuItem mntmCadastrarMedicamento = new JMenuItem("Cadastrar medicamento");
		mntmCadastrarMedicamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaCadastroMedicamento cadastroMedicamento = new TelaCadastroMedicamento();
				desktopPane.add(cadastroMedicamento);
				cadastroMedicamento.setVisible(true);
			}
		});
		mnMedicamento.add(mntmCadastrarMedicamento);
		
		JMenuItem mntmListarMedicamentos = new JMenuItem("Listar Medicamentos");
		mntmListarMedicamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaListarMedicamentos listarMedicamentos = new TelaListarMedicamentos();
				desktopPane.add(listarMedicamentos);
				listarMedicamentos.setVisible(true);
			}
		});
		mnMedicamento.add(mntmListarMedicamentos);
		
		JMenuItem mntmMedicamentosVencidos = new JMenuItem("Relat\u00F3rio de Medicamentos Vencidos");
		mntmMedicamentosVencidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaMedicamentosVencidos listarMedicamentosVencidos = new TelaMedicamentosVencidos();
				desktopPane.add(listarMedicamentosVencidos);
				listarMedicamentosVencidos.setVisible(true);
			}
		});
		mnMedicamento.add(mntmMedicamentosVencidos);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		desktopPane = new JDesktopPane();
		desktopPane.setBounds(0, 0, 794, 550);
		contentPane.add(desktopPane);
	}
	JDesktopPane desktopPane;
}
