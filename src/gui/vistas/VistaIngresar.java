package gui.vistas;
import java.awt.*;

import javax.swing.*;

import gui.IngresarAdministrador;
import gui.IngresarEmpleado;
import gui.IngresarUsuario;

public class VistaIngresar extends Vista {
	private JPanel panelSeleccion, panelUsuario;
	private JComboBox<IngresarUsuario> cBox_tipoUsuario;
	private int lastIndexSelected;

	public VistaIngresar() {
		lastIndexSelected = -1;
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		frame.setFont(new Font("Tahoma", Font.PLAIN, 16));
		frame.setLayout(new BorderLayout(0, 0));
		frame.setTitle("Ingresar");

		panelUsuario = new JPanel();

		cBox_tipoUsuario = new JComboBox<>();
		cBox_tipoUsuario.addItem(new IngresarAdministrador(panelUsuario));
		cBox_tipoUsuario.addItem(new IngresarEmpleado(panelUsuario));
		cBox_tipoUsuario.setSelectedIndex(0);
		cBox_tipoUsuario.getModel().setSelectedItem("Selecciona el tipo de usuario...");
		cBox_tipoUsuario.setPreferredSize(new Dimension(140, cBox_tipoUsuario.getPreferredSize().height));

		panelUsuario.setBorder(BorderFactory.createEmptyBorder(5, 10, 15,10));

		cBox_tipoUsuario.addActionListener(e -> {
			int indexSelected = cBox_tipoUsuario.getSelectedIndex();
			if(indexSelected != lastIndexSelected) {
				lastIndexSelected = indexSelected;
				int heigthOld = panelUsuario.getPreferredSize().height;
				panelUsuario.removeAll();
				panelUsuario.repaint();
				cBox_tipoUsuario.getItemAt(indexSelected).crearGUI();

				frame.setSize(frame.getWidth(), frame.getHeight()+panelUsuario.getPreferredSize().height-heigthOld);
			}

		});

		panelSeleccion = new JPanel();
		panelSeleccion.setLayout(new BorderLayout());
		panelSeleccion.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
		panelSeleccion.add(cBox_tipoUsuario, BorderLayout.CENTER);
		
		frame.add(panelSeleccion, BorderLayout.NORTH);
		frame.add(panelUsuario, BorderLayout.SOUTH);
		
		frame.setSize(cBox_tipoUsuario.getPreferredSize().width+100, cBox_tipoUsuario.getPreferredSize().height+100);
		frame.setResizable(false);
		
	}
}
