package gui;

import java.awt.BorderLayout;
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
			ConexionVuelos conn;
			try {
				String pw = new String(password.getPassword());
				System.out.println("Password: " + pw);
				conn = new ConexionVuelos("admin", pw);
				GestorDeVistas.agregarVista(new VistaAdministrador(conn));
			} catch (SQLException er) {
				if(er.getErrorCode() == 1045){
					JOptionPane.showMessageDialog(panel,
							"La contraseña proporcionada es inválida. Por favor, vuelva a ingresarla.",
							"Contraseña inválida",
							JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(panel,
							"Se ha producido un error en la base de datos: \n"+er.getMessage(),
							"Error " + er.getErrorCode(),
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		panel.add(password, BorderLayout.NORTH);
		panel.add(btnIngresar, BorderLayout.CENTER);
	}
	
	
}
