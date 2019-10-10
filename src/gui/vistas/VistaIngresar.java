package gui.vistas;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.JTextField;

import gui.IngresarAdministrador;
import gui.IngresarEmpleado;
import gui.IngresarUsuario;

public class VistaIngresar extends Vista {

	private Panel panelUsuario;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					VistaIngresar window = new VistaIngresar();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public VistaIngresar() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
		frame.setBounds(100, 100, 280, 115);
		frame.setLayout(null);
			
		
		JComboBox<IngresarUsuario> cBox_tipoUsuario = new JComboBox<>();
		cBox_tipoUsuario.setBounds(12, 20, 240, 22);
		
		panelUsuario = new Panel();
		panelUsuario.setBounds(0, 49, 262, 15);
		frame.add(panelUsuario);
		panelUsuario.setLayout(null);
		
		
		cBox_tipoUsuario.addItem(new IngresarAdministrador(panelUsuario));
		cBox_tipoUsuario.addItem(new IngresarEmpleado(panelUsuario));
		cBox_tipoUsuario.setSelectedIndex(0);
		cBox_tipoUsuario.getModel().setSelectedItem("Selecciona el tipo de usuario...");;
		
		cBox_tipoUsuario.addActionListener(e -> {
			int heigthOld = panelUsuario.getSize().height;
			panelUsuario.removeAll();
			panelUsuario.repaint();
			cBox_tipoUsuario.getItemAt(cBox_tipoUsuario.getSelectedIndex()).crearGUI();
			frame.setSize(Math.max(frame.getSize().width,panelUsuario.getSize().width), frame.getSize().height-heigthOld+panelUsuario.getSize().height);
		});

		frame.add(cBox_tipoUsuario);
	}
}
