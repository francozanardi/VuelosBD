package gui.vistas;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import gui.GestorDeVistas;

public abstract class Vista {
	protected JFrame frame;
	
	public Vista() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				System.out.println("cerrando vista");
				GestorDeVistas.retroceder();
			}
			
		});
		
		// en vez de esto agregar un botón de volver a cada vista, o algo así.
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
