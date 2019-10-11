package gui.vistas;
import java.awt.*;

import javax.swing.*;

import gui.IngresarAdministrador;
import gui.IngresarEmpleadoBuscar;
import gui.IngresarUsuario;

public class VistaIngresar extends Vista {
	private JPanel panelSeleccion, panelUsuario;
	private JComboBox<IngresarUsuario> cBox_tipoUsuario;

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
		frame.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
//		frame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
		frame.setLayout(new BorderLayout(0, 0));
//		frame.getContentPane().setLayout(new BorderLayout(0, 15));


		panelUsuario = new JPanel();

		String defaultString = "Selecciona el tipo de usuario...";
		cBox_tipoUsuario = new JComboBox<>();
		cBox_tipoUsuario.addItem(new IngresarAdministrador(panelUsuario));
		cBox_tipoUsuario.addItem(new IngresarEmpleadoBuscar(panelUsuario));
		cBox_tipoUsuario.setSelectedIndex(0);
		cBox_tipoUsuario.getModel().setSelectedItem(defaultString);
		cBox_tipoUsuario.setPrototypeDisplayValue(new IngresarUsuario(defaultString) {
			@Override
			public void crearGUI() {
		
			}
		});

		panelUsuario.setBorder(BorderFactory.createEmptyBorder(5, 10, 15,10));

		cBox_tipoUsuario.addActionListener(e -> {
			int heigthOld = panelUsuario.getPreferredSize().height;
			panelUsuario.removeAll();
			panelUsuario.repaint();
			cBox_tipoUsuario.getItemAt(cBox_tipoUsuario.getSelectedIndex()).crearGUI();

			frame.setSize(frame.getWidth(), frame.getHeight()+panelUsuario.getPreferredSize().height-heigthOld);
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
