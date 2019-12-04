package view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.bean.Usuario;
import javax.swing.SwingConstants;

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
		
		JMenu mnEstoque = new JMenu("Estoque");
		menuBar.add(mnEstoque);
		
		JMenuItem mntmGerenciarEstoque = new JMenuItem("Gerenciar Estoque");
		mntmGerenciarEstoque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaGerenciarEstoque gerenciarEstoque = new TelaGerenciarEstoque();
				desktopPane.add(gerenciarEstoque);
				gerenciarEstoque.setVisible(true);
			}
		});
		mnEstoque.add(mntmGerenciarEstoque);
		
		JMenu mnCaixa = new JMenu("Caixa");
		menuBar.add(mnCaixa);
		
		JMenuItem mntmAberturaDeCaixa = new JMenuItem("Abertura de Caixa");
		mntmAberturaDeCaixa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaAberturaDeCaixa aberturaDeCaixa = new TelaAberturaDeCaixa(usuario);
				desktopPane.add(aberturaDeCaixa);
				aberturaDeCaixa.setVisible(true);
			}
		});
		mnCaixa.add(mntmAberturaDeCaixa);
		
		JMenuItem mntmCaixasAbertos = new JMenuItem("Caixas Abertos");
		mntmCaixasAbertos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCaixasAbertos telaCaixasAbertos = new TelaCaixasAbertos();
				desktopPane.add(telaCaixasAbertos);
				telaCaixasAbertos.setVisible(true);
			}
		});
		mnCaixa.add(mntmCaixasAbertos);
		
		JMenu mnVenda = new JMenu("Venda");
		menuBar.add(mnVenda);
		
		JMenuItem mntmRealizarVenda = new JMenuItem("Realizar Venda");
		mntmRealizarVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaRealizarVenda telaRealizarVenda = new TelaRealizarVenda(usuario);
				desktopPane.add(telaRealizarVenda);
				telaRealizarVenda.setVisible(true);
			}
		});
		mnVenda.add(mntmRealizarVenda);
		
		JMenuItem mntmConsultarVendas = new JMenuItem("Consultar Vendas");
		mntmConsultarVendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaConsultarVendas telaConsultarVendas = new TelaConsultarVendas();
				desktopPane.add(telaConsultarVendas);
				telaConsultarVendas.setVisible(true);
			}
		});
		mnVenda.add(mntmConsultarVendas);
		
		JMenu mnOpces = new JMenu("Op\u00E7\u00F5es");
		menuBar.add(mnOpces);
		
		JMenuItem mntmSairDoSistema = new JMenuItem("Trocar de Usu\u00E1rio");
		mntmSairDoSistema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaLogin window = new TelaLogin();
				dispose();
			}
		});
		mnOpces.add(mntmSairDoSistema);
		
		JMenuItem mntmFechamentoDeCaixa = new JMenuItem("Fechamento de Caixa");
		mntmFechamentoDeCaixa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TelaFechamentoDeCaixa telaFechamentoDeCaixa = new TelaFechamentoDeCaixa();
					desktopPane.add(telaFechamentoDeCaixa);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage(),
							"Erro no fechamento do Caixa", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		if (usuario.getTipoUsuario().equals("G")) { // apenas para o gerente
			mnCaixa.add(mntmFechamentoDeCaixa);
		}	
		
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
