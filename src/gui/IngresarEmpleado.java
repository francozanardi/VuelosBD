package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

import conexionBD.ConexionVuelos;
import gui.elements.PasswordField;
import gui.elements.TextField;
import gui.vistas.VistaEmpleadoBuscar;

public class IngresarEmpleado extends IngresarUsuario {
	private JPanel panel;
	private JTextField txtLegajo;
	private JPasswordField password;
	private JButton btnIngresar;

	public IngresarEmpleado(JPanel p) {
		super("Empleado");
		panel = p;
	}
	
	@Override
	public void crearGUI() {
		panel.setLayout(new BorderLayout(0, 15));
		
		txtLegajo = new TextField("Ingrese su legajo", new Font("Tahoma", Font.PLAIN, 16));
		
		password = new PasswordField("Ingrese su contrase\u00F1a", new Font("Tahoma", Font.PLAIN, 16));
		
		btnIngresar = new JButton();
		btnIngresar.setText("Ingresar");
		btnIngresar.addActionListener(e -> {
			ConexionVuelos conn;
			Statement st;
			ResultSet rs;
			try {
				conn = new ConexionVuelos("empleado", "empleado");
				st = conn.getConnection().createStatement();
				rs = st.executeQuery("Select * from empleados where legajo = '" + txtLegajo.getText() + "' and password = md5('" + new String(password.getPassword()) + "');");
				if(rs.next()){
					GestorDeVistas.agregarVista(new VistaEmpleadoBuscar(conn));
				} else {
					JOptionPane.showMessageDialog(panel,
							"Los datos proporcionados son inválidos, por favor vuelva a ingresarlos.",
							"Datos inválidos",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException er) {
				JOptionPane.showMessageDialog(panel,
						"Se produjo un error al intentar conectarse a la base de datos.\n" + er.getMessage(),
						"Error" + er.getErrorCode(),
						JOptionPane.ERROR_MESSAGE);
			}

		});
		
		panel.add(txtLegajo, BorderLayout.NORTH);
		panel.add(password, BorderLayout.CENTER);
		panel.add(btnIngresar, BorderLayout.SOUTH);
		
	}
	
}
