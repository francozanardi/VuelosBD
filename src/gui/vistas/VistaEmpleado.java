package gui.vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import gui.elements.DateText;
import gui.elements.TextField;

import javax.swing.JCheckBox;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JFormattedTextField;

public class VistaEmpleado extends Vista {
	
	private JTextField txtOrigen;
	private JTextField txtDestino;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					VistaEmpleado window = new VistaEmpleado();
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
	public VistaEmpleado() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 323, 275);
		frame.setLayout(null);

		
		txtOrigen = new TextField("Ciudad origen", new Font("Tahoma", Font.PLAIN, 16));
		txtOrigen.setBounds(12, 23, 266, 22);
		
		txtDestino = new TextField("Ciudad destino", new Font("Tahoma", Font.PLAIN, 16));
		txtDestino.setBounds(12, 58, 266, 22);
		
		frame.add(txtOrigen);
		frame.add(txtDestino);
		
		JFormattedTextField fechaIda, fechaVuelta;
		fechaIda = new DateText("Fecha de ida", new Font("Tahoma", Font.PLAIN, 16));
		fechaIda.setBounds(12, 97, 120, 22);
		
		fechaVuelta = new DateText("Fecha de vuelta", new Font("Tahoma", Font.PLAIN, 16));
		fechaVuelta.setBounds(151, 97, 127, 22);
		fechaVuelta.setEnabled(false);
		
		frame.add(fechaIda);
		frame.add(fechaVuelta);
		
		
		JCheckBox chckbxIdaYVuelta = new JCheckBox("Ida y vuelta");
		chckbxIdaYVuelta.setFont(new Font("Tahoma", Font.PLAIN, 16));
		chckbxIdaYVuelta.setBounds(12, 128, 113, 25);
		chckbxIdaYVuelta.addActionListener(e -> fechaVuelta.setEnabled(chckbxIdaYVuelta.isSelected()));
		
		frame.add(chckbxIdaYVuelta);

	}
}
