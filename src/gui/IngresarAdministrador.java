package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.*;

import conexionBD.ConexionVuelos;
import gui.elements.PasswordField;
import gui.vistas.VistaAdministrador;


public class IngresarAdministrador extends IngresarUsuario {
	
	private JPasswordField password;
	private JPanel panel;
	private JButton btnIngresar;

	public IngresarAdministrador(JPanel p) {
		super("Administrador");
		this.panel = p;
	}
	
	@Override
	public void crearGUI() {
		panel.setSize(264, 78);
		panel.setLayout(new BorderLayout(0, 15));
		
		password = new PasswordField("Ingrese su contrase\u00F1a", new Font("Tahoma", Font.PLAIN, 16));
		
		
		btnIngresar = new JButton();
		btnIngresar.setText("Ingresar");
		btnIngresar.addActionListener(e -> {
			/*
			 * La idea sería acá crear un objeto que se encargue de conectarse a la base de datos y ahí
			 * quede administrado todo el tema de la base de datos.
			 * Inclusive podría llamarse Administrado, aunque quizás mejor debería tener un método que se llame
			 * conectar administrador. Quizás ese objeto se podría crear en la 'Ventana' y pasarse acá.
			 *
			 * También podríamos cambiar el nombre de esta clase a "IngresarAdministrador" o algo así.
			 * Y luego la clase de lo que ve el admin sería "VistaAdministrador"
			 * También podríamos cambiar el nombre de 'Ventana' a 'VistaIngresar'
			 * Además cambiar 'TipoUsuario' a 'IngresarUsuario'
			 */

			ConexionVuelos conn;
			try {
				String pw = new String(password.getPassword());
				System.out.println("Password: " + pw);
				conn = new ConexionVuelos("admin", pw);
				GestorDeVistas.agregarVista(new VistaAdministrador(conn));
			} catch (SQLException er) {
				er.printStackTrace(); //esto hay que cambiarlo por un cuadro de dialogo
				//if codigo == password incorrecta
				//entonces show dialog 'password incorrecta'
				//sino mostrar codigo y lo que dice la bd.
			}
		});

		panel.add(password, BorderLayout.NORTH);
		panel.add(btnIngresar, BorderLayout.CENTER);
	}
	
	
}
