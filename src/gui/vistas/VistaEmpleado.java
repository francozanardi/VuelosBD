package gui.vistas;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.MaskFormatter;

import conexionBD.ConexionVuelos;
import gui.GestorDeVistas;
import gui.elements.DateText;
import gui.elements.TextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.ParseException;

public class VistaEmpleado extends Vista {

	private ConexionVuelos conn;
	private JTextField txtOrigen;
	private JTextField txtDestino;
	private JPanel panelFechas, panelCiudades, panelFormulario, panelBotones;
	private JCheckBox chckbxIdaYVuelta;
	private JButton buscar;

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
	public VistaEmpleado(ConexionVuelos conn) {
		this.conn = conn;
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame.setBounds(100, 100, 320, 240);
		frame.setLayout(new BorderLayout());
//		frame.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		frame.setResizable(false);

		panelCiudades = new JPanel();
		panelCiudades.setLayout(new BorderLayout(0, 0));

		txtOrigen = new TextField("Ciudad origen", new Font("Tahoma", Font.PLAIN, 16));
		txtDestino = new TextField("Ciudad destino", new Font("Tahoma", Font.PLAIN, 16));

		panelCiudades.setPreferredSize(new Dimension(frame.getWidth()-30, txtDestino.getPreferredSize().height+txtOrigen.getPreferredSize().height+20));
		panelCiudades.setBorder(BorderFactory.createEmptyBorder(5, 0,5, 5));

		panelCiudades.add(txtOrigen, BorderLayout.NORTH);
		panelCiudades.add(txtDestino, BorderLayout.SOUTH);

		panelFechas = new JPanel();
		panelFechas.setLayout(new BorderLayout(0, 0));
		
		JFormattedTextField fechaIda, fechaVuelta;
		fechaIda = new DateText("Fecha de ida", new Font("Tahoma", Font.PLAIN, 16));
		fechaIda.setPreferredSize(new Dimension(panelCiudades.getPreferredSize().width/2 -10, fechaIda.getPreferredSize().height));
		
		fechaVuelta = new DateText("Fecha de vuelta", new Font("Tahoma", Font.PLAIN, 16));
		fechaVuelta.setPreferredSize(fechaIda.getPreferredSize());
		fechaVuelta.setEnabled(false);

		panelFechas.setPreferredSize(new Dimension(panelCiudades.getPreferredSize().width, fechaVuelta.getPreferredSize().height+12));
		panelFechas.setBorder(BorderFactory.createEmptyBorder(5, 0,5, 5));
		
		panelFechas.add(fechaIda, BorderLayout.WEST);
		panelFechas.add(fechaVuelta, BorderLayout.EAST);
		
		
		chckbxIdaYVuelta = new JCheckBox("Ida y vuelta");
		chckbxIdaYVuelta.setFont(new Font("Tahoma", Font.PLAIN, 16));
		chckbxIdaYVuelta.addActionListener(e -> fechaVuelta.setEnabled(chckbxIdaYVuelta.isSelected()));

		panelFormulario = new JPanel();
		panelFormulario.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		panelFormulario.add(panelCiudades);
		panelFormulario.add(panelFechas);
		panelFormulario.add(chckbxIdaYVuelta);

		buscar = new JButton();
		buscar.setText("Buscar");
		buscar.addActionListener((e) -> {
			if(chckbxIdaYVuelta.isSelected()){
				GestorDeVistas.agregarVista(new VistaEmpleadoTablas(conn, txtOrigen.getText(), txtDestino.getText(), fechaIda.getText(), fechaVuelta.getText()));
			} else {
				GestorDeVistas.agregarVista(new VistaEmpleadoTablas(conn, txtOrigen.getText(), txtDestino.getText(), fechaIda.getText()));
			}
		});

		panelBotones = new JPanel();
		panelBotones.setLayout(new BorderLayout());
		panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));
		panelBotones.add(buscar, BorderLayout.CENTER);

		
		frame.add(panelFormulario, BorderLayout.CENTER);
		frame.add(panelBotones, BorderLayout.SOUTH);

	}
}
