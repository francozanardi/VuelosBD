package gui.vistas;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import gui.GestorDeVistas;

public abstract class Vista {
	protected JFrame frame;
	protected JMenuBar menuBar;
	protected JMenu menuOpciones;
	protected JMenuItem itemAtras;
	
	public Vista() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				GestorDeVistas.finalizar();
			}
			
		});

		menuBar = new JMenuBar();
		menuOpciones = new JMenu("Opciones");
		itemAtras = new JMenuItem("Atrás");
		itemAtras.addActionListener(e -> GestorDeVistas.retroceder());

		menuOpciones.add(itemAtras);
		menuBar.add(menuOpciones);
	}

	public void activateBackButton(){
		frame.setJMenuBar(menuBar);
	}

	public void desactivateBackButton(){
		frame.setJMenuBar(null);
	}
	
	public void minimizarVista() {
		frame.setVisible(false);
	}
	
	public void finalizarVista() {
		minimizarVista();
		frame.dispose();
	}
	
	public void abrirVista() {
		frame.setVisible(true);
	}

}
