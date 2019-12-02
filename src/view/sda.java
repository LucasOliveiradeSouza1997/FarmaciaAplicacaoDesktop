package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

public class sda extends JDialog {
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			sda dialog = new sda();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public sda() {
		getContentPane().setLayout(new MigLayout("", "[][][grow]", "[]"));
		
		JLabel lblNewLabel = new JLabel("New label");
		getContentPane().add(lblNewLabel, "cell 0 0");
		
		textField_1 = new JTextField();
		getContentPane().add(textField_1, "cell 2 0,growx");
		textField_1.setColumns(10);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new GridLayout(, 4, 0, 0));
		{
			JLabel lblNewLabel_2 = new JLabel("New label");
			getContentPane().add(lblNewLabel_2);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("New label");
			getContentPane().add(lblNewLabel_1);
		}
		{
			JLabel lblNewLabel = new JLabel("New label");
			getContentPane().add(lblNewLabel);
		}
		{
			textField = new JTextField();
			getContentPane().add(textField);
			textField.setColumns(2);
		}
	}
}
