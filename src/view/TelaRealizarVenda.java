package view;

import javax.swing.JInternalFrame;

import model.bean.Usuario;

public class TelaRealizarVenda extends JInternalFrame {

	private static final long serialVersionUID = 5060553779977942399L;

	public TelaRealizarVenda(Usuario usuario) {
		setBounds(0, 0, 794, 550);
		setClosable(true);
		setTitle("Farmácia Express - Realizar Venda");
		getContentPane().setLayout(null);
	}
}
