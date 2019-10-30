package gui.vistas;

import java.awt.*;

import javax.swing.*;
import conexionBD.ConexionVuelos;
import fechas.Fechas;
import gui.GestorDeVistas;
import gui.elements.DateText;
import gui.elements.TextField;
import gui.vistas.excepciones.PasajeroNotFoundException;

import java.sql.SQLException;
import java.util.Date;

public class VistaEmpleadoBuscar extends Vista {

	private ConexionVuelos conn;
	private JTextField txtOrigen;
	private JTextField txtDestino;
	private JTextField txtTipoDoc_pasajero;
	private JTextField txtNroDoc_pasajero;
	private JPanel panelFechas, panelData, panelFormulario, panelBotones;
	private JCheckBox chckbxIdaYVuelta;
	private JButton buscar;
	private String nroLeg_empleado;


	public VistaEmpleadoBuscar(ConexionVuelos conn, String nroLeg_empleado) {
		this.conn = conn;
		this.nroLeg_empleado = nroLeg_empleado;
		initialize();
		frame.setVisible(true);
	}

	@Override
	public void finalizarVista() {
		super.finalizarVista();
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	private void initialize() {
		frame.setBounds(100, 100, 320, 330);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		frame.setTitle("Empleado | Reserva");

		panelData = new JPanel();
		panelData.setLayout(new BoxLayout(panelData, BoxLayout.Y_AXIS));

		txtTipoDoc_pasajero = new TextField("Tipo de documento del pasajero", new Font("Tahoma", Font.PLAIN, 16));
		txtNroDoc_pasajero = new TextField("Número de documento del pasajero", new Font("Tahoma", Font.PLAIN, 16));
		txtOrigen = new TextField("Ciudad origen", new Font("Tahoma", Font.PLAIN, 16));
		txtDestino = new TextField("Ciudad destino", new Font("Tahoma", Font.PLAIN, 16));



		panelData.setBorder(BorderFactory.createEmptyBorder(5, 0,5, 5));

		panelData.add(txtTipoDoc_pasajero);
		panelData.add(Box.createVerticalStrut(10));
		panelData.add(txtNroDoc_pasajero);
		panelData.add(Box.createVerticalStrut(10));
		panelData.add(txtOrigen);
		panelData.add(Box.createVerticalStrut(10));
		panelData.add(txtDestino);
		panelData.add(Box.createVerticalStrut(10));

		panelData.setPreferredSize(new Dimension(frame.getWidth()-30, panelData.getPreferredSize().height));

		panelFechas = new JPanel();
		panelFechas.setLayout(new BorderLayout(0, 0));
		
		JFormattedTextField fechaIda, fechaVuelta;
		fechaIda = new DateText("Fecha de ida", new Font("Tahoma", Font.PLAIN, 16));
		fechaIda.setPreferredSize(new Dimension(panelData.getPreferredSize().width/2 -10, fechaIda.getPreferredSize().height));
		
		fechaVuelta = new DateText("Fecha de vuelta", new Font("Tahoma", Font.PLAIN, 16));
		fechaVuelta.setPreferredSize(fechaIda.getPreferredSize());
		fechaVuelta.setEnabled(false);

		panelFechas.setPreferredSize(new Dimension(panelData.getPreferredSize().width, fechaVuelta.getPreferredSize().height+12));
		panelFechas.setBorder(BorderFactory.createEmptyBorder(5, 0,5, 5));
		
		panelFechas.add(fechaIda, BorderLayout.WEST);
		panelFechas.add(fechaVuelta, BorderLayout.EAST);
		
		
		chckbxIdaYVuelta = new JCheckBox("Ida y vuelta");
		chckbxIdaYVuelta.setFont(new Font("Tahoma", Font.PLAIN, 16));
		chckbxIdaYVuelta.addActionListener(e -> fechaVuelta.setEnabled(chckbxIdaYVuelta.isSelected()));

		panelFormulario = new JPanel();
		panelFormulario.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		panelFormulario.add(panelData);
		panelFormulario.add(panelFechas);
		panelFormulario.add(chckbxIdaYVuelta);

		buscar = new JButton();
		buscar.setText("Buscar");
		buscar.addActionListener((e) -> {

			if(Fechas.validar(fechaIda.getText())){
				try {
					if (chckbxIdaYVuelta.isSelected()) {
						Date dateIda = Fechas.convertirStringADate(fechaIda.getText());
						Date dateVuelta = Fechas.convertirStringADate(fechaVuelta.getText());

						if (Fechas.validar(fechaVuelta.getText()) && (dateIda.before(dateVuelta) || dateIda.equals(dateVuelta))) {
							GestorDeVistas.agregarVista(new VistaEmpleadoTablas(conn, txtOrigen.getText(), txtDestino.getText(), fechaIda.getText(), fechaVuelta.getText(), txtTipoDoc_pasajero.getText(), txtNroDoc_pasajero.getText(), nroLeg_empleado));
						} else {
							JOptionPane.showMessageDialog(frame,
									"La fecha de vuelta es inválida.",
									"Fecha inválida",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						GestorDeVistas.agregarVista(new VistaEmpleadoTablas(conn, txtOrigen.getText(), txtDestino.getText(), fechaIda.getText(), txtTipoDoc_pasajero.getText(), txtNroDoc_pasajero.getText(), nroLeg_empleado));
					}
				} catch (PasajeroNotFoundException err){
					JOptionPane.showMessageDialog(frame,
							"El pasajero no ha sido encontrado en la base de datos.",
							"Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(frame,
						"La fecha de ida es inválida.",
						"Fecha inválida",
						JOptionPane.ERROR_MESSAGE);
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
