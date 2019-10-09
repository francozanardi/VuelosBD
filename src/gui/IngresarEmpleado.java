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
import javax.swing.JTextField;

import gui.elements.PasswordField;
import gui.elements.TextField;

public class IngresarEmpleado extends IngresarUsuario {
	private Panel panel;
	private JTextField txtLegajo;
	private JPasswordField password;
	private JButton btnIngresar;

	public IngresarEmpleado(Panel p) {
		super("Empleado");
		panel = p;
	}
	
	@Override
	public void crearGUI() {
		panel.setSize(264, 114);
		
		txtLegajo = new TextField("Ingrese su legajo", new Font("Tahoma", Font.PLAIN, 16));
		txtLegajo.setBounds(12, 13, 240, 22);
		
		password = new PasswordField("Ingrese su contrase\u00F1a", new Font("Tahoma", Font.PLAIN, 16));
		password.setBounds(12, 48, 240, 22);
		
		btnIngresar = new JButton();
		btnIngresar.setText("Ingresar");
		btnIngresar.setBounds(12, 83, 240, 22);
		btnIngresar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
		
		panel.add(txtLegajo);
		panel.add(password);
		panel.add(btnIngresar);
		
	}
	
}
