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
			 * La idea ser�a ac� crear un objeto que se encargue de conectarse a la base de datos y ah�
			 * quede administrado todo el tema de la base de datos.
			 * Inclusive podr�a llamarse Administrado, aunque quiz�s mejor deber�a tener un m�todo que se llame
			 * conectar administrador. Quiz�s ese objeto se podr�a crear en la 'Ventana' y pasarse ac�.
			 *
			 * Tambi�n podr�amos cambiar el nombre de esta clase a "IngresarAdministrador" o algo as�.
			 * Y luego la clase de lo que ve el admin ser�a "VistaAdministrador"
			 * Tambi�n podr�amos cambiar el nombre de 'Ventana' a 'VistaIngresar'
			 * Adem�s cambiar 'TipoUsuario' a 'IngresarUsuario'
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
