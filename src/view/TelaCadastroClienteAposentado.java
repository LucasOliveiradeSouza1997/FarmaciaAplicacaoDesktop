package view;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

public class TelaCadastroClienteAposentado extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastroClienteAposentado frame = new TelaCadastroClienteAposentado();
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
	public TelaCadastroClienteAposentado() {
		setBounds(100, 100, 450, 300);

	}

}
