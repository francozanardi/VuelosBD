package gui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JPasswordField;

import gui.elements.PasswordField;


public class IngresarAdministrador extends IngresarUsuario {
	
	private JPasswordField password;
	private Panel panel;
	private JButton btnIngresar;

	public IngresarAdministrador(Panel p) {
		super("Administrador");
		this.panel = p;
	}
	
	@Override
	public void crearGUI() {
		panel.setSize(264, 78);
		
		password = new PasswordField("Ingrese su contrase\u00F1a", new Font("Tahoma", Font.PLAIN, 16));
		password.setBounds(12, 13, 240, 22);
		
		
		btnIngresar = new JButton();
		btnIngresar.setText("Ingresar");
		btnIngresar.setBounds(12, 48, 240, 22);
		btnIngresar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
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
			}
			
		});
		
		panel.add(password);
		panel.add(btnIngresar);
	}
	
	
}
