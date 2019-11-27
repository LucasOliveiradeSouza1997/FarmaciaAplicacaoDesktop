package view;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

public class viewTelaCadastroClienteAposentado extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viewTelaCadastroClienteAposentado frame = new viewTelaCadastroClienteAposentado();
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
	public viewTelaCadastroClienteAposentado() {
		setBounds(100, 100, 450, 300);

	}

}
