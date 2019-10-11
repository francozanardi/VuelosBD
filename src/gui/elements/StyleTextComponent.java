package gui.elements;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.text.JTextComponent;


class StyleTextComponent<E extends JTextComponent & FocuseableElement> {

	private StyleTextComponent() {
		
	}

	
	public static void addStyle(FocuseableElement fe, String text, Font font) {	
		focusOff(fe, text, font);
		
		fe.addFocusListener(new FocusListener() {
			private boolean estaEscrito;

			{
				estaEscrito = false;
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				if(!estaEscrito)
					focusOn(fe, font);
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if(fe.isEmpty()) {
					focusOff(fe, text, font);
					estaEscrito = false;
				} else {
					estaEscrito = true;
				}
			}
		});
	}
	
	private static void focusOff(FocuseableElement fe, String text, Font font) {
		fe.focusOff();
		fe.setForeground(Color.LIGHT_GRAY);
		fe.setFont(font.deriveFont(Font.ITALIC));
		fe.setText(text);
	}
	
	private static void focusOn(FocuseableElement fe, Font font) {
		fe.focusOn();
		fe.setForeground(Color.BLACK);
		fe.setFont(font);
		fe.setText("");
	}
	
	
}